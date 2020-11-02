package com.qq.reader.provider;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.provider.inter.IFiller;
import com.qq.reader.provider.loader.ILoader;
import com.qq.reader.provider.parser.IParser;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.inter.INetQuestParams;
import com.qq.reader.provider.loader.ObserverEntity;

import java.util.List;
/**
 * @author zhanglulu on 2019/3/2.
 * for 数据加载中间层 <br/>
 * 能力:
 * 1. 拼接协议url <br/>
 * 2. 解析数据 <br/>
 * 需要传入请求Bean {@link BaseDataBean} 和 响应 Bean {@link BaseDataBean}<br/>
 *  [注: ] 不要随意改变两个
 */
@SuppressWarnings("rawtypes")
public class DataProvider< Q extends BaseDataBean, P extends BaseDataBean> {

    private static final String TAG = "ReaderBaseDataProvider";


    /**
     * 请求 Bean
     */
    protected Q requestBean;

    /**
     * GSON 解析生成的Bean
     */
    protected P mData;

    /**
     * 响应 bean 类型
     */
    private final Class<P> responseClass;

    /**
     * 源 JSON 字符串
     */
    protected String mJSONStr;

    /**
     *  ViewBindItems 列表
     */
    @Nullable
    protected List<BaseViewBindItem> mViewBindItems;

    /**
     * 是否为缓存数据
     */
    private boolean isCache;

    /**
     * 数据回调
     */
    private volatile MutableLiveData<ObserverEntity> liveData;

    /**
     * 解析器
     */
    private IParser<P> parser;

    /**
     * 加载器
     */
    private ILoader loader;

    /**
     * 填充器
     */
    private IFiller<P> filler;

    /**
     * 网络参数接口
     */
    private INetQuestParams netQuestParams;

    private DataProvider(Q requestBean, Class<P> responseClass) {
        this.requestBean = requestBean;
        this.responseClass = responseClass;
    }

    public synchronized MutableLiveData<ObserverEntity> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public boolean isExpired() {
        return filler.getExpiredTime(mData) <= System.currentTimeMillis();
    }

    public Q getRequestBean() {
        return requestBean;
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
    public List<BaseViewBindItem> getViewBindItems() {
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
     * 数据填充
     */
    public void fillData() {
        if (mData == null) {
            throw new RuntimeException("fillData 失败，mData == null !!!");
        }
        mViewBindItems = getFiller().fillData(mData);
    }


    //----------------------------------------------------------------------------------------------
    // 缓存

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    //----------------------------------------------------------------------------------------------
    // parser

    public void setParser(IParser<P> parser) {
        this.parser = parser;
    }

    private IParser<P> getParser() {
        if (parser == null) {
            throw new RuntimeException("Provider 组件需要提供 IParser 解析器，请参考文档使用！");
        }
        return parser;
    }

    //----------------------------------------------------------------------------------------------
    // filler

    public IFiller<P> getFiller() {
        return filler;
    }

    public void setFiller(IFiller<P> filler) {
        this.filler = filler;
    }

    //----------------------------------------------------------------------------------------------
    // loader


    /**
     * 加载数据
     */
    public void loadData() {
        getLoader().loadData(this);
    }

    public void setLoader(ILoader loader) {
        this.loader = loader;
    }

    public ILoader getLoader() {
        if (loader == null) {
            throw new RuntimeException("Provider 组件需要提供 ILoader 加载器，请参考文档使用！");
        }
        return loader;
    }

    //----------------------------------------------------------------------------------------------
    // INetQuestParams 接口
    public void setNetQuestParams(INetQuestParams netQuestParams) {
        this.netQuestParams = netQuestParams;
    }

    public INetQuestParams getNetQuestParams() {
        if (netQuestParams == null) {
            throw new RuntimeException("Provider 组件需要提供 INetQuestParams 网络请求参数接口，请参考文档使用！");
        }
        return netQuestParams;
    }


    /**
     * DataProvider 构建类
     * @param <Q>
     * @param <P>
     */
    public static class Builder< Q extends BaseDataBean, P extends BaseDataBean> {

        private IParser<P> parser;
        private ILoader loader;
        private IFiller<P> filler;
        private INetQuestParams netQuestParams;

        public Builder<Q ,P> setParser(IParser<P> parser) {
            this.parser = parser;
            return this;
        }

        public Builder<Q ,P> setLoader(ILoader loader) {
            this.loader = loader;
            return this;
        }

        public Builder<Q ,P> setFiller(IFiller<P> filler) {
            this.filler = filler;
            return this;
        }

        public Builder<Q ,P> setNetQuestParams(INetQuestParams netQuestParams) {
            this.netQuestParams = netQuestParams;
            return this;
        }

        public DataProvider build(Q requestBean, Class<P> responseClazz) {
            DataProvider dataProvider = new DataProvider(requestBean, responseClazz);
            dataProvider.setLoader(loader);
            dataProvider.setParser(parser);
            dataProvider.setFiller(filler);
            dataProvider.setNetQuestParams(netQuestParams);
            return dataProvider;
        }
    }
}
