package com.qq.reader.zebra;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import com.qq.reader.zebra.inter.IGetExpiredTime;
import com.qq.reader.zebra.inter.IViewBindItemBuilder;
import com.qq.reader.zebra.loader.ILoader;
import com.qq.reader.zebra.loader.SimpleLoader;
import com.qq.reader.zebra.parser.IParser;
import com.qq.reader.zebra.log.Logger;
import com.qq.reader.zebra.inter.INetQuestParams;
import com.qq.reader.zebra.parser.SimpleGSONParser;
import com.qq.reader.zebra.utils.CastUtils;

import java.util.List;
/**
 * @author zhanglulu on 2019/3/2.
 * for 数据加载中间层 <br/>
 * 能力:
 * 1. 拼接协议url <br/>
 * 2. 解析数据 <br/>
 * 3. 填充 ViewBindItem
 */
public class Zebra<R> {

    private static final String TAG = "DataProvider";

    /**
     * GSON 解析生成的Bean
     */
    private R mData;

    /**
     * 结果 bean 类型
     */
    private final Class<R> responseClass;

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
    private IParser<R> parser;

    /**
     * 填充器
     */
    private IViewBindItemBuilder<R> builder;

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

    /**
     * 加载信号
     */
    private int loadSignal;

    private Zebra(Class<R> responseClass) {
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
            mData = getParser().parseData(jsonStr, responseClass);
        } catch (Exception e) {
            Logger.e(TAG, "parseData: 解析失败：" + e.getMessage());
            mData = responseClass.newInstance();
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

    /**
     * 获取加载信号
     */
    public int getLoadSignal() {
        return loadSignal;
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

    private IParser<R> getParser() {
        if (parser == null) {
            return new SimpleGSONParser<>();
        }
        return parser;
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

    public static <R> RequestBuilder<R> with(Class<R> resultClass) {
        return new RequestBuilder<R>(resultClass);
    }

    /**
     * 请求构造类
     * @param <R>
     */
    public static class RequestBuilder<R> {
        private Zebra<R> provider;

        private RequestBuilder(Class<R> responseClass) {
            this.provider = new Zebra<>(responseClass);
        }

        /**
         * 请求参数
         */
        private String url;
        private String requestMethod = "GET";
        private String requestContent;
        private String requestContentType;
        private boolean needGzip;

        public RequestBuilder<R> url(String url) {
            this.url = url;
            return this;
        }

        public RequestBuilder<R> get() {
            this.requestMethod = "GET";
            return this;
        }

        public RequestBuilder<R> post(String contentType, String requestContent) {
            this.requestMethod = "POST";
            this.requestContentType = contentType;
            this.requestContent = requestContent;
            return this;
        }

        public RequestBuilder<R> post(String requestContent) {
            this.requestMethod = "POST";
            this.requestContent = requestContent;
            this.requestContentType = "application/json";
            return this;
        }

        public RequestBuilder<R> needGzip(boolean needGzip) {
            this.needGzip = needGzip;
            return this;
        }

        /**
         * 加载器，提供默认的加载器 SimpleProviderLoader
         */
        private ILoader<R> loader = new SimpleLoader<R>();

        public RequestBuilder<R> loader(ILoader loader) {
            this.loader = CastUtils.cast(loader);
            return this;
        }

        /**
         * 解析器，提供默认解析器 SimpleGSONParser
         */
        public RequestBuilder<R> parser(IParser parser) {
            provider.parser = CastUtils.cast(parser);
            return this;
        }

        /**
         * ViewBindItem 构建器
         */
        public RequestBuilder<R> viewBindItemBuilder(IViewBindItemBuilder builder) {
            provider.builder = CastUtils.cast(builder);
            return this;
        }

        /**
         * 缓存配置
         */
        public RequestBuilder<R> cacheConfig(int cacheMode, IGetExpiredTime expiredTime) {
            provider.cacheMode = cacheMode;
            provider.expiredTime = CastUtils.cast(expiredTime);
            return this;
        }

        /**
         * 数据加载，此种加载方式不推荐！可使用带有加载信号的 load 方法 {@link #load(String)}
         */
        @Deprecated
        public ZebraLiveData load() {
            return load(-1);
        }

        /**
         * @param loadSignal 加载信号，由业务侧实现，一般可用于加载的状态信号，例如：加载更多等
         * @return
         */
        public ZebraLiveData load(int loadSignal) {
            provider.loadSignal = loadSignal;
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
