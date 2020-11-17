package com.qq.reader.provider;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.qq.reader.provider.loader.ObserverEntity;
import com.qq.reader.provider.viewmodel.SimpleRecyclerViewAdapter;

import java.util.List;

/**
 * 页面列表加载辅助类
 * 针对 DataProvider 框架上层的封装 View 层
 */
abstract public class BaseListPageView implements BaseQuickAdapter.RequestLoadMoreListener, Observer<ObserverEntity> {
    private static final String TAG = "PageListLoadHelper";

    protected Context context;
    protected RecyclerView mRecyclerView;
    protected BaseQuickAdapter<BaseViewBindItem, BaseViewHolder> mAdapter;
    protected LinearLayoutManager mLayoutManager;
    //进入时调用
    protected static final int STATE_ENTER_INIT = 0;
    //下拉刷新
    protected static final int STATE_DOWN_REFRESH = 1;
    //上拉加载
    protected static final int STATE_UP_LOAD_MORE = 2;
    protected int mRecyclerViewState = STATE_ENTER_INIT;

    private View contentView;
    protected LoadMoreView mLoadMoreView;
    protected View mLoadingView;
    protected View mDataErrorView;

    //Load More
    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener;
    private boolean enableLoadMore = true;

    public BaseListPageView(Context context) {
        this.context = context;
        this.contentView = LayoutInflater.from(context).inflate(getContentViewLayoutRes(), null);
        onCreateView(contentView);
    }


    protected void onCreateView(View contentView) {
        mRecyclerView = (RecyclerView) contentView.findViewById(getRecyclerViewIdRes());
        if (mRecyclerView == null) {
            throw new RuntimeException("mRecyclerView 为空，请指定正确的 getRecyclerViewIdRes()");
        }
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = initAdapter();
        mLoadMoreView = getLoadMoreView();
        mAdapter.setLoadMoreView(mLoadMoreView);
        mAdapter.setEnableLoadMore(enableLoadMore);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mLoadingView = contentView.findViewById(getLoadingViewIdRes());
        mDataErrorView = contentView.findViewById(getDataErrorViewIdRes());

        showLoadingView();
        hideDataErrorView();
    }

    protected BaseQuickAdapter<BaseViewBindItem, BaseViewHolder> initAdapter() {
        return new SimpleRecyclerViewAdapter(context, null);
    }

    public View getContentView() {
        return contentView;
    }

    //----------------------------------------------------------------------------------------------
    // Loading More View 控制
    public LoadMoreView getLoadMoreView() {
        return new SimpleLoadMoreView();
    }

    public void setRequestLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        this.requestLoadMoreListener = requestLoadMoreListener;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
        mAdapter.setEnableLoadMore(enableLoadMore);
    }

    //----------------------------------------------------------------------------------------------
    // Net Error View 控制
    public void showDataErrorView() {
        if (mDataErrorView != null) {
            mDataErrorView.setVisibility(View.VISIBLE);
        }
    }

    public void showNetErrorView() {
        if (mDataErrorView != null) {
            mDataErrorView.setVisibility(View.VISIBLE);
        }
    }

    public void hideDataErrorView() {
        if (mDataErrorView != null) {
            mDataErrorView.setVisibility(View.GONE);
        }
    }

    public boolean isShowDataErrorView() {
        if (mDataErrorView != null) {
            return mDataErrorView.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------
    // Loading View 控制
    public void showLoadingView(){
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    //----------------------------------------------------------------------------------------------
    // BaseQuickAdapter.RequestLoadMoreListener 上拉加载更多
    @Override
    public void onLoadMoreRequested() {
        Log.d(TAG, "onLoadMoreRequested: 调用");
        mRecyclerViewState = STATE_UP_LOAD_MORE;
        requestLoadMoreListener.onLoadMoreRequested();
    }

    //----------------------------------------------------------------------------------------------
    // OnRefreshListener 下拉刷新
    public void onRefresh() {
        Log.d(TAG, "onRefresh: 调用");
        mRecyclerViewState = STATE_DOWN_REFRESH;
    }


    //----------------------------------------------------------------------------------------------
    // 数据回调处理
    @Override
    @SuppressWarnings("unchecked")
    public void onChanged(ObserverEntity entity) {
        if (context == null) {
            return;
        }
        if (entity.isSuccess()) {
            List<BaseViewBindItem> viewBindItems = entity.provider.getViewBindItems();
            if (viewBindItems == null) {
                showDataErrorView();
                return;
            }
            if (mRecyclerViewState == STATE_ENTER_INIT) {
                mAdapter.setNewData(viewBindItems);
                hideLoadingView();
            } else if (mRecyclerViewState == STATE_UP_LOAD_MORE) {
                mAdapter.addData(viewBindItems);
            }
            mAdapter.loadMoreComplete();
        } else {
            showDataErrorView();
        }
    }

    //----------------------------------------------------------------------------------------------
    // 抽象方法
    abstract public @IdRes int getRecyclerViewIdRes();
    abstract public @IdRes int getLoadingViewIdRes();
    abstract public @IdRes int getDataErrorViewIdRes();
    abstract public @LayoutRes int getContentViewLayoutRes();
}
