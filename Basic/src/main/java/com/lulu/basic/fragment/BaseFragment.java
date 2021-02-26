package com.lulu.basic.fragment;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.lulu.basic.view.ProgressDialogFragment;
import com.lulu.skin.ISkinUpdateListener;

/**
 * 兼容主工程的空壳 BaseFragment
 * @author zhanglulu
 */
public abstract class BaseFragment extends Fragment implements ISkinUpdateListener {

    private ProgressDialogFragment progress;

    public boolean onBackPress() {
        return false;
    }

    /**
     * 预加载需要做的事情，UI线程
     */
    public abstract void onPreLoad();

    /**
     * 加载数据，子线程
     */
    public abstract void onLoading();

    /**
     * 数据加载完毕，UI线程
     */
    public abstract void onLoadFinished();

    public void cancelLoadData() {

    }

    //----------------------------------------------------------------------------------------------
    // 其他 UI 工具
    public void showProgress(String msg) {
        if (progress == null) {
            progress = new ProgressDialogFragment();
        }
        progress.setMsg(msg);
        progress.show(getFragmentManager());
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
        super.onDestroyView();
    }

    //----------------------------------------------------------------------------------------------
    // 换肤更新
    @Override
    public void onSkinUpdate() {
    }

}
