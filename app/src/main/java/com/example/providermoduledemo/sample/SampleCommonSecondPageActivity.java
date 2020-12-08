package com.example.providermoduledemo.sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.providermoduledemo.build.PageBuilderParams;
import com.qq.reader.provider.build.PageConfigInfo;
import com.qq.reader.provider.listpage.SimpleListPageView;
import com.qq.reader.provider.build.IPageBuilder;
import com.qq.reader.provider.build.PageBuilderManger;

/**
 * 通用二级页 示例页面
 */
public class SampleCommonSecondPageActivity extends AppCompatActivity {

    public static String PROVIDER_BUILDER_TYPE = "PROVIDER_BUILDER_TYPE";

    private static final String TAG = "SampleListPageActivity";

    private SimpleListPageView simpleListPageView;

    private int curIndex = 1;

    private IPageBuilder providerBuilder;
    private PageConfigInfo pageConfigInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleListPageView = new SimpleListPageView(this);
        setContentView(simpleListPageView.getContentView());
        initProviderBuilder();
        initLoadData();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pageConfigInfo.getTitleName());
        }
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
        providerBuilder.buildProvider(bundle).observe(this, simpleListPageView);
    }

    private void initProviderBuilder() {
        Intent intent = getIntent();
        String providerType = intent.getStringExtra(PROVIDER_BUILDER_TYPE);
        if (TextUtils.isEmpty(providerType)) {
            throw new NullPointerException(SampleCommonSecondPageActivity.class.getSimpleName() + " 启动该 Activity 前需要传入 PROVIDER_BUILDER_TYPE");
        }
        providerBuilder = PageBuilderManger.getInstance(getClassLoader()).getPageBuilder(providerType);
        if (providerBuilder == null) {
            throw new NullPointerException(SampleCommonSecondPageActivity.class.getSimpleName() + "Provider Builder 类型：" + providerType + "获取为空，请检查注解配置！");
        }
        pageConfigInfo = providerBuilder.buildPageConfigInfo();
    }

}
