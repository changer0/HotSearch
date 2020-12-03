package com.example.providermoduledemo.sample;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.listpage.SimpleListPageView;
import com.qq.reader.provider.cache.CacheMode;


public class SampleListPageActivity extends AppCompatActivity {

    private static final String TAG = "SampleListPageActivity";

    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";

    private SimpleListPageView simpleListPageView;

    private int curIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleListPageView = new SimpleListPageView(this);
        setContentView(simpleListPageView.getContentView());
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

        DataProvider.with(SampleResultBean.class, SampleConvertResponseBean.class)
                .url(url)
                .converter(new SampleConverter())
                .viewBindItemBuilder(new SampleViewBindItemBuilder())
                .cacheConfig(CacheMode.CACHE_MODE_NOT_USE_CACHE, new SampleGetExpiredTime())
                .load()
                .observe(this, simpleListPageView);
    }
}
