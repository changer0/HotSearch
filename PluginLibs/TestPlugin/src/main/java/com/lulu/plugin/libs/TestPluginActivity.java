package com.lulu.plugin.libs;

import android.os.Bundle;
import android.view.View;

import com.lulu.basic.utils.ToastUtil;
import com.lulu.plugin.PluginActivity;
import com.lulu.plugin.test.R;

public class TestPluginActivity extends PluginActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.test_plugin_layout);
        View viewById = findViewById(R.id.tvTest);
        if (viewById == null) {
            ToastUtil.showShortToast("viewById 为空");
        } else {
            viewById.setOnClickListener(v -> {
                ToastUtil.showShortToast("点击事件生效喽~");
            });
        }

    }
}
