package com.example.providermoduledemo.sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qq.reader.provider.listpage.SimpleListPageView;
import com.qq.reader.provider.build.IProviderBuilder;
import com.qq.reader.provider.build.ProviderBuilderManger;

/**
 * 通用二级页 示例页面
 */
public class SampleCommonSecondPageActivity extends AppCompatActivity {

    public static String PROVIDER_BUILDER_TYPE = "PROVIDER_BUILDER_TYPE";

    private static final String TAG = "SampleListPageActivity";

    private SimpleListPageView simpleListPageView;

    private int curIndex = 1;

    private IProviderBuilder providerBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleListPageView = new SimpleListPageView(this);
        setContentView(simpleListPageView.getContentView());
        initProviderBuilder();
        initLoadData();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(providerBuilder.getTitleName());
        }
    }

    /**
     * 初始化加载数据
     */
    private void initLoadData() {
        curIndex = providerBuilder.getStartIndex();
        loadData(curIndex);
        simpleListPageView.setEnableLoadMore(providerBuilder.isEnableLoadMore());
        simpleListPageView.setEnablePullDownRefresh(providerBuilder.isEnablePullDownRefresh());
        if (providerBuilder.isEnableLoadMore()) {
            simpleListPageView.setOnLoadMoreListener(() -> {
                curIndex++;
                loadData(curIndex);
            });
        }
        if (providerBuilder.isEnablePullDownRefresh()) {
            simpleListPageView.setOnRefreshListener(() -> {
                curIndex = providerBuilder.getStartIndex();
                loadData(curIndex);
            });
        }
    }

    private void loadData(int index) {
        providerBuilder.buildProvider(index).observe(this, simpleListPageView);
    }

    private void initProviderBuilder() {
        Intent intent = getIntent();
        String providerType = intent.getStringExtra(PROVIDER_BUILDER_TYPE);
        if (TextUtils.isEmpty(providerType)) {
            throw new NullPointerException(SampleCommonSecondPageActivity.class.getSimpleName() + " 启动该 Activity 前需要传入 PROVIDER_BUILDER_TYPE");
        }
        providerBuilder = ProviderBuilderManger.getInstance(getClassLoader()).getProviderBuilder(providerType);
        if (providerBuilder == null) {
            throw new NullPointerException(SampleCommonSecondPageActivity.class.getSimpleName() + "Provider Builder 类型：" + providerType + "获取为空，请检查注解配置！");
        }
    }

}
