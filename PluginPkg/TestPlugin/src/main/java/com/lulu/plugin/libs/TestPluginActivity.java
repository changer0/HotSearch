package com.lulu.plugin.libs;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lulu.baseutil.ReflectUtil;
import com.lulu.basic.activity.BaseActivity;
import com.lulu.basic.utils.ToastUtil;
import com.lulu.plugin.lib.IGetHotSearchConfig;
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
                IGetHotSearchConfig getHotSearchConfig = ReflectUtil
                        .newInstanceByName(IGetHotSearchConfig.HOT_SEARCH_INFO_CLASS, IGetHotSearchConfig.class);
                if (getHotSearchConfig == null) {
                    ToastUtil.showShortToast("getHotSearchConfig 为空");
                } else {
                    String name = getHotSearchConfig.getCurHotSearchInfo().getString(IGetHotSearchConfig.CONFIG_INFO_NAME);
                    ToastUtil.showShortToast("当前展示得类型：" + name);
                }
            });
        }

    }
}
