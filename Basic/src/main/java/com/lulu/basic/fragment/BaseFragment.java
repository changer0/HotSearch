package com.lulu.basic.fragment;

import android.util.Log;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.lulu.basic.view.ProgressDialogFragment;
import com.lulu.skin.ISkinUpdateListener;

/**
 * 兼容主工程的空壳 BaseFragment
 * @author zhanglulu
 */
public abstract class BaseFragment extends Fragment implements ISkinUpdateListener {

    private static final String TAG = "BaseFragment";
    private ProgressDialogFragment progress;

    public boolean onBackPress() {
        return false;
    }

    //----------------------------------------------------------------------------------------------
    // 其他 UI 工具

    public void showProgress(String msg) {
        if (progress == null) {
            progress = new ProgressDialogFragment();
        }
        progress.setMsg(msg);
        progress.show(getChildFragmentManager());
    }

    public void showProgress(@StringRes int stringId) {
        showProgress(getString(stringId));
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }


    @Override
    public void onDestroyView() {
        progress = null;
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    //----------------------------------------------------------------------------------------------
    // 换肤更新
    @Override
    public void onSkinUpdate() {
    }

}
