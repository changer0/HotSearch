package com.qq.reader.provider;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import converter.IConverter;
import converter.NoConverter;

import com.qq.reader.provider.inter.IGetExpiredTime;
import com.qq.reader.provider.inter.IViewBindItemBuilder;
import com.qq.reader.provider.loader.ILoader;
import com.qq.reader.provider.loader.SimpleProviderLoader;
import com.qq.reader.provider.parser.IParser;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.inter.INetQuestParams;
import com.qq.reader.provider.parser.SimpleGSONParser;
import com.qq.reader.provider.utils.CastUtils;

import java.util.List;
/**
 * @author zhanglulu on 2019/3/2.
 * for 数据加载中间层 <br/>
 * 能力:
 * 1. 拼接协议url <br/>
 * 2. 解析数据 <br/>
 * 3. 填充 ViewBindItem
 */
public class DataProvider<R, P> {

    private static final String TAG = "DataProvider";

    /**
     * GSON 解析生成的Bean
     */
    private R mData;

    /**
     * 结果 bean 类型
     */
    private final Class<R> resultClass;

    /**
     * 响应 bean 类型
     */
    private Class<P> responseClass;

    /**
     * 源 JSON 字符串
     */
    private String mJSONStr;

    /**
     *  ViewBindItems 列表
     */
    @Nullable
    private List<BaseViewBindItem> mViewBindItems;

    /**
     * 是否为缓存数据
     */
    private boolean isCache;

    /**
     * 解析器
     */
    private IParser<P> parser;

    /**
     * 填充器
     */
    private IViewBindItemBuilder<R> builder;

    /**
     * 转换器
     */
    private IConverter<R, P> converter;

    /**
     * 网络参数接口
     */
    private INetQuestParams netQuestParams;

    /**
     * 过期时间
     */
    private IGetExpiredTime<R> expiredTime;

    /**
     * 缓存模式
     */
    private int cacheMode;

    private DataProvider(Class<R> resultClass, Class<P> responseClass) {
        this.resultClass = resultClass;
        this.responseClass = responseClass;
    }

    public boolean isExpired() {
        if (expiredTime == null) {
            Logger.w(TAG, "未配置过期时间，数据将不会缓存！");
            return true;
        }
        return expiredTime.getExpiredTime(mData) <= System.currentTimeMillis();
    }

    /**
     * 获取解析实例 注意判空
     */
    @Nullable
    public R getData() {
        return mData;
    }

    /**
     *  获取JSON 字符串
     */
    public String getJSONStr() {
        return mJSONStr;
    }

    /**
     * 获取 mViewBindItems
     */
    public List<BaseViewBindItem> getViewBindItems() {
        return mViewBindItems;
    }

