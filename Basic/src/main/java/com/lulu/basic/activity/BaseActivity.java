package com.lulu.basic.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.lulu.basic.skin.SkinManager;
import com.lulu.basic.view.ProgressDialogFragment;
import com.lulu.skin.ISkinUpdateListener;
import com.lulu.skin.SkinFactory;

/**
 * @author zhanglulu
 */
public class BaseActivity extends AppCompatActivity implements ISkinUpdateListener {

    private ProgressDialogFragment progress;

    private SkinFactory skinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        skinFactory = new SkinFactory(this);
        //设置给 BaseActivity
        getLayoutInflater().setFactory(skinFactory);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //换肤初始化
        SkinManager.get().init();
        SkinManager.get().addSkinUpdateListener(this);
    }

    protected void adapterStatus() {
        try {
            //R.color.primaryColor
            setStatusBgColor(SkinManager.get().getColor("primaryDarkColor"));
            setStatusTextColor(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.get().removeSkinUpdateListener(this);
        skinFactory.release();
        InputMethodManagerLastSrvView.fixLeak(this);
        progress = null;
    }


    //----------------------------------------------------------------------------------------------
    // 其他 UI 工具

    public void showProgress(String msg) {
        if (progress == null) {
            progress = new ProgressDialogFragment();
        }
        progress.setMsg(msg);
        progress.show(getSupportFragmentManager());
    }

    public void showProgress(@StringRes int stringId) {
        showProgress(getString(stringId));
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    //----------------------------------------------------------------------------------------------
    // 状态栏控制

    /**
     * 状态栏背景颜色
     * @param color
     */
    public void setStatusBgColor(int color) {
        // Android 4.4
        //设置状态栏透明，并且变为全屏模式。上面的解释已经说得很清楚了，当window的这个属性有效的时候，
        // 会自动设置 system ui visibility的标志SYSTEM_UI_FLAG_LAYOUT_STABLE和SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //设置了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,表明会Window负责系统bar的background 绘制，
        // 绘制透明背景的系统bar（状态栏和导航栏），
        // 然后用getStatusBarColor()和getNavigationBarColor()的颜色填充相应的区域。
        // 这就是Android 5.0 以上实现沉浸式导航栏的原理。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(color);
    }

    /**
     * 设置状态栏文字显示模式
     * @param isLight true: 文字颜色为浅色 false 文字颜色为深色
     */
    public void setStatusTextColor(boolean isLight) {
        if (!isLight) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //为setSystemUiVisibility(int)方法添加的Flag,请求status bar
            //绘制模式，它可以兼容亮色背景的status bar 。要在设置了 FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag
            //,同时清除了 FLAG_TRANSLUCENT_STATUS flag 才会生效。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 全屏展示
     */
    public void setFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }

    //----------------------------------------------------------------------------------------------
    // 换肤更新
    @Override
    public void onSkinUpdate() {
        skinFactory.apply();
        adapterStatus();
    }
}
