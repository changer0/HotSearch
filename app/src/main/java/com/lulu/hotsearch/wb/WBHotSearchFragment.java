package com.lulu.hotsearch.wb;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lulu.hotsearch.wb.bean.WBHotSearchBean;
import com.qq.reader.bookstore.BaseBookStoreFragment;
import com.qq.reader.bookstore.define.LoadSignal;
import com.yuewen.reader.zebra.loader.ObserverEntity;
import com.yuewen.reader.zebra.utils.CastUtils;

import java.util.Calendar;

/**
 * Author: zhanglulu
 * Time: 2021/2/14
 */
@Route(path = Constant.WB_HOT_SEARCH)
public class WBHotSearchFragment extends BaseBookStoreFragment<WBHotSearchView, WBViewModel> {
    @Override
    protected WBHotSearchView onCreateBookStoreView() {
        return new WBHotSearchView(mContext);
    }

    @Override
    protected Class<WBViewModel> onCreateBookStoreViewModel(@NonNull Bundle enterBundle) {
        return WBViewModel.class;
    }

    @Override
    protected void onLaunchSuccess(@NonNull View container, @NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState) {
        loadData(LoadSignal.LOAD_SIGNAL_INIT);
        //https://www.cnblogs.com/sanfeng4476/p/6112284.html
    }

    @Override
    public void onDataInit(ObserverEntity entity) {
        super.onDataInit(entity);
        configUpdateTime(entity.zebra.getData());
    }


    /**
     * 配置更新时间
     * @param bean
     */
    private void configUpdateTime(WBHotSearchBean bean) {
        //添加更新时间
        mBookStoreView.titleRightTime.setVisibility(View.VISIBLE);
        CharSequence timeStr = DateFormat.format("kk:mm:ss", (long) bean.getTime_stamp());
        mBookStoreView.titleRightTime.setText(getString(R.string.update_time, timeStr));
    }
}
