package com.lulu.basic.net;

import com.lulu.basic.Init;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 构建Okhttp对象
 *
 * 正常使用单例okhttpClient，若有自定义拦截器 或特殊需求则重新构建okhttpClient对象，
 *
 * 目前我们的方式走协议各种数据是放在head里，此处标记一下，若后续改为走post方法体，建议将
 * okhttpClient 针对不同host创建多个缓存对象，类似于工厂的方式， 这样可以针对不同的host定制出不同的需求开发以及取消等。
 *
 * Created by qijunqing on 2018/4/25.
 */

public class OkHttpConfig {

    private volatile static OkHttpConfig okHttpConfig;
    private volatile static OkHttpClient okHttpClient;

    /**
     * 存放https请求缓存client，因OKhttpClient根据之前创建的对象重新build生成，所以底部线程池等 参数都是公用的，只是缓存了OKhttpClient对象而已用于不同host请求的https host校验
     * 选择对应使用相应对象
     * key 代表host，value代表host相应的client对象
     */
    private HashMap<String, OkHttpClient> httpsCacheOkhttpClient = new HashMap<>();

    private OkHttpConfig() {
    }


    public static OkHttpConfig getInstance(){
        if(okHttpConfig==null){
            synchronized (OkHttpConfig.class) {
                if(okHttpConfig==null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(Http.CON_TIME_OUT, TimeUnit.MILLISECONDS)
                            .readTimeout(Http.READ_TIME_OUT, TimeUnit.MILLISECONDS);

                    //取消重定向
                    //builder.followRedirects(false);
                    //builder.followSslRedirects(false);
//                    SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
//                    if (sslSocketFactory != null)
//                        builder.sslSocketFactory(getSSLSocketFactory());
                    InetSocketAddress pxy = HttpNetUtil.getProxy();
                    if(pxy!=null){//设置代理
                        builder.proxy(new Proxy(Proxy.Type.HTTP, HttpNetUtil.getProxy()));
                    }
                    if(mSslFactoryListener != null) {
                        mSslFactoryListener.setSSLFactory(builder);
                    }
                    okHttpClient = builder.build();

                    okHttpConfig=new OkHttpConfig();
                }
            }
        }
        return okHttpConfig;
    }

    /**
     * 获取okHttpClient 对象
     * @return
     */
    public OkHttpClient getOkHttpClient() {

        return okHttpClient;
    }

    /**
     * 获取builder
     *
     * @return
     */
    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return okHttpClient.newBuilder();
    }

    /**
     * 添加自定义拦截器构建OkhttpClient，若传入的拦截器为null则默认使用单例okHttpClient
     *
     * @param applicationIntercepList 应用拦截器集合
     * @param networkInterceptorList  网络拦截器集合
     * @param okHttpClient 以当前client为样本clone生成新的自定义对象
     * @return
     */
    public OkHttpClient getOkHttpClient(List<Interceptor> applicationIntercepList, List<Interceptor> networkInterceptorList,OkHttpClient okHttpClient) {

        if (applicationIntercepList == null && networkInterceptorList == null) {
            return okHttpClient;
        }

        OkHttpClient.Builder builder = okHttpClient.newBuilder();

        if (applicationIntercepList != null && applicationIntercepList.size() > 0) {
            for (Interceptor interceptor : applicationIntercepList) {
                builder.addInterceptor(interceptor);
            }
        }

        if (networkInterceptorList != null && networkInterceptorList.size() > 0) {
            for (Interceptor interceptor : networkInterceptorList) {
                builder.addNetworkInterceptor(interceptor);
            }
        }
        if(mSslFactoryListener != null) {
            mSslFactoryListener.setSSLFactory(builder);
        }
        OkHttpClient newClient=builder.build();
        return newClient;
    }

    public void cancelAll(){
        if(okHttpClient!=null) {
            okHttpClient.dispatcher().cancelAll();
        }
        if(httpsCacheOkhttpClient!=null){
            httpsCacheOkhttpClient.clear();
        }

    }

    /**
     * 取消单个请求
     * @param client
     * @param tag
     */
    public void cancelTag(OkHttpClient client,Object tag) {
        if (tag == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * SSL证书
     * @return
     */
    private SSLSocketFactory getSSLSocketFactory() {
//			SSLSocketFactory wrappedSSLSocketFactory = new SSLSocketFactoryWrapper(connection);
//			connection.setSSLSocketFactory(wrappedSSLSocketFactory);
        CertificateFactory cf = null;

        final Certificate ca;
        try {
            InputStream in = Init.app.getAssets().open("server.cer");
            cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(in);
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
//
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 华为厂商要求https使用它们自己的证书，个别厂商的要求不能耦合到代码里面，通过接口实现。
     */
    public interface SSLFactoryListener{
        void setSSLFactory(OkHttpClient.Builder builder);
    }
    private static SSLFactoryListener mSslFactoryListener;
    public static void setSSLFactoryListener(SSLFactoryListener sslFactoryListener) {
        mSslFactoryListener = sslFactoryListener;
    }
}
