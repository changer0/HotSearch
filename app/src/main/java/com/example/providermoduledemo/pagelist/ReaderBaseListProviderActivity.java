package com.example.providermoduledemo.pagelist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.example.providermoduledemo.R;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.viewmodel.SimpleRecyclerViewAdapter;


/**
 * @author zhanglulu on 2019/9/10.
 * for 通用的 加载列表的 Activity
 * 不要在里面耦合任何非公共业务 <br/>
 * 具体业务需求请在子类中实现
 */
@SuppressWarnings("rawtypes")
@SuppressLint("Registered")
public abstract class ReaderBaseListProviderActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "ReaderBaseListProvider";
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

    protected LoadMoreView mLoadMoreView;

    protected View mLoadingView;
    protected View mDataErrorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutRes());
        initRecyclerView();
    }

    protected void initRecyclerView() {


        mRecyclerView = (RecyclerView) findViewById(R.id.refresh_target_view);
        if (mRecyclerView != null) {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = initAdapter();


            mLoadMoreView = getLoadMoreView();
            mAdapter.setLoadMoreView(mLoadMoreView);
            mAdapter.setEnableLoadMore(true);
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
        }

        mLoadingView = findViewById(R.id.loading_view);
        mDataErrorView =  findViewById(R.id.data_error_view);

        showLoadingView();
        mDataErrorView.setOnClickListener(v -> {
            dataErrorReload();
        });
        hideDataErrorView();
    }

    protected BaseQuickAdapter<BaseViewBindItem, BaseViewHolder> initAdapter() {
        return new SimpleRecyclerViewAdapter(this, null);
    }

    //----------------------------------------------------------------------------------------------
    // Loading More View 控制
    public LoadMoreView getLoadMoreView() {
        return new SimpleLoadMoreView();
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

    public void dataErrorReload() {
        //重新加载数据
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
    }


    //----------------------------------------------------------------------------------------------
    // OnRefreshListener 下拉刷新
    public void onRefresh() {
        Log.d(TAG, "onRefresh: 调用");
        mRecyclerViewState = STATE_DOWN_REFRESH;
    }

    //----------------------------------------------------------------------------------------------
    // 加载布局资源

    /**
     * ContentView 布局资源
     */
    @LayoutRes
    public int getContentViewLayoutRes() {
        return R.layout.common_recycle_layout;
    }
}
