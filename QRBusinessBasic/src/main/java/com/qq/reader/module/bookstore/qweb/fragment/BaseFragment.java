package com.qq.reader.module.bookstore.qweb.fragment;

import androidx.fragment.app.Fragment;

/**
 * 兼容主工程的空壳 BaseFragment
 * @author zhanglulu
 */
public abstract class BaseFragment extends Fragment {

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

    public void cancleLoadData() {

    }
}
