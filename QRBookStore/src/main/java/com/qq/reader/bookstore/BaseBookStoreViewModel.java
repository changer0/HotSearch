package com.qq.reader.bookstore;

import android.os.Bundle;
import androidx.lifecycle.ViewModel;

import com.yuewen.reader.zebra.ZebraLiveData;

/**
 * 承载 Fragment 的 ViewModel, zebraLiveDataCache 可选使用
 * @author zhanglulu
 */
public abstract class BaseBookStoreViewModel extends ViewModel {


    /**
     * 获取 ZebraLiveData
     * @param params 参数
     * @return ZebraLiveData
     */
    abstract public ZebraLiveData getZebraLiveData(Bundle params);
}
