package com.qq.reader.zebra;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.qq.reader.zebra.loader.ObserverEntity;
import com.qq.reader.zebra.utils.CastUtils;

/**
 * 抑制泛型警告
 */
public class ZebraLiveData extends MutableLiveData<ObserverEntity> {
    
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
        super.observe(owner, CastUtils.cast(observer));
    }

    @Override
    public void observeForever(@NonNull Observer observer) {
        super.observeForever(CastUtils.cast(observer));
    }
}
