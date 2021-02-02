package com.qq.reader.activity;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * @author zhanglulu
 */
public class ReaderBaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }
}
