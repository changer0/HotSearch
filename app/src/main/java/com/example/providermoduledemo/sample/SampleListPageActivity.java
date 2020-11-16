package com.example.providermoduledemo.sample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.SimpleListPageView;
import com.qq.reader.provider.cache.CacheMode;


public class SampleListPageActivity extends AppCompatActivity {

    private static final String TAG = "SampleListPageActivity";

    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/leftImgRightText (%s).json";

    private SimpleListPageView simpleListPageView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        simpleListPageView = new SimpleListPageView(this);
        setContentView(simpleListPageView.getContentView());
        loadData(1);
    }

    private void loadData(int index) {
        String url = String.format(SERVER_URL, index);
        Log.d(TAG, "loadData: url: $url");

        DataProvider.with(SampleResponseBean.class)
                .url(url)
                .viewBindItemBuilder(new SampleViewBindItemBuilder())
                .cacheConfig(CacheMode.CACHE_MODE_USE_CACHE_PRIORITY, new SampleGetExpiredTime())
                .load()
                .observe(this, simpleListPageView);
    }
}
