package com.qq.reader.provider.listpage;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.loader.ObserverEntity;
import com.example.providermoduledemo.QuickRecyclerViewAdapter;
import com.qq.reader.provider.viewmodel.CommonViewHolder;

import java.util.List;

/**
 * 页面列表加载辅助类
 * 针对 DataProvider 框架上层的封装 View 层
 */
abstract public class BaseListPageView implements BaseQuickAdapter.RequestLoadMoreListener, Observer<ObserverEntity>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "PageListLoadHelper";

    protected Context context;
    protected RecyclerView mRecyclerView;
    protected BaseQuickAdapter<BaseViewBindItem, CommonViewHolder> mAdapter;
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

    //下拉刷新
    protected SwipeRefreshLayout mPullDownView;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    //Load More
    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener;

    private ListPageResParams listPageResParams;

    public BaseListPageView(Context context) {
        listPageResParams = getListPageResParams();
        this.context = context;
        this.contentView = LayoutInflater.from(context).inflate(listPageResParams.getContentViewLayoutRes(), null);
        createView(contentView);
        onCreateView(contentView);
    }

    protected void onCreateView(View contentView) {

    }


    protected void createView(View contentView) {
        mRecyclerView = (RecyclerView) contentView.findViewById(listPageResParams.getRecyclerViewIdRes());
        if (mRecyclerView == null) {
            throw new RuntimeException("mRecyclerView 为空，请指定正确的 getRecyclerViewIdRes()");
        }
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = initAdapter();
        mLoadMoreView = getLoadMoreView();
        mAdapter.setLoadMoreView(mLoadMoreView);
        mRecyclerView.setAdapter(mAdapter);

        mLoadingView = contentView.findViewById(listPageResParams.getLoadingViewIdRes());
        mDataErrorView = contentView.findViewById(listPageResParams.getDataErrorViewIdRes());
        mPullDownView = (SwipeRefreshLayout) contentView.findViewById(listPageResParams.getPullDownViewIdRes());
        mPullDownView.setOnRefreshListener(this);
        mPullDownView.setEnabled(false);
        showLoadingView();
        hideDataErrorView();
    }

    protected BaseQuickAdapter<BaseViewBindItem, CommonViewHolder> initAdapter() {
        return new QuickRecyclerViewAdapter(context, null);
    }

    public View getContentView() {
        return contentView;
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
        if (requestLoadMoreListener != null) {
            requestLoadMoreListener.onLoadMoreRequested();
        }
    }

    public LoadMoreView getLoadMoreView() {
        return new SimpleLoadMoreView();
    }

    public void setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        this.requestLoadMoreListener = requestLoadMoreListener;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        mAdapter.setEnableLoadMore(enableLoadMore);
        if (!enableLoadMore) {
            mAdapter.setOnLoadMoreListener(null, mRecyclerView);
        }
    }

    //----------------------------------------------------------------------------------------------
    // OnRefreshListener 下拉刷新
    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: 调用");
        mRecyclerViewState = STATE_DOWN_REFRESH;
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        setEnablePullDownRefresh(true);
        this.refreshListener = refreshListener;
    }

    public void setEnablePullDownRefresh(boolean enablePullDownRefresh) {
        mPullDownView.setEnabled(enablePullDownRefresh);
    }

    public boolean isRefreshing() {
        return mPullDownView.isRefreshing();
    }

    //----------------------------------------------------------------------------------------------
    // 数据回调处理
    @Override
    public void onChanged(ObserverEntity entity) {
        if (context == null) {
            return;
        }
        if (entity.isSuccess()) {
            List<BaseViewBindItem> viewBindItems = entity.provider.getViewBindItems();
            if (viewBindItems == null) {
                Log.e(TAG, "onChanged: viewBindItems == null");
                showDataErrorView();
                return;
            }
            if (mRecyclerViewState == STATE_ENTER_INIT || mRecyclerViewState == STATE_DOWN_REFRESH) {
                if (viewBindItems.isEmpty()) {
                    showDataErrorView();
                } else {
                    mAdapter.setNewData(viewBindItems);
                    mAdapter.loadMoreComplete();
                }
            } else if (mRecyclerViewState == STATE_UP_LOAD_MORE) {
                if (viewBindItems.isEmpty()) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.addData(viewBindItems);
                    mAdapter.loadMoreComplete();
                }
            }
            hideDataErrorView();
        } else {
            if (mRecyclerViewState == STATE_ENTER_INIT || mRecyclerViewState == STATE_DOWN_REFRESH) {
                showDataErrorView();
            } else {
                mAdapter.loadMoreFail();
            }
            Log.e(TAG, "onChanged: entity.isSuccess() false ");
        }
        hideLoadingView();
        mPullDownView.setRefreshing(false);
    }

    //----------------------------------------------------------------------------------------------
    // 抽象方法

    /**
     * 提供资源参数 {@link ListPageResParams.Builder}
     * @return
     */
    abstract public ListPageResParams getListPageResParams();
}
