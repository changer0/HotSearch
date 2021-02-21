package com.qq.reader.bookstore;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qq.reader.bookstore.define.LoadSignal;
import com.qq.reader.bookstore.view.BaseBookStoreView;
import com.lulu.basic.fragment.BaseFragment;
import com.yuewen.reader.zebra.loader.ObserverEntity;
import com.yuewen.reader.zebra.log.Logger;

import org.jetbrains.annotations.NotNull;

/**
 * 开发者需遵循的设计规范
 *
 *                      /  BookStoreView
 * BaseBookStoreFragment
 *                      \  BookStoreViewModel
 *
 * 请不要尝试修改该类来满足你的需求 !!!
 *
 * @author zhanglulu
 */
public abstract class BaseBookStoreFragment<V extends BaseBookStoreView,
        VM extends BaseBookStoreViewModel> extends BaseFragment implements Observer<ObserverEntity>,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    protected Context mContext;
    protected V mBookStoreView;
    protected VM mViewModel;
    public QuickRecyclerViewAdapter mAdapter;
    protected Bundle mEnterBundle;
    private boolean isFrameworkReady;
    protected LaunchParams mLaunchParams;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBookStoreView = onCreateBookStoreView();
        mBookStoreView.createView();
        initUI();
        return mBookStoreView.getContentView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean initSuccess = initEnterData(savedInstanceState);
        if (initSuccess) {
            isFrameworkReady = true;
            try {
                launchSuccess(view, savedInstanceState);
            } catch (Exception e) {
                launchFailed(e.getMessage());
                e.printStackTrace();
            }
        } else {
            launchFailed("initEnterData 失败");
        }
    }

    //----------------------------------------------------------------------------------------------
    // Fragment 配置
    protected boolean initEnterData(@Nullable Bundle savedEnterBundle) {
        mEnterBundle = getArguments();
        if (mEnterBundle == null) {
            //状态恢复而来! 需要注意的是,这种状态只针对 Activity 销毁的场景 ! 不针对 View 销毁的场景
            //https://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
            if (savedEnterBundle != null) {
                mEnterBundle = savedEnterBundle;
                if (mLaunchParams == null) {
                    mLaunchParams = mEnterBundle.getParcelable(BookStoreActivityLauncher.BOOK_STORE_FRAGMENT_PARAMS);
                }
            }
        } else {
            mLaunchParams = mEnterBundle.getParcelable(BookStoreActivityLauncher.BOOK_STORE_FRAGMENT_PARAMS);
        }
        return mEnterBundle != null && mLaunchParams != null;
    }

    /**
     * Fragment 首次启动失败
     */
    protected void launchFailed(String msg) {
        mBookStoreView.showMutexStateView(mBookStoreView.dataErrorView);
        if (BuildConfig.DEBUG) {
            Toast.makeText(mContext, "启动 " + this.getClass().getName() + " 发生错误: " + msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Fragment 启动成功
     */
    protected void launchSuccess(@NonNull View view, @Nullable Bundle savedInstanceState) {
        analyzingFragmentArguments();
        mViewModel = new ViewModelProvider(this).get(onCreateBookStoreViewModel(mEnterBundle));
        onLaunchSuccess(view, mEnterBundle, savedInstanceState);
    }

    // Fragment 配置 end
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // UI 配置

    /**
     * UI 初始化
     */
    protected void initUI() {
        if (mAdapter != null) {
            return;
        }
        mAdapter = new QuickRecyclerViewAdapter(mContext, null);
        mAdapter.setLoadMoreView(mBookStoreView.loadMoreView);
        mBookStoreView.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mBookStoreView.recyclerView.setAdapter(mAdapter);
        mBookStoreView.pullDownView.setOnRefreshListener(this);
    }

    /**
     * 解析 Fragment 参数
     */
    protected void analyzingFragmentArguments() {
        mBookStoreView.pullDownView.setEnabled(mLaunchParams.isPullRefreshEnable());
        mAdapter.setEnableLoadMore(mLaunchParams.isLoadMoreEnable());
        if (mLaunchParams.isLoadMoreEnable()) {
            mAdapter.setOnLoadMoreListener(this, mBookStoreView.recyclerView);
        }
        configActionBar();
    }

    /**
     * ActionBar 的配置
     */
    protected void configActionBar() {
        if (mBookStoreView.actionBarBackView != null) {
            mBookStoreView.actionBarBackView.setOnClickListener(v -> {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.finish();
                }
            });
        }
        if (mBookStoreView.actionBarContainer != null
                && mBookStoreView.actionBarTitle != null) {
            if (!TextUtils.isEmpty(mLaunchParams.getTitle())) {
                mBookStoreView.actionBarTitle.setText(mLaunchParams.getTitle());
                mBookStoreView.actionBarContainer.setVisibility(View.VISIBLE);
            } else {
                mBookStoreView.actionBarContainer.setVisibility(View.GONE);
            }
        }
    }

    // UI 配置 end
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // 数据加载工具
    /**
     * 数据加载
     */
    public void loadData(int loadSignal) {
        if (mEnterBundle == null) {
            return;
        }
        Bundle params = LoadSignal.generateLoadBundle(loadSignal, mEnterBundle);
        mViewModel.getZebraLiveData(params).observe(this, this);
    }



    // 数据加载工具 end
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // 拉取信号触发

    @Override
    public void onRefresh() {
        loadData(LoadSignal.LOAD_SIGNAL_REFRESH);
    }

    @Override
    public void onLoadMoreRequested() {
        loadData(LoadSignal.LOAD_SIGNAL_MORE);
    }

    // 拉取信号触发
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // 数据回调

    @Override
    public void onChanged(ObserverEntity entity) {
        if (mContext == null || !isFrameworkReady()) {
            return;
        }
        mBookStoreView.pullDownView.setRefreshing(false);
        int loadSignal = entity.zebra.getLoadSignal();
        Logger.d("onChanged", "是否为缓存: " + entity.zebra.isCache());
        switch (loadSignal) {
            case LoadSignal.LOAD_SIGNAL_INIT:
                onDataInit(entity);
                break;
            case LoadSignal.LOAD_SIGNAL_REFRESH:
                onDataRefresh(entity);
                break;
            case LoadSignal.LOAD_SIGNAL_MORE:
                onDataAddMore(entity);
                break;
        }
    }

    /**
     * 数据 Init 状态回调
     * @param entity 数据实体
     */
    public void onDataInit(ObserverEntity entity) {
        if(!entity.isSuccess()
                || entity.zebra.getViewBindItems() == null
                || entity.zebra.getViewBindItems().isEmpty()) {
            mBookStoreView.showMutexStateView(mBookStoreView.dataErrorView);
        } else {
            mAdapter.setNewData(entity.zebra.getViewBindItems());
            mAdapter.loadMoreComplete();
            mBookStoreView.showMutexStateView(mBookStoreView.recyclerView);
        }
    }

    /**
     * 数据 Refresh 状态回调
     * @param entity 数据实体
     */
    public void onDataRefresh(ObserverEntity entity) {
        //默认 refresh 状态与 Init 等同
        onDataInit(entity);
    }

    /**
     * 数据 AddMore 状态回调
     * @param entity 数据实体
     */
    public void onDataAddMore(ObserverEntity entity) {
        if (entity.isSuccess()) {
            if (entity.zebra.getViewBindItems() == null
                    || entity.zebra.getViewBindItems().isEmpty()) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.addData(entity.zebra.getViewBindItems());
                mAdapter.loadMoreComplete();
            }
        } else {
            mAdapter.loadMoreFail();
        }
    }

    // 数据回调 end
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // 其他工具方法

    public boolean isFrameworkReady() {
        return isFrameworkReady;
    }

    // 其他工具方法 end
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // BaseFragment 抽象方法
    @Override
    public void onPreLoad() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFinished() {

    }
    // BaseFragment 抽象方法 end
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // 子类提供方法

    /**
     * 提供一个书城专用 View
     */
    abstract protected V onCreateBookStoreView();

    /**
     * 提供一个书城专用 ViewModel
     * @param enterBundle
     */
    abstract protected Class<VM> onCreateBookStoreViewModel(@NonNull Bundle enterBundle);

    /**
     * Fragment 成功启动, 交互逻辑可在后续处理
     */
    protected abstract void onLaunchSuccess(@NonNull View container,@NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState);
}
