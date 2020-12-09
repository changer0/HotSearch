package com.example.providermoduledemo.sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.providermoduledemo.build.PageBuilderParams;
import com.qq.reader.provider.build.IPage;
import com.qq.reader.provider.build.PageInfo;
import com.qq.reader.provider.listpage.BaseListPageView;
import com.qq.reader.provider.listpage.SimpleListPageView;
import com.qq.reader.provider.build.PageManger;

/**
 * 通用二级页 示例页面
 */
public class SampleCommonSecondPageActivity extends AppCompatActivity {

    public static String PAGE_BUILDER_TYPE = "PAGE_BUILDER_TYPE";

    private static final String TAG = "SampleListPageActivity";

    private BaseListPageView simpleListPageView;

    private int curIndex = 1;

    private IPage pageBuilder;
    private PageInfo pageInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleListPageView = getListPageView();
        setContentView(simpleListPageView.getContentView());
        initPageBuilder();
        initLoadData();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(pageInfo.getTitleName());
        }
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
        Intent intent = getIntent();
        String providerType = intent.getStringExtra(PAGE_BUILDER_TYPE);
        if (TextUtils.isEmpty(providerType)) {
            throw new NullPointerException(SampleCommonSecondPageActivity.class.getSimpleName() + " 启动该 Activity 前需要传入 PROVIDER_BUILDER_TYPE");
        }
        pageBuilder = PageManger.getInstance(getClassLoader()).getPage(providerType);
        if (pageBuilder == null) {
            throw new NullPointerException(SampleCommonSecondPageActivity.class.getSimpleName() + "Provider Builder 类型：" + providerType + "获取为空，请检查注解配置！");
        }
        pageInfo = pageBuilder.buildPageInfo();
    }

    protected BaseListPageView getListPageView() {
        if (simpleListPageView != null) {
            return simpleListPageView;
        }
        return new SimpleListPageView(this);
    }

}
