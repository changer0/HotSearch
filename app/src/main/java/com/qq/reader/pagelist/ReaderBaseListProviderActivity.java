package com.qq.reader.pagelist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.providermoduledemo.R;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.bean.BaseBean;
import com.qq.reader.view.EmptyView;
import com.qq.reader.view.ReaderLoadMoreView;
import com.qq.reader.widget.recyclerview.base.BaseQuickAdapter;
import com.qq.reader.widget.recyclerview.base.BaseViewHolder;

/**
 * @author zhanglulu on 2019/9/10.
 * for 通用的 加载列表的 Activity
 * 不要在里面耦合任何非公共业务 <br/>
 * 具体业务需求请在子类中实现
 */
@SuppressLint("Registered")
public abstract class ReaderBaseListProviderActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "ReaderBaseListProvider";
    protected RecyclerView mRecyclerView;
    protected BaseQuickAdapter<BaseViewBindItem<? extends BaseBean>, BaseViewHolder> mAdapter;
    protected LinearLayoutManager mLayoutManager;

    //进入时调用
    protected static final int STATE_ENTER_INIT = 0;
    //下拉刷新
    protected static final int STATE_DOWN_REFRESH = 1;
    //上拉加载
    protected static final int STATE_UP_LOAD_MORE = 2;
    protected int mRecyclerViewState = STATE_ENTER_INIT;

    protected ReaderLoadMoreView mLoadMoreView;

    protected View mLoadingView;
    protected EmptyView mDataErrorView;


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
            mAdapter.setReaderLoadMoreView(mLoadMoreView);
            mAdapter.setEnableLoadMore(true);
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
        }

        mLoadingView = findViewById(R.id.loading_view);
        mDataErrorView = (EmptyView) findViewById(R.id.data_error_view);

        showLoadingView();
        mDataErrorView.setButtonOnclick(v -> {
            dataErrorReload();
        });
        hideDataErrorView();
    }

    protected BaseQuickAdapter<BaseViewBindItem<? extends BaseBean>, BaseViewHolder> initAdapter() {
        return new NativeBookStoreAdapterForRecyclerView(this, null);
    }

    //----------------------------------------------------------------------------------------------
    // Loading More View 控制
    public ReaderLoadMoreView getLoadMoreView() {
        return new ReaderLoadMoreView();
    }

    //----------------------------------------------------------------------------------------------
    // Net Error View 控制
    public void showDataErrorView() {
        if (mDataErrorView != null) {
            mDataErrorView.setEmptyViewType(EmptyView.BUTTON_TEXT);
            mDataErrorView.setIcon(R.drawable.detail_load_failed);
            mDataErrorView.setContent(CommonUtility.getStringById(R.string.loading_data_fail_text));
            mDataErrorView.setVisibility(View.VISIBLE);
        }
    }

    public void showNetErrorView() {
        if (mDataErrorView != null) {
            mDataErrorView.setEmptyViewType(EmptyView.BUTTON_TWO_TEXT);
            mDataErrorView.setIcon(R.drawable.empty_net_failed);
            mDataErrorView.setContent(CommonUtility.getStringById(R.string.loading_fail_text));
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
