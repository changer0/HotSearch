package com.qq.reader.provider;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.qq.reader.provider.inter.IGetExpiredTime;
import com.qq.reader.provider.inter.IViewBindItemBuilder;
import com.qq.reader.provider.loader.ILoader;
import com.qq.reader.provider.loader.SimpleProviderLoader;
import com.qq.reader.provider.parser.IParser;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.inter.INetQuestParams;
import com.qq.reader.provider.loader.ObserverEntity;
import com.qq.reader.provider.parser.SimpleGSONParser;

import java.util.List;
/**
 * @author zhanglulu on 2019/3/2.
 * for 数据加载中间层 <br/>
 * 能力:
 * 1. 拼接协议url <br/>
 * 2. 解析数据 <br/>
 * 3. 填充 ViewBindItem
 * 需要传入请求Bean 和 响应 Bean <br/>
 */
public class DataProvider<P> {

    private static final String TAG = "DataProvider";

    /**
     * GSON 解析生成的Bean
     */
    private P mData;

    /**
     * 响应 bean 类型
     */
    private final Class<P> responseClass;

    /**
     * 源 JSON 字符串
     */
    private String mJSONStr;

    /**
     *  ViewBindItems 列表
     */
    @Nullable
    private List<BaseViewBindItem<?, ? extends RecyclerView.ViewHolder>> mViewBindItems;

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
    private IViewBindItemBuilder<P> builder;

    /**
     * 网络参数接口
     */
    private INetQuestParams netQuestParams;

    /**
     * 过期时间
     */
    private IGetExpiredTime<P> expiredTime;

    /**
     * 缓存模式
     */
    private int cacheMode;

    private DataProvider(Class<P> responseClass) {
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
     * 获取解析示例 注意判空
     */
    @Nullable
    public P getData() {
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
    public List<BaseViewBindItem<?, ? extends RecyclerView.ViewHolder>> getViewBindItems() {
        return mViewBindItems;
    }

    /**
     * 数据解析
     */
    public void parseData(String jsonStr) {
        mJSONStr = jsonStr;
        try {
            mData = getParser().parseData(jsonStr, responseClass);
        } catch (Exception e) {
            Logger.e(TAG, "parseData: 解析失败：" + e.getMessage());
            e.printStackTrace();
            try {
                mData = responseClass.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
            return new SimpleGSONParser<P>();
        }
        return parser;
    }

    //----------------------------------------------------------------------------------------------
    // builder

    private IViewBindItemBuilder<P> getBuilder() {
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
    // IGetExpiredTime

    private void setExpiredTime(IGetExpiredTime<P> expiredTime) {
        this.expiredTime = expiredTime;
    }

    //----------------------------------------------------------------------------------------------
    // 构造

    public static <T> RequestBuilder with(Class<T> responseClazz) {
        return new RequestBuilder(responseClazz);
    }

    /**
     * 请求构造类
     * @param <P>
     */
    public static class RequestBuilder<P> {
        private DataProvider<P> provider;

        public RequestBuilder(Class<P> responseClazz) {
            this.provider = new DataProvider<>(responseClazz);
        }

        /**
         * 请求参数
         */
        private String url;
        private String requestMethod;
        private String requestContent;
        private String requestContentType;
        private boolean needGzip;

        public RequestBuilder<P> url(String url) {
            this.url = url;
            return this;
        }

        public RequestBuilder<P> requestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }
        public RequestBuilder<P> requestContent(String requestContent) {
            this.requestContent = requestContent;
            return this;
        }
        public RequestBuilder<P> requestContentType(String requestContentType) {
            this.requestContentType = requestContentType;
            return this;
        }
        public RequestBuilder<P> needGzip(boolean needGzip) {
            this.needGzip = needGzip;
            return this;
        }

        /**
         * 加载器，提供默认的加载器 SimpleProviderLoader
         */
        private ILoader<P> loader = new SimpleProviderLoader();

        public RequestBuilder<P> loader(ILoader<P> loader) {
            this.loader = loader;
            return this;
        }

        /**
         * 解析器，提供默认解析器 SimpleGSONParser
         */
        public RequestBuilder<P> parser(IParser<P> parser) {
            provider.parser = parser;
            return this;
        }

        /**
         * ViewBindItem 构建器
         */
        public RequestBuilder<P> viewBindItemBuilder (IViewBindItemBuilder<P> builder) {
            provider.builder = builder;
            return this;
        }

        /**
         * 缓存配置
         */
        public RequestBuilder<P> cacheConfig(int cacheMode, IGetExpiredTime<P> expiredTime) {
            provider.cacheMode = cacheMode;
            provider.expiredTime = expiredTime;
            return this;
        }

        /**
         * 数据加载
         */
        public MutableLiveData<ObserverEntity> load() {
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
