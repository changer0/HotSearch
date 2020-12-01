package com.example.providermoduledemo.generator;

import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.generator.annotations.ProviderGeneratorType;
import com.qq.reader.provider.generator.IProviderGenerator;

@ProviderGeneratorType("TestType2")
public class ProviderGeneratorImpl implements IProviderGenerator {
    @Override
    public ProviderLiveData loadData(int index) {
        return null;
    }
}
