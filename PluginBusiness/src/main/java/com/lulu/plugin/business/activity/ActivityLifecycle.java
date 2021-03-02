package com.lulu.plugin.business.activity;

import android.os.Bundle;

/**
 * @author zhanglulu
 */
public interface ActivityLifecycle {
    void onCreate(Bundle bundle);
    void onStart();
    void onResume();
    void onStop();
    void onPause();
    void onDestroy();
}