    /**
     * 数据解析
     */
    public void parseData(String jsonStr) throws Exception {
        mJSONStr = jsonStr;
        try {
            P responseData = getParser().parseData(jsonStr, responseClass);
            mData = getConverter().convert(responseData);
        } catch (Exception e) {
            Logger.e(TAG, "parseData: 解析失败：" + e.getMessage());
            mData = resultClass.newInstance();
            //抛出异常交给
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建 ViewBindItem
     */
    public void buildViewBindItem() {
        if (mData == null) {
            throw new RuntimeException("buildViewBindItem 失败，mData == null !!!");
        }
        mViewBindItems = getBuilder().buildViewBindItem(mData);
    }


    //----------------------------------------------------------------------------------------------
    // 缓存

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public String getRequestKey() {
        INetQuestParams p = getNetQuestParams();
        if (TextUtils.equals(p.getRequestMethod(), "POST")) {
            return p.getUrl() + p.getRequestMethod() + p.getRequestContent() + p.getContentType();
        }
        return p.getUrl();
    }

    public int getCacheMode() {
        return cacheMode;
    }

    //----------------------------------------------------------------------------------------------
    // parser

    private IParser<P> getParser() {
        if (parser == null) {
            return new SimpleGSONParser<>();
        }
        return parser;
    }

    //----------------------------------------------------------------------------------------------
    // converter

    private IConverter<R, P> getConverter() {
        if (converter == null) {
            //此时无需转换器，直接强转成 R 类型
            return new NoConverter<>();
        }
        if (responseClass == resultClass) {
            throw new RuntimeException("需要在 with 中传入响应 bean 类型！");
        }
        return converter;
    }


    //----------------------------------------------------------------------------------------------
    // builder

    private IViewBindItemBuilder<R> getBuilder() {
        if (builder == null) {
            throw new RuntimeException("Provider 组件需要提供 IViewBindItemBuilder 构建接口，请参考文档使用！");
        }
        return builder;
    }

    //----------------------------------------------------------------------------------------------
    // INetQuestParams 接口

    public INetQuestParams getNetQuestParams() {
        if (netQuestParams == null) {
            throw new RuntimeException("Provider 组件需要提供 INetQuestParams 网络请求参数接口，请参考文档使用！");
        }
        return netQuestParams;
    }

    //----------------------------------------------------------------------------------------------
    // 构造

    public static <R, P> RequestBuilder with(Class<R> resultClass, Class<P> responseClass) {
        return new RequestBuilder<R, P>(resultClass, responseClass);
    }

    public static <R> RequestBuilder with(Class<R> resultClass) {
        return new RequestBuilder<R, R>(resultClass, resultClass);
    }

    /**
     * 请求构造类
     * @param <R>
     */
    public static class RequestBuilder<R, P> {
        private DataProvider<R, P> provider;

        private RequestBuilder(Class<R> resultClass, Class<P> responseClass) {
            this.provider = new DataProvider<>(resultClass, responseClass);
        }

        /**
         * 请求参数
         */
        private String url;
        private String requestMethod = "GET";
        private String requestContent;
        private String requestContentType;
        private boolean needGzip;

        public RequestBuilder<R, P> url(String url) {
            this.url = url;
            return this;
        }

        public RequestBuilder<R, P> get() {
            this.requestMethod = "GET";
            return this;
        }

        public RequestBuilder<R, P> post(String contentType, String requestContent) {
            this.requestMethod = "POST";
            this.requestContentType = contentType;
            this.requestContent = requestContent;
            return this;
        }

        public RequestBuilder<R, P> post(String requestContent) {
            this.requestMethod = "POST";
            this.requestContent = requestContent;
            this.requestContentType = "application/json";
            return this;
        }

        public RequestBuilder<R, P> needGzip(boolean needGzip) {
            this.needGzip = needGzip;
            return this;
        }

        public RequestBuilder<R, P> converter(IConverter converter) {
            provider.converter = CastUtils.cast(converter);
            return this;
        }

        /**
         * 加载器，提供默认的加载器 SimpleProviderLoader
         */
        private ILoader<R, P> loader = new SimpleProviderLoader<R, P>();

        public RequestBuilder<R, P> loader(ILoader loader) {
            this.loader = CastUtils.cast(loader);
            return this;
        }

        /**
         * 解析器，提供默认解析器 SimpleGSONParser
         */
        public RequestBuilder<R, P> parser(IParser parser) {
            provider.parser = CastUtils.cast(parser);
            return this;
        }

        /**
         * ViewBindItem 构建器
         */
        public RequestBuilder<R, P> viewBindItemBuilder(IViewBindItemBuilder builder) {
            provider.builder = CastUtils.cast(builder);
            return this;
        }

        /**
         * 缓存配置
         */
        public RequestBuilder<R, P> cacheConfig(int cacheMode, IGetExpiredTime expiredTime) {
            provider.cacheMode = cacheMode;
            provider.expiredTime = CastUtils.cast(expiredTime);
            return this;
        }

        /**
         * 数据加载
         */
        public ProviderLiveData load() {
            provider.netQuestParams = new INetQuestParams() {
                @Override
                public String getUrl() {
                    return url;
                }
                @Override
                public String getRequestMethod() {
                    return requestMethod;
                }
                @Override
                public String getContentType() {
                    return requestContentType;
                }
                @Override
                public String getRequestContent() {
                    return requestContent;
                }
                @Override
                public boolean needGzip() {
                    return needGzip;
                }
            };
            return loader.loadData(provider);
        }
    }
}
