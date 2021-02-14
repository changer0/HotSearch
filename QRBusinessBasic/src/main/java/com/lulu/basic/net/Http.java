package com.lulu.basic.net;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Http util class
 *
 * @author SawRen
 * @email: sawren@tencent.com
 * @date 2010-4-27
 */
public class Http {

    public static boolean ANDROID_HTTP_KEEYALIVE_BUG_VERSION;
    static {
        if (Build.VERSION.SDK != null) {
            int sdk_level = Integer.valueOf(Build.VERSION.SDK);
            ANDROID_HTTP_KEEYALIVE_BUG_VERSION = (sdk_level >= 14 && sdk_level < 16)
                    || sdk_level < 8;
        }
    }

    public static final String POST = "POST";

    public static final String GET = "GET";
    /**
     * 连接超时时间 原来为20秒，20180609 尝试更改为10秒 若后续有问题 超时问题 则修改此处
     */
    public static final int CON_TIME_OUT = 10 * 1000;

    public static final int READ_TIME_OUT = 10 * 1000;

    public static final int FILE_BUFFER_SIZE = 5 * 1024;

    public static final String TYPE_WML = "text/vnd.wap.wml";
    public static final String TYPE_WMLC = "application/vnd.wap.wmlc";

    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";

    //对http_mode进行定义，这里先简单改。因为OnlineWorker的缘故，重试逻辑不太好直接封装到Http这个工具类里面
    //TODO 需要考虑进行结构优化 @author River
    public static final int HTTP_MODE_HTTP = 0;
    public static final int HTTP_MODE_HTTPS = 1;
    public static final int HTTP_MODE_HTTPDNS = 2;


    /**
     * 同步请求
     *
     * @param destUrl        can't be null, others can
     * @param requestContent
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static InputStream sendRequest(String destUrl,
                                          String requestContent, String mothed,
                                          HashMap<String, String> mParams, String contentType, Context context)
            throws Exception {

        Request request = makeRequestBody(destUrl, requestContent,
                false, mothed, mParams, contentType);
        Response response = getResponse(request, null, null);
        // 需要加入返回值判断? add by saw
        return response.body().byteStream();

    }

    /**
     * 同步请求
     *
     * @param destUrl
     * @param requestContent
     * @param needGzip
     * @param mothed
     * @param mParams
     * @param contentType
     * @param applicationInterceptors
     * @param networkInterceptors
     */
    public static Response doRequest(String destUrl, String requestContent,
                                     boolean needGzip, String mothed, HashMap<String, String> mParams,
                                     String contentType, List<Interceptor> applicationInterceptors, List<Interceptor> networkInterceptors) throws IOException {
        Log.i("network", " destUrl : " + destUrl);
        Request request = makeRequestBody(destUrl, requestContent,
                needGzip, mothed, mParams, contentType);

        Response response = getResponse(request, applicationInterceptors, networkInterceptors);
        return response;
    }


    /**
     * 异步请求
     *
     * @param destUrl
     * @param requestContent
     * @param needGzip
     * @param mothed
     * @param mParams
     * @param contentType
     * @param callback
     * @param applicationInterceptors
     * @param networkInterceptors
     */
    public static void doRequestAsy(String destUrl, String requestContent,
                                    boolean needGzip, String mothed, HashMap<String, String> mParams,
                                    String contentType, Callback callback, List<Interceptor> applicationInterceptors, List<Interceptor> networkInterceptors) {
        Log.i("network", " destUrl : " + destUrl);
        Request request = makeRequestBody(destUrl, requestContent,
                needGzip, mothed, mParams, contentType);

        getResponseAsy(request, callback, applicationInterceptors, networkInterceptors);
    }

