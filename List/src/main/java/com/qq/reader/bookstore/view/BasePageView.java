package com.qq.reader.bookstore.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.lulu.basic.skin.SkinManager;
import com.lulu.skin.ISkinUpdateListener;
import com.qq.reader.bookstore.BookStoreUtil;
import com.qq.reader.bookstore.define.BookStoreViewParams;

import java.util.HashSet;
import java.util.Set;

/**
 * 承载 Fragment 中所有 View
 * @author zhanglulu
 */
public abstract class BasePageView implements ISkinUpdateListener {

    protected Context context;

    public LinearLayoutManager layoutManager;
    public View contentView;
    public RecyclerView recyclerView;
    public View loadingView;
    public View dataErrorView;
    public View netErrorView;
    public View actionBarBackView;
    public TextView actionBarTitle;
    public View actionBarContainer;
    public LoadMoreView loadMoreView;
    private Set<View> stateViewSet = new HashSet<>();

    //下拉刷新
    public SwipeRefreshLayout pullDownView;
    private final BookStoreViewParams params;

    public BasePageView(Context context) {
        params = onCreateParams();
        this.context = context;
        this.contentView = LayoutInflater.from(context).inflate(params.getContentViewLayoutRes(), null);
    }

    public void createView() {
        initView(contentView);
        initStatViewSet();
        showMutexStateView(loadingView);
        onCreateView(contentView);
    }

    private void initView(View contentView) {
        recyclerView = (RecyclerView) contentView.findViewById(params.getRecyclerViewIdRes());
        if (recyclerView == null) {
            throw new RuntimeException("mRecyclerView 为空，请指定正确的 getRecyclerViewIdRes()");
        }
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        loadingView = contentView.findViewById(params.getLoadingViewIdRes());
        dataErrorView = contentView.findViewById(params.getDataErrorViewIdRes());
        netErrorView = contentView.findViewById(params.getDataErrorViewIdRes());
        actionBarBackView = contentView.findViewById(params.getActionBarBackViewIdRes());
        actionBarTitle = BookStoreUtil.findViewByIdCheckNull(contentView, params.getActionBarTitleViewIdRes(), TextView.class);
        actionBarContainer = BookStoreUtil.findViewByIdCheckNull(contentView, params.getActionBarContainerIdRes(), View.class);
        pullDownView = BookStoreUtil.findViewByIdCheckNull(contentView, params.getPullDownViewIdRes(), SwipeRefreshLayout.class);
        loadMoreView = params.getLoadMoreView();
    }

    private void initStatViewSet() {
        putStateViewSetCheckNull(loadingView);
        putStateViewSetCheckNull(dataErrorView);
        putStateViewSetCheckNull(netErrorView);
        putStateViewSetCheckNull(recyclerView);
    }

    private void putStateViewSetCheckNull(View view) {
        if (view != null) {
            stateViewSet.add(view);
        }
    }

    public View getContentView() {
        return contentView;
    }

    public void setEnablePullDownRefresh(boolean enablePullDownRefresh) {
        pullDownView.setEnabled(enablePullDownRefresh);
    }

    public boolean isRefreshing() {
        return pullDownView.isRefreshing();
    }

    @Override
    public void onSkinUpdate() {
        //使用皮肤包
        pullDownView.setColorSchemeColors(SkinManager.get().getColor("primaryLightColor"));
    }

    //----------------------------------------------------------------------------------------------
    // 其他工具方法

    /**
     * 互斥展示 View
     */
    public void showMutexStateView(View targetView) {
        for (View view : stateViewSet) {
            view.setVisibility(view == targetView ? View.VISIBLE: View.GONE);
        }
    }

    //----------------------------------------------------------------------------------------------
    // 子类实现方法

    /**
     * 用于子类创建其他自定义控件
     * @param contentView
     */
    abstract public void onCreateView(@NonNull View contentView);

    /**
     * 提供资源参数 {@link BookStoreViewParams.Builder}
     * @return
     */
    abstract public BookStoreViewParams onCreateParams();

}
