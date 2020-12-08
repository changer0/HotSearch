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
import com.qq.reader.provider.build.IPageBuilder;
import com.qq.reader.provider.build.PageBuilderManger;
import com.qq.reader.provider.build.PageConfigInfo;
import com.qq.reader.provider.listpage.SimpleListPageView;

/**
 * 通用二级页 Fragment 示例页面
 */
public class SampleCommonSecondPageFragment extends Fragment {

    public static String PAGE_BUILDER_TYPE = "PAGE_BUILDER_TYPE";

    private static final String TAG = "SampleListPageActivity";

    private SimpleListPageView simpleListPageView;

    private int curIndex = 1;

    private IPageBuilder pageBuilder;
    private PageConfigInfo pageConfigInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        simpleListPageView = new SimpleListPageView(getContext());
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
        curIndex = pageConfigInfo.getStartIndex();
        loadData(curIndex);
        simpleListPageView.setEnableLoadMore(pageConfigInfo.isEnableLoadMore());
        simpleListPageView.setEnablePullDownRefresh(pageConfigInfo.isEnablePullDownRefresh());
        if (pageConfigInfo.isEnableLoadMore()) {
            simpleListPageView.setOnLoadMoreListener(() -> {
                curIndex++;
                loadData(curIndex);
            });
        }
        if (pageConfigInfo.isEnablePullDownRefresh()) {
            simpleListPageView.setOnRefreshListener(() -> {
                curIndex = pageConfigInfo.getStartIndex();
                loadData(curIndex);
            });
        }
    }

    private void loadData(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(PageBuilderParams.PAGE_INDEX, index);
        pageBuilder.buildProvider(bundle).observe(this, simpleListPageView);
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
        pageBuilder = PageBuilderManger.getInstance(context.getClassLoader()).getPageBuilder(pageType);
        if (pageBuilder == null) {
            throw new NullPointerException(SampleCommonSecondPageFragment.class.getSimpleName() + "Page 类型：" + pageType + "获取为空，请检查注解配置！");
        }
        pageConfigInfo = pageBuilder.buildPageConfigInfo();
    }

}
