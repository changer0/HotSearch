package com.example.providermoduledemo.sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

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

    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";

    private SimpleListPageView simpleListPageView;

    private int curIndex = 1;

    private IProviderBuilder providerBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleListPageView = new SimpleListPageView(this);
        setContentView(simpleListPageView.getContentView());
        initProviderBuilder();
        loadData(curIndex);
        simpleListPageView.setOnLoadMoreListener(() -> {
            curIndex++;
            curIndex = curIndex > 5 ? 1 : curIndex;
            loadData(curIndex);
        });
        simpleListPageView.setOnRefreshListener(() -> {
            loadData(1);
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(providerBuilder.getTitleName());
        }

    }

    private void loadData(int index) {
        String url = String.format(SERVER_URL, index);
        Log.d(TAG, "loadData: url:" + url);
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
