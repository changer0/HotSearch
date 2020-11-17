package com.qq.reader.provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.qq.reader.provider.loader.ObserverEntity;
import com.qq.reader.provider.utils.CastUtils;

/**
 * 抑制泛型警告
 */
public class ProviderLiveData extends MutableLiveData<ObserverEntity> {
    
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
        super.observe(owner, CastUtils.cast(observer));
    }

    @Override
    public void observeForever(@NonNull Observer observer) {
        super.observeForever(CastUtils.cast(observer));
    }
}
