package com.qq.reader.provider;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.provider.cache.CacheMode;
import com.qq.reader.provider.loader.DataProviderLoader;
import com.qq.reader.provider.loader.ObserverEntity;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.task.INetQuestParams;
import com.qq.reader.provider.task.LoadDispatcherTask;
import com.qq.reader.provider.utils.GSONUtil;

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
public abstract class BaseDataProvider< Q extends BaseDataBean, P extends BaseDataBean> implements INetQuestParams {

    private static final String TAG = "ReaderBaseDataProvider";


    /**
     * 协议地址
     */
    private String mUrl;

    /**
     * 请求 Bean
     */
    protected Q mRequestBean = null;

    /**
     * GSON 解析生成的Bean
     */
    protected P mData;

    /**
     * 响应 bean 类型
     */
    private Class<P> mResponseClass;

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
     * 缓存模式 {@link com.qq.reader.provider.cache.CacheMode}
     */
    private int cacheMode = CacheMode.CACHE_MODE_USE_CACHE_PRIORITY;

    /**
     * 数据回调
     */
    private volatile MutableLiveData<ObserverEntity> mLiveData;

    /**
     * 分发任务（缓存/网络）
     */
    private volatile LoadDispatcherTask loadDispatcherTask;

    /**
     * 获取缓存 Key
     * POST 请求使用 url+contentType+requestContent
     * GET 请求使用 url
     * <br/>
     * 缓存文件,如果是post请求，则以url+contentType+requestContent生成的SHA256值作为key，取出缓存文件，因缓存未考虑区分用户，所以具体先不以uid加入其中作为区分。
     * Key 的生成由缓存层去做！
     */
    public String getCacheKey() {
        if (TextUtils.equals(getRequestMethod(), "POST")) {
            return getUrl() + getContentType() + getRequestContent();
        }
        return getUrl();
    }

    /**
     * 清除缓存
     */
    public void removeCache() {
        DataProviderLoader.getInstance().remove(getCacheKey());
    }

    public BaseDataProvider(Q requestBean, Class<P> responseClass) {
        mRequestBean = requestBean;
        mResponseClass = responseClass;
    }

    /**
     * 为每个 DataProvider 提供一个数据加载的Task
     * @return
     */
    public synchronized LoadDispatcherTask getDispatcherTask() {
        if (loadDispatcherTask == null) {
            loadDispatcherTask = new LoadDispatcherTask(this);
        }
        return loadDispatcherTask;
    }

    public synchronized MutableLiveData<ObserverEntity> getLiveData() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
        }
        return mLiveData;
    }

    public boolean isExpired() {
        if (getExpiredTime() > System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    /**
     * 获取解析示例 注意判空
     * @return
     */
    public P getData() {
        return mData;
    }

    /**
     *  获取JSON 字符串
     * @return
     */
    public String getJSONStr() {
        return mJSONStr;
    }


    public List<BaseViewBindItem> getDataItems() {
        return mViewBindItems;
    }

    /**
     * 数据解析
     * @param jsonStr
     */
    public void parseData(String jsonStr) {
        mJSONStr = jsonStr;
        try {
            mData = GSONUtil.parseJsonWithGSON(jsonStr, mResponseClass);
        } catch (Exception e) {
            Logger.e(TAG, "parseData: GSON 解析失败!!!!!");
            //解析失败 删除缓存
            removeCache();
            e.printStackTrace();
            try {
                mData = mResponseClass.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // INetQuestParams 接口
    /**
     * 获取协议地址
     * @return
     */
    @Override
    public String getUrl() {
        //每次获取需要重新重新赋值
        mUrl = composeUrl(mRequestBean);
        if (mUrl == null) {
            mUrl = "";
        }
        return mUrl;
    }

    /**
     * 获取请求request的方式，get or post
     */
    @Override
    public String getRequestMethod(){
        return "GET";
    }

    /**
     * 获取 请求的contentType
     */
    @Override
    public String getContentType() {
        return "application/text";
    }

    /**
     * 获取请求对象的 内容
     */
    @Override
    public String getRequestContent() {
        return null;
    }

    @Override
    public boolean needGzip() {
        return false;
    }

    // INetQuestParams 接口
    //----------------------------------------------------------------------------------------------

    public void setCacheMode(int cacheMode) {
        this.cacheMode = cacheMode;
    }

    public int getCacheMode() {
        return cacheMode;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public boolean isCache() {
        return isCache;
    }

    //----------------------------------------------------------------------------------------------
    //抽象方法

    /**
     * 获取整个页面的url
     *
     * @return
     */
    public abstract String composeUrl(Q requestBean);

    /**
     * 数据填充
     */
    public abstract void fillData();

    /**
     * 用于获取系统返回的过期时间
     * @return
     */
    public abstract long getExpiredTime();
}
