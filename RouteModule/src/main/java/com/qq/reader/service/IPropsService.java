package com.qq.reader.service;

import androidx.annotation.Nullable;
import android.util.Log;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.qq.reader.entity.props.ChapterInfo;
import com.qq.reader.entity.props.PropsDownloadModel;
import com.qq.reader.entity.props.UsePropsRequestBean;
import com.qq.reader.ARouterUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanglulu on 2019/4/15.
 * for 给外部提供方法
 */
public abstract class IPropsService implements IProvider {

    private static IPropsService instance;
    private static final String TAG = "IPropsService";
    protected ConcurrentHashMap<String, PropsDownloadModel> mPropsDownloadModelCache = new ConcurrentHashMap<String, PropsDownloadModel>();
    protected Set<OnObtainPropsListener> onObtainPropsListeners = new LinkedHashSet<>();

    @Nullable
    public static IPropsService getInstance() {
        if (instance == null) {
            synchronized (IPropsService.class) {
                if (instance == null) {
                    instance = ARouterUtils.getPropsManagerService();
                }
            }
        }
        if (instance == null) {
            Log.e(TAG, "getInstance: IPropsService获取为空");
        }
        return instance;
    }


    /**
     * 获取道具列表 获取成功回调
     */
    public abstract void obtainPropsList();
    /**
     * 获取下载道具Model
     * @param bid 当前书籍 bid
     * @param chapterInfoList 章节信息列表
     * @param downloadModelListener 回调
     */
    public abstract void obtainPropsDownloadModel(String bid, List<ChapterInfo> chapterInfoList, @Nullable OnPropsDownloadModelListener downloadModelListener);

    /**
     * 获取 PropsDownloadModel 缓存
     * @return
     */
    public abstract ConcurrentHashMap<String, PropsDownloadModel> getPropsDownloadModelCache();

    /**
     * 根据bid清除指定缓存
     * @return
     */
    public abstract PropsDownloadModel removePropsDownloadModelCacheById(String bid);

    /**
     * 检查是否含有免广告 道具
     * @return
     */
    public abstract boolean isHasUnusedRemovedAdProps();

    /**
     * 检查是否免除广告
     * @return
     */
    public abstract boolean isRemoveAd();
    /**
     * 获取免广告时间
     * @return
     */
    public abstract String getRemoveAdTime();

    public abstract void requestUseProps(UsePropsRequestBean requestBean, OnUsePropsListener listener);

    /**
     * 清空道具列表
     */
    public abstract void clearPropsList();

    /**
     * 清除广告道具列表
     */
    public abstract void clearRemoveAdPropsList();

    /**
     * 启动网络连接成功监听
     */
    public abstract void startNetStateListener();

    /**
     * 获取道具列表的监听
     */
    public interface OnObtainPropsListener {
        void onObtainSuccess();
    }

    /**
     * 必须与 remove 成对出现，否则可能会造成内存泄漏！！！！！
     * @param listener
     */
    public void addOnObtainPropsListener(OnObtainPropsListener listener) {
        onObtainPropsListeners.add(listener);
    }

    public void removeOnObtainPropsListener(OnObtainPropsListener listener) {
        onObtainPropsListeners.remove(listener);
    }

    /**
     * 下载道具Model 获取成功回调
     */
    public interface OnPropsDownloadModelListener {
        void onGetPropsDownloadModel(PropsDownloadModel propsDownloadModel);
    }

    /**
     * 道具使用回调
     */
    public interface OnUsePropsListener{
        void onUserPropsListener(int code);
    }

}
