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

import com.example.providermoduledemo.PageLoadSignal;
import com.example.providermoduledemo.build.BookStoreConstants;
import com.qq.reader.provider.listpage.BaseListPageView;
import com.qq.reader.provider.listpage.SimpleListPageView;

/**
 * 通用二级页 Fragment 示例页面
 */
public class SampleCommonSecondPageFragment extends Fragment {

    private static final String TAG = "SampleListPageActivity";

    private BaseListPageView simpleListPageView;


    public SampleCommonSecondPageFragment() {
    }

    public static SampleCommonSecondPageFragment newInstance(String pageBuilderType) {
        SampleCommonSecondPageFragment fragment = new SampleCommonSecondPageFragment();
        Bundle args = new Bundle();
        args.putString(BookStoreConstants.PAGE_BUILDER_TYPE, pageBuilderType);
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
        loadData(PageLoadSignal.LOAD_STATE_INIT);
        simpleListPageView.setOnLoadMoreListener(() -> {
            loadData(PageLoadSignal.LOAD_STATE_MORE);
        });
        simpleListPageView.setOnRefreshListener(() -> {
            loadData(PageLoadSignal.LOAD_STATE_REFRESH);
        });
    }

    private void loadData(String loadState) {
        Bundle bundle = new Bundle();
        bundle.putString(PageLoadSignal.LOAD_STATE, loadState);
        //pageBuilder.loadPageData(bundle).observe(this, simpleListPageView);
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
        String pageType = bundle.getString(BookStoreConstants.PAGE_BUILDER_TYPE);
        if (TextUtils.isEmpty(pageType)) {
            throw new NullPointerException(SampleCommonSecondPageFragment.class.getSimpleName() + " 启动该 Fragment 前需要传入 PROVIDER_BUILDER_TYPE");
        }
        //pageBuilder = PageManger.getPage(pageType);
//        if (pageBuilder == null) {
//            throw new NullPointerException(SampleCommonSecondPageFragment.class.getSimpleName() + "Page 类型：" + pageType + "获取为空，请检查注解配置！");
//        }
    }
    protected BaseListPageView getListPageView() {
        if (simpleListPageView != null) {
            return simpleListPageView;
        }
        return new SimpleListPageView(getContext());
    }


}
