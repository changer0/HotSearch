package com.example.providermoduledemo.sample;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.providermoduledemo.build.PageBuilderParams;
import com.qq.reader.provider.build.IPage;
import com.qq.reader.provider.build.PageManger;
import com.qq.reader.provider.build.PageInfo;
import com.qq.reader.provider.listpage.BaseListPageView;
import com.qq.reader.provider.listpage.SimpleListPageView;

/**
 * 通用二级页 Fragment 示例页面
 */
public class SampleCommonSecondPageFragment extends Fragment {

    public static String PAGE_BUILDER_TYPE = "PAGE_BUILDER_TYPE";

    private static final String TAG = "SampleListPageActivity";

    private BaseListPageView simpleListPageView;

    private int curIndex = 1;

    private IPage pageBuilder;
    private PageInfo pageInfo;

    private SampleCommonSecondPageFragment() {
    }

    public static SampleCommonSecondPageFragment newInstance(String pageBuilderType) {
        SampleCommonSecondPageFragment fragment = new SampleCommonSecondPageFragment();
        Bundle args = new Bundle();
        args.putString(PAGE_BUILDER_TYPE, pageBuilderType);
        fragment.setArguments(args);
        return fragment;
    }

    public static SampleCommonSecondPageFragment newInstance(Bundle bundle) {
        SampleCommonSecondPageFragment fragment = new SampleCommonSecondPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        simpleListPageView = getListPageView();
        return simpleListPageView.getContentView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPageBuilder();
        initLoadData();
    }

    /**
     * 初始化加载数据
     */
    private void initLoadData() {
        curIndex = pageInfo.getStartIndex();
        loadData(curIndex);
        simpleListPageView.setEnableLoadMore(pageInfo.isEnableLoadMore());
        simpleListPageView.setEnablePullDownRefresh(pageInfo.isEnablePullDownRefresh());
        if (pageInfo.isEnableLoadMore()) {
            simpleListPageView.setOnLoadMoreListener(() -> {
                curIndex++;
                loadData(curIndex);
            });
        }
        if (pageInfo.isEnablePullDownRefresh()) {
            simpleListPageView.setOnRefreshListener(() -> {
                curIndex = pageInfo.getStartIndex();
                loadData(curIndex);
            });
        }
    }

    private void loadData(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(PageBuilderParams.PAGE_INDEX, index);
        pageBuilder.loadPageData(bundle).observe(this, simpleListPageView);
    }

    private void initPageBuilder() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new NullPointerException(SampleCommonSecondPageFragment.class.getSimpleName() + " 启动该 Fragment 前需要传入 PROVIDER_BUILDER_TYPE");
        }
        String pageType = bundle.getString(PAGE_BUILDER_TYPE);
        if (TextUtils.isEmpty(pageType)) {
            throw new NullPointerException(SampleCommonSecondPageFragment.class.getSimpleName() + " 启动该 Fragment 前需要传入 PROVIDER_BUILDER_TYPE");
        }
        pageBuilder = PageManger.getInstance(context.getClassLoader()).getPageBuilder(pageType);
        if (pageBuilder == null) {
            throw new NullPointerException(SampleCommonSecondPageFragment.class.getSimpleName() + "Page 类型：" + pageType + "获取为空，请检查注解配置！");
        }
        pageInfo = pageBuilder.buildPageInfo();
    }
    protected BaseListPageView getListPageView() {
        if (simpleListPageView != null) {
            return simpleListPageView;
        }
        return new SimpleListPageView(getContext());
    }


}
