package com.example.providermoduledemo.generator;

import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.generator.annotations.ProviderGeneratorType;
import com.qq.reader.provider.generator.ILoadProvider;

@ProviderGeneratorType("TestType2")
public class LoadProviderImpl implements ILoadProvider {
    @Override
    public ProviderLiveData loadData(int index) {
        return null;
    }
}
