package com.qq.reader.zebra;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;
import com.qq.reader.zebra.log.Logger;
import com.qq.reader.zebra.utils.ZebraUtil;

import java.lang.ref.WeakReference;

/**
 * Created by zhanglulu on 2019/3/3.
 * for View 绑定 Item
 */
public abstract class BaseViewBindItem<T, Holder extends RecyclerView.ViewHolder> {

    private static final String TAG = "BaseDataItem";

    protected T mItemData;

    /**
     * RecyclerView Holder
     */
    private WeakReference<Holder> mHolderWeakRef;

    /**
     * Item 在 List 中的索引
     * 默认修改为-1, 当-1时说明并没有显示出来
     */
    protected int mIndex = -1;

    public BaseViewBindItem() {

    }

    public void setIndex(int pos) {
        mIndex = pos;
    }

    /**
     * 当前DataItem 是否显示
     */
    protected boolean isDisplay = true;

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    public int getIndex() {
        return mIndex;
    }

    /**
     * 数据绑在view上
     *
     * @param holder
     */
    public void attachView(final Holder holder) throws Exception {
        mHolderWeakRef = new WeakReference<>(holder);

        if (holder.itemView.getVisibility() == View.GONE) {
            holder.itemView.setVisibility(View.VISIBLE);
        }
        Logger.d(TAG, "attachView: isDisplay: " + isDisplay + " mIndex:" + mIndex);
        if (isDisplay){
            //只有当前页面显示才会曝光
            try {
                exposeDataItem();
            } catch (Exception e) {
                Logger.e(TAG, "exposeDataItem 抛出异常！" + this);
                e.printStackTrace();
            }
        }
        Activity activity = getActivity();
        if (activity == null || !bindView(holder, activity)) {
            //如果没有正常处理, 直接隐藏
            holder.itemView.setVisibility(View.GONE);
            hideItemView();
            if (BuildConfig.DEBUG) {
                Toast.makeText(ZebraConfig.getApplication(), "ViewBindItem 加载未加载成功： " + this, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 隐藏 Item View 布局 <br/>
     * 这样隐藏在支持加载更多的页面会有持续加载的问题 <br/>
     * 根据不同业务需求来判断是否需要直接隐藏单个 ViewBindItem <br/>
     * {@link #hideItemView()}
     */
    public boolean isSupportHideItem() {
        return false;
    }

    /**
     * 隐藏 Item View 布局 <br/>
     * 这样隐藏在支持加载更多的页面会有持续加载的问题 <br/>
     * 根据不同业务需求来判断是否需要直接隐藏单个 ViewBindItem <br/>
     * {@link #isSupportHideItem()}
     */
    public void hideItemView() {
        if (mHolderWeakRef != null && isSupportHideItem()) {
            Holder holder = mHolderWeakRef.get();
            if (holder != null) {
                RecyclerView.LayoutParams param = new RecyclerView.LayoutParams(0,0);
                holder.itemView.setLayoutParams(param);
            }
        }
    }

    /**
     * 设置数据
     *
     * @param
     */
    public void setData(T bean ){
        mItemData = bean;
    }

    /**
     * 获取数据
     *
     * @param
     */
    public T getData() {
        return mItemData;
    }

    /**
     * 获取当前的 Activity
     */
    @Nullable
    private Activity getActivity() {
        Activity activity = null;
        if (mHolderWeakRef == null) {
            return null;
        }
        Holder baseViewHolder = mHolderWeakRef.get();
        if (baseViewHolder == null) {
            return null;
        }
        //之前的代码，后续看看这个位置会有什么异常
        //Context context = baseViewHolder.getAdapterContext();
        //如果没有使用 RecyclerView 则为空
        if (baseViewHolder.itemView == null) {
            return null;
        }
        Context context = baseViewHolder.itemView.getContext();
        activity = ZebraUtil.getActivity(context);
        if (activity == null) {
            if (BuildConfig.DEBUG) {
                String errorTip = "请使用 Activity 的 Context：" + getClass().getName();
                Toast.makeText(ZebraConfig.getApplication(), errorTip, Toast.LENGTH_SHORT).show();
                Logger.e(TAG, "getActivity: " + errorTip);
            }
        }
        return activity;
    }

    //----------------------------------------------------------------------------------------------
    //上报相关

    /**
     * 用于上报的信息
     */
    private SparseArray mStatInfo;

    public void setStatInfo(SparseArray mStatInfo) {
        this.mStatInfo = mStatInfo;
    }

    public SparseArray getStatInfo() {
        return mStatInfo;
    }

    /**
     * 当前 ViewBindItem 曝光时机 <br/>
     * 务必注意, 这个方法只用于当前DataItem 曝光上报使用, #### 禁止任何耗时操作 ####
     */
    public void exposeDataItem () {
        Logger.d(TAG, "exposeDataItem: 当前 ViewBindItem 曝光: " + getClass().getSimpleName());
    }

    //----------------------------------------------------------------------------------------------
    //抽象方法
    /**
     * @return 该栏目块儿所对应的布局layout id
     */
    @LayoutRes
    public abstract int getResLayoutId();

    /**
     * 数据绑定
     */
    public abstract boolean bindView(@NonNull Holder holder, @NonNull Activity activity) throws Exception;

}
