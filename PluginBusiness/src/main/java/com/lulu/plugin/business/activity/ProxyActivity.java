package com.lulu.plugin.business.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import com.lulu.basic.activity.BaseActivity;

/**
 * 系统会通过ProxyActivity触发对应的方法的具体实现
 * @author zhanglulu
 */
public class ProxyActivity extends BaseActivity {
    LifeCircleController mPluginController = new LifeCircleController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        if (bundle == null) {
            throw new RuntimeException("检查传入参数");
        }
        mPluginController.onCreate(bundle);
    }

    @Override
    public Resources getResources() {
        // construct when loading apk
        Resources resources = mPluginController.getResources();
        return resources == null ? super.getResources() : resources;
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = mPluginController.getTheme();
        return theme == null ? super.getTheme() : theme;
    }

    @Override
    public AssetManager getAssets() {
        return mPluginController.getAssets();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPluginController.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPluginController.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPluginController.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPluginController.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPluginController.onDestroy();
    }

    @Override
    public void onSkinUpdate() {
        super.onSkinUpdate();
        mPluginController.onSkinUpdate();
    }
}

