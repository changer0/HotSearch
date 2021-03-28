package com.lulu.plugin.libs;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lulu.basic.activity.BaseActivity;
import com.lulu.plugin.test.R;

/**
 * @author zhanglulu
 */
public class TestPluginActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.test_plugin_layout);
        View viewById = findViewById(R.id.tvTest);
        if (viewById == null) {
            //ToastUtil.showShortToast("viewById 为空");
            Toast.makeText(this, "viewById 为空", Toast.LENGTH_SHORT).show();
        } else {
            viewById.setOnClickListener(v -> {
                //ToastUtil.showShortToast("点击事件生效喽~");
                Toast.makeText(this, "点击事件生效喽, 插件成功了~", Toast.LENGTH_SHORT).show();
            });
        }

    }
}
