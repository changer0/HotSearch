package com.example.providermoduledemo.sample;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.providermoduledemo.generator.ProviderGeneratorTypes;
import com.qq.reader.provider.SimpleListPageView;
import com.qq.reader.provider.build.ClassLoaderUtils;
import com.qq.reader.provider.build.IProviderBuilder;
import com.qq.reader.provider.build.IProviderBuilderManager;
import com.qq.reader.provider.build.ProviderBuilderConstants;

/**
 * 通用二级页 示例页面
 */
public class SampleCommonSecondPageActivity extends AppCompatActivity {

    private static final String TAG = "SampleListPageActivity";

    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";

    private SimpleListPageView simpleListPageView;

    private int curIndex = 1;

    private IProviderBuilder iProviderGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleListPageView = new SimpleListPageView(this);
        setContentView(simpleListPageView.getContentView());
        initProviderGenerator();
        loadData(curIndex);
        simpleListPageView.setOnLoadMoreListener(() -> {
            curIndex++;
            curIndex = curIndex > 5 ? 1 : curIndex;
            loadData(curIndex);
        });
        simpleListPageView.setOnRefreshListener(() -> {
            loadData(1);
        });
    }

    private void loadData(int index) {
        String url = String.format(SERVER_URL, index);
        Log.d(TAG, "loadData: url:" + url);

        iProviderGenerator.buildProvider(index).observe(this, simpleListPageView);


    }

    private void initProviderGenerator() {
        IProviderBuilderManager iProviderBuilderManager =
                ClassLoaderUtils.newInstance(getClassLoader(), ProviderBuilderConstants.GENERATOR_CLASS_NAME, IProviderBuilderManager.class);
        String providerGenerator = iProviderBuilderManager.getProviderBuilder(ProviderGeneratorTypes.TEST_PAGE);
        iProviderGenerator = ClassLoaderUtils.newInstance(getClassLoader(), providerGenerator, IProviderBuilder.class);
        if (iProviderGenerator == null) {
            throw new NullPointerException("iProviderGenerator 为空！！！！");
        }
    }

}
