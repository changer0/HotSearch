package com.lulu.hotsearch;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lulu.hotsearch.bean.HotSearchBean;
import com.lulu.hotsearch.bean.SkinPackageBean;
import com.lulu.hotsearch.define.Constant;
import com.lulu.hotsearch.manager.HotSearchConfigManager;
import com.lulu.hotsearch.utils.SwitchSkinUtil;
import com.lulu.hotsearch.view.HotSearchView;
import com.qq.reader.bookstore.BaseBookStoreFragment;
import com.qq.reader.bookstore.define.LoadSignal;
import com.yuewen.reader.zebra.loader.ObserverEntity;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Author: zhanglulu
 * Time: 2021/2/14
 */
@Route(path = Constant.WB_HOT_SEARCH)
public class HotSearchFragment extends BaseBookStoreFragment<HotSearchView, HotSearchViewModel> {
    @Override
    protected HotSearchView onCreateBookStoreView() {
        return new HotSearchView(mContext);
    }

    @Override
    protected Class<HotSearchViewModel> onCreateBookStoreViewModel(@NonNull Bundle enterBundle) {
        return HotSearchViewModel.class;
    }

    @Override
    protected void onLaunchSuccess(@NonNull View container, @NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState) {

        mBookStoreView.actionBarContainer.setVisibility(View.VISIBLE);
        innerLoadData(enterBundle, false);

        mBookStoreView.setOnFabClickListener((view, bean) -> {
            mEnterBundle.putString(Constant.HOT_SEARCH_TYPE, bean.getType());
            innerLoadData(mEnterBundle, true);
        });

    }

    private void innerLoadData(@NonNull Bundle enterBundle, boolean showProgress) {
        if (showProgress) {
            showProgress(R.string.loading);
        }
        String type = enterBundle.getString(Constant.HOT_SEARCH_TYPE, Constant.HOT_SEARCH_WB);
        HotSearchConfigManager.saveCurType(type);

        mBookStoreView.refreshActionBar(type);
        loadData(LoadSignal.LOAD_SIGNAL_INIT);
        mBookStoreView.hideFABMenu();

    }

    @Override
    public void onDataInit(ObserverEntity entity) {
        super.onDataInit(entity);
        mBookStoreView.recyclerView.smoothScrollToPosition(0);
        configUpdateTime(entity.zebra.getData());
        hideProgress();
    }


    /**
     * 配置更新时间
     * @param bean
     */
    private void configUpdateTime(@Nullable HotSearchBean bean) {
        if (bean == null) {
            return;
        }
        //添加更新时间
        mBookStoreView.titleRightTime.setVisibility(View.VISIBLE);
        CharSequence timeStr = DateFormat.format("kk:mm:ss", (long) bean.getTime_stamp());
        mBookStoreView.titleRightTime.setText(getString(R.string.update_time, timeStr));
    }

    /**
     * 配置换肤弹窗
     */
    private void configSwitchSkinDialog() {

        mBookStoreView.rightImage.setOnClickListener(v -> {

            SwitchSkinUtil.requestSkinConfig(HotSearchFragment.this, skinPackageBeans -> {


                return null;
            });
        });
    }

    private void showSwitchSkinDialog(String[] names, boolean[] isChecked) {

    }
}
