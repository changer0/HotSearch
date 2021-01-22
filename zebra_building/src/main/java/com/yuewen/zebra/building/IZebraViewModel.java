package com.yuewen.zebra.building;

import android.os.Bundle;

import com.qq.reader.provider.ProviderLiveData;

/**
 * @author zhanglulu
 * @date : 2021/1/21 8:22 PM
 */
public interface IZebraViewModel {
    public ProviderLiveData load(Bundle params);
}
