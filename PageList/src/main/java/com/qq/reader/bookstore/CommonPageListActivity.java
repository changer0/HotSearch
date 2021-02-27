package com.qq.reader.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lulu.basic.activity.BaseActivity;
import com.lulu.basic.fragment.BaseFragment;

/**
 * 通用书城 Activity, 用于放置二级页
 * Time: 2020/12/13
 * @author zhanglulu
 */
public class CommonPageListActivity extends BaseActivity {

    private BaseFragment mHoldFragment;
    private Bundle enterBundle;
    /**恢复用*/
    private static final String SAVE_KEY = "enterBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_container_layout);
        try {
            Intent intent = getIntent();
            if (intent == null) {
                throw new NullPointerException("CommonBookStoreActivity intent 不可为空");
            }
            enterBundle = intent.getExtras();
            if (enterBundle == null) {
                enterBundle = savedInstanceState.getBundle(SAVE_KEY);
            }
            if (enterBundle == null) {
                throw new NullPointerException("CommonBookStoreActivity enterBundle 不可为空");
            }

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mHoldFragment = (BaseFragment) fm.findFragmentByTag("fragment");
            if (mHoldFragment == null){
                //如果不为空则为进程被杀死后恢复的，由Fragment自行恢复
                mHoldFragment = analyzePageFragment(enterBundle);
                if (mHoldFragment == null) {
                    throw new NullPointerException("CommonBookStoreActivity BOOK_STORE_FRAGMENT_PATH 不可为空");
                }
                ft.add(R.id.fragment_content, mHoldFragment, "fragment");
                ft.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private BaseFragment analyzePageFragment(Bundle enterBundle) {
        String path = enterBundle.getString(BookStoreActivityLauncher.BOOK_STORE_FRAGMENT_PATH);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return (BaseFragment) ARouter.getInstance().build(path)
                .with(enterBundle)
                .navigation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBundle(SAVE_KEY, enterBundle);
    }


    @Override
    public void onBackPressed() {
        if (mHoldFragment == null || !mHoldFragment.onBackPress()){
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHoldFragment != null) {
            mHoldFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSkinUpdate() {
        super.onSkinUpdate();
        mHoldFragment.onSkinUpdate();
    }
}
