package com.yuewen.reader.zebra;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;
import com.yuewen.reader.zebra.log.Logger;
import com.yuewen.reader.zebra.utils.ZebraUtil;

import java.lang.ref.WeakReference;

/**
 * Created by zhanglulu on 2019/3/3.
 * for View 绑定 Item
 */
public abstract class BaseViewBindItem<T, Holder extends RecyclerView.ViewHolder> {

    private static final String TAG = "BaseDataItem";

    /**
     * 当前 Item 多对应的 Zebra
     */
    protected Zebra<?> zebra;

    /**
     * 当前 Item 数据
     */
    protected T itemData;

    /**
     * RecyclerView Holder
     */
    private WeakReference<Holder> holderWeakRef;

    /**
     * Item 在 List 中的索引
     * 默认修改为-1, 当-1时说明并没有显示出来
     */
    protected int index = -1;

    public BaseViewBindItem(T itemData) {
        this.itemData = itemData;
    }

    public void setIndex(int pos) {
        index = pos;
    }

    public void setZebra(Zebra<?> zebra) {
        this.zebra = zebra;
    }

    /**
     * 当前DataItem 是否显示
     */
    protected boolean isDisplay = true;

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    public int getIndex() {
        return index;
    }

    /**
     * 数据绑在view上
     *
     * @param holder
     */
    public void attachView(final Holder holder) throws Exception {
        holderWeakRef = new WeakReference<>(holder);

        if (holder.itemView.getVisibility() == View.GONE) {
            holder.itemView.setVisibility(View.VISIBLE);
        }
        Logger.d(TAG, "attachView: isDisplay: " + isDisplay + " mIndex:" + index);
        if (isDisplay){
            //只有当前页面显示才会曝光
            try {
                exposeDataItem();
            } catch (Exception e) {
                Logger.e(TAG, "exposeDataItem 抛出异常！" + this);
                e.printStackTrace();
            }
        }
        if (itemData == null) {
            throw new RuntimeException("检查 ViewBindItemBuilder 是否已经调用 setData ???");
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
        if (holderWeakRef != null && isSupportHideItem()) {
            Holder holder = holderWeakRef.get();
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
        itemData = bean;
    }

    /**
     * 获取数据
     *
     * @param
     */
    public T getData() {
        return itemData;
    }

    /**
     * 获取当前的 Activity
     */
    @Nullable
    private Activity getActivity() {
        Activity activity = null;
        if (holderWeakRef == null) {
            return null;
        }
        Holder baseViewHolder = holderWeakRef.get();
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
    private SparseArray statInfo;

    public void setStatInfo(SparseArray mStatInfo) {
        this.statInfo = mStatInfo;
    }

    public SparseArray getStatInfo() {
        return statInfo;
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
