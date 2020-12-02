package com.example.providermoduledemo.sample;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.providermoduledemo.generator.ProviderGeneratorTypes;
import com.qq.reader.provider.SimpleListPageView;
import com.qq.reader.provider.generator.IProviderBuilder;
import com.qq.reader.provider.generator.IProviderGeneratorManager;
import com.qq.reader.provider.generator.ProviderGeneratorConstants;
import com.qq.reader.provider.utils.CastUtils;


public class SampleListPageActivity extends AppCompatActivity {

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

//        DataProvider.with(SampleResultBean.class, SampleConvertResponseBean.class)
//                .url(url)
//                .converter(new SampleConverter())
//                .viewBindItemBuilder(new SampleViewBindItemBuilder())
//                .cacheConfig(CacheMode.CACHE_MODE_NOT_USE_CACHE, new SampleGetExpiredTime())
//                .load()
//                .observe(this, simpleListPageView);
        iProviderGenerator.loadData(index).observe(this, simpleListPageView);


    }

    private void initProviderGenerator() {
        IProviderGeneratorManager iProviderGeneratorManager =
                newInstance(getClassLoader(), ProviderGeneratorConstants.GENERATOR_CLASS_NAME, IProviderGeneratorManager.class);
        String providerGenerator = iProviderGeneratorManager.getProviderGenerator(ProviderGeneratorTypes.TEST_PAGE);
        iProviderGenerator = newInstance(getClassLoader(), providerGenerator, IProviderBuilder.class);
        if (iProviderGenerator == null) {
            throw new NullPointerException("iProviderGenerator 为空！！！！");
        }
    }

    private static <T> T newInstance(ClassLoader loader, String className, Class<T> tClass) {
        try {
            Class<?> aClass = loader.loadClass(className);
            return CastUtils.cast(aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