    /**
     * 构建OKhttp Request对象
     *
     * @param destUrl        请求目标url
     * @param requestContent 当请求为post时，为RequestBody数据；当请求为GET时，为destUrl后附带的参数
     * @param needGzip       是否需要Gzip
     * @param mothed         请求类型如POST、GET
     * @param mParams        请求头集合
     * @param contentType    当请求为POST时，用于标识RequestBody数据类型
     * @return
     * @throws IOException
     */
    private static Request makeRequestBody(String destUrl,
                                           final String requestContent, boolean needGzip, String mothed,
                                           HashMap<String, String> mParams, final String contentType) {
        boolean urlConstans = false;
        String tempEndUrl = "";
        if (destUrl.contains("?")) {
            tempEndUrl = destUrl.substring(destUrl.indexOf("?"), destUrl.length());
        }
        if (destUrl.endsWith("?") || (destUrl.contains("?") && (tempEndUrl.contains("=") || tempEndUrl.contains("&")))) {
            urlConstans = true;
        }
        //如果是get方式，并且上层已经拼好了 url后面的查询部分，则直接添加即可
        if (GET.equals(mothed) && requestContent != null && requestContent.length() > 0) {
            if (urlConstans) {
                destUrl = destUrl + requestContent;
            } else {
                destUrl = destUrl + "?" + requestContent;
            }
        } else if (GET.equals(mothed) && mParams != null && mParams.size() > 0) {//如果上层未拼好，只是传递了集合，则在此处拼接
            StringBuilder requestParams = new StringBuilder();
            Set<Map.Entry<String, String>> entrySet = mParams.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                if (entry.getKey() != null) {
                    requestParams.append(entry.getKey() + "=");
                    requestParams.append(URLEncoder.encode(entry.getValue()) + "&");
                }
            }
            if (urlConstans) {
                if (destUrl.endsWith("?")) {
                    destUrl = destUrl + requestParams.substring(0, requestParams.length() - 1);
                } else {
                    destUrl = destUrl + "&" + requestParams.substring(0, requestParams.length() - 1);
                }
            } else {
                destUrl = destUrl + "?" + requestParams.substring(0, requestParams.length() - 1);
            }

        }
        Request.Builder builder = createRequestBuilder(destUrl, mParams);
        //如果是post方式，并且如果上层拼接好了body
        if (POST.equals(mothed) && requestContent != null) {
            builder.post(createPostRequestBody(requestContent, needGzip, contentType));
        } else if (POST.equals(mothed) && mParams != null) {//如果是post方式
            FormBody.Builder formBody = new FormBody.Builder();
            Set<Map.Entry<String, String>> entrySet = mParams.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    formBody.add(entry.getKey(), entry.getValue());
                }
            }
            builder.post(formBody.build());
        }

        return builder.build();
    }

    /**
     * 构建OKhttp post请求体
     *
     * @param contentType
     * @return
     */
    private static RequestBody createPostRequestBody(final String requestContent, boolean needGzip, String contentType) {
        RequestBody requestBody = null;
        //OKHTTP 底层并没有做MediaType.parse(mediaTypeStr) 的非空校验，所以当mediaTypeStr为null时候会报null指针，一定要自己人为判断创建
        final MediaType mediaType;
        if (!TextUtils.isEmpty(contentType)) {
            String mediaTypeStr = contentType + "; charset=utf-8";
            mediaType = MediaType.parse(mediaTypeStr);
        } else {
            mediaType = null;
        }
        if (needGzip) {
            requestBody = new RequestBody() {
                @Override
                public MediaType contentType() {
                    return mediaType;
                }

                @Override
                public long contentLength() {
                    return -1; // 无法提前知道压缩后的数据大小
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    gzipSink.writeString(requestContent, Charset.defaultCharset());
                    gzipSink.close();
                }
            };
        } else {
            requestBody = RequestBody.create(mediaType, requestContent);
        }
        return requestBody;
    }


    /**
     * 构建requestBuilder
     *
     * @param srcUrlString
     * @return
     */
    private static Request.Builder createRequestBuilder(String srcUrlString, HashMap<String, String> mParams) {
        Request.Builder builder = new Request.Builder();
        //添加head，注意一定要先调用builder.mParams后单项添加 因为底层是重新new的集合
        // 在4.0.4和2.1部分机器上都发现保持keep-alive字段会导致网络不畅有时候
        if (ANDROID_HTTP_KEEYALIVE_BUG_VERSION) {
            builder.addHeader("Connection", "Close");
        }
        int httpMode = NetUtils.getHttpMode(srcUrlString);
        InetSocketAddress pxy = HttpNetUtil.getProxy();
        URL destUrl = null;
        try {
            destUrl = new URL(srcUrlString);
            if (pxy == null) {
                switch (httpMode) {
                    case HTTP_MODE_HTTP:
                        srcUrlString = srcUrlString.replaceFirst(destUrl.getProtocol(), "http");
                        break;
                    case HTTP_MODE_HTTPS:
                        srcUrlString = srcUrlString.replaceFirst(destUrl.getProtocol(), "https");
                        break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //将通过dns转换后的url设置上 转换成功则会在head中添加Host，此处变为ip前缀的url
        builder.url(srcUrlString);
        //此处预留一个tag，日后或许会用到
        builder.tag(srcUrlString);

        return builder;
    }

    /**
     * 异步执行，启动okhttp自身线程池以及队列管理方式执行
     *
     * @param request
     * @param callback
     * @param applicationInterceptors
     * @param networkInterceptors
     */
    private static void getResponseAsy(Request request, Callback callback, List<Interceptor> applicationInterceptors, List<Interceptor> networkInterceptors) {
//        OkHttpClient mHttpClinet;
//        final String hostNameVerifier = request.url().host();
        //根据不同host取出相应的client
//        if (request.isHttps()) {
//            mHttpClinet = OkHttpConfig.getInstance().getOkHttpClientFromHost(hostNameVerifier);
//        } else {
//            mHttpClinet = OkHttpConfig.getInstance().getOkHttpClient();
//        }
        OkHttpClient mHttpClinet = OkHttpConfig.getInstance().getOkHttpClient();
        //如果当前请求是自定义了拦截器，重新生成httpCient对象执行
        if (applicationInterceptors != null || networkInterceptors != null) {
            mHttpClinet = OkHttpConfig.getInstance().getOkHttpClient(applicationInterceptors, networkInterceptors, mHttpClinet);
        }
        if (callback != null) {
            mHttpClinet.newCall(request).enqueue(callback);
        }
    }

    /**
     * 同步执行方法，需要外界自己开启子线程
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static Response getResponse(Request request, List<Interceptor> applicationInterceptors, List<Interceptor> networkInterceptors) throws IOException {
//        OkHttpClient mHttpClinet;
//        final String hostNameVerifier = request.url().host();
//        if (request.isHttps()) {
//            mHttpClinet = OkHttpConfig.getInstance().getOkHttpClientFromHost(hostNameVerifier);
//        } else {
//            mHttpClinet = OkHttpConfig.getInstance().getOkHttpClient();
//        }
        OkHttpClient mHttpClinet = OkHttpConfig.getInstance().getOkHttpClient();
        //如果当前请求是自定义了拦截器，重新生成httpCient对象执行
        if (applicationInterceptors != null || networkInterceptors != null) {
            mHttpClinet = OkHttpConfig.getInstance().getOkHttpClient(applicationInterceptors, networkInterceptors, mHttpClinet);
        }
        return mHttpClinet.newCall(request).execute();
    }


    public static Response prepareHttpConnection(String url,
                                                 HashMap<String, String> mParams) throws IOException {
        int httpMode = NetUtils.getHttpMode(url);
        return prepareHttpConnectionAndTry(url, mParams, true, httpMode);
    }

    public static Response prepareHttpConnection(String url,
                                                 HashMap<String, String> mParams, int httpMode) throws IOException {
        return prepareHttpConnectionAndTry(url, mParams, true, httpMode);
    }

    /**
     * @param url
     * @param mParams
     * @param
     * @param isWapToTry 遇到头部是页面是否再重试
     * @return
     * @throws IOException
     */
    public static Response prepareHttpConnectionAndTry(String url,
                                                       HashMap<String, String> mParams, boolean isWapToTry, int httpMode)
            throws IOException {
        URL arul = new URL(url);
        Response response = connect(arul, mParams, httpMode);
        Log.v("Http", "prepareConnection.Finish to prepare connection");
        return response;
    }


    /**
     * 可处理外部传进来的header
     * @param srcUrl
     * @param mParams
     * @param httpMode
     * @return
     * @throws IOException
     */
    public static Response connect(URL srcUrl, HashMap<String, String> mParams, int httpMode) throws IOException {

        Request.Builder requestBuilder = createRequestBuilder(srcUrl.toString(), mParams);

        try {
            if(mParams != null){
                for (String s : mParams.keySet()) {
                    String value = mParams.get(s);
                    if(!TextUtils.isEmpty(value)) {
                        requestBuilder.addHeader(s, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Http","connect header 特殊有字符");
        }

        Request request = requestBuilder.build();
        return getResponse(request, null, null);

    }

    public static URLConnection openConnection(URL arul)
            throws IOException {
        InetSocketAddress pxy = HttpNetUtil.getProxy();
        try {
            Log.d("Http", "Scheme:" + arul.toURI().getScheme() + ";Url:" + arul.toString());
            if (arul.toURI().getScheme().equals(SCHEME_HTTPS)) {
                HttpsURLConnection urlConnection = null;
                if (pxy == null) {
                    urlConnection = (HttpsURLConnection) arul.openConnection();
                } else {
                    Proxy p = new Proxy(Proxy.Type.HTTP, pxy);
                    urlConnection = (HttpsURLConnection) arul.openConnection(p);
                }
                return urlConnection;
            } else {
                HttpURLConnection urlConnection = null;
                if (pxy == null) {
                    urlConnection = (HttpURLConnection) arul.openConnection();
                } else {
                    Proxy p = new Proxy(Proxy.Type.HTTP, pxy);
                    urlConnection = (HttpURLConnection) arul.openConnection(p);
                }
                return urlConnection;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        if (pxy == null) {
            urlConnection = (HttpURLConnection) arul.openConnection();
        } else {
            Proxy p = new Proxy(Proxy.Type.HTTP, pxy);
            urlConnection = (HttpURLConnection) arul.openConnection(p);
        }

        return urlConnection;
    }


    public static byte[] openFileHttpByte(String aurl)
            throws IOException {
        URL url = new URL(aurl);
        URLConnection conn = openConnection(url);
        conn.setConnectTimeout(CON_TIME_OUT);
        conn.setReadTimeout(READ_TIME_OUT);

        InputStream is = null;
        byte[] refile;
        try {
            is = conn.getInputStream();
            refile = getByte(conn.getContentLength(), is);
        } catch (IOException e) {

            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return refile;
    }

    /**
     * @param
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] getByte(int length, InputStream is) throws IOException {
        int fileSize = length;
        byte[] buffer = new byte[fileSize];
        byte[] tempBuffer = new byte[FILE_BUFFER_SIZE];
        int pos = 0;
        int readLength = 0;
        while ((readLength = is.read(tempBuffer)) != -1) {
            System.arraycopy(tempBuffer, 0, buffer, pos, readLength);
            pos += readLength;
        }
        return buffer;
    }

    /**
     * 取消协议请求，主要用于一些页面不等待返回值，立刻关闭页面 或者 一些业务需求等
     * @param urlStr
     */
    public static void cancel(String urlStr) {
//        try {
//            URL url = new URL(urlStr);
//            OkHttpClient okHttpClient = OkHttpConfig.getInstance().getOkHttpClientFromHost(url.getHost());
//            if (okHttpClient == null) {
//                okHttpClient = OkHttpConfig.getInstance().getOkHttpClient();
//            }
            OkHttpClient okHttpClient = OkHttpConfig.getInstance().getOkHttpClient();
            OkHttpConfig.getInstance().cancelTag(okHttpClient, urlStr);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

    }
}
