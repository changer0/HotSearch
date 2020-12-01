package com.example.providermoduledemo.generator;


import com.qq.reader.provider.generator.ILoadProviderGenerator;

import java.util.HashMap;
import java.util.Map;

public class LoadProviderGeneratorImpl implements ILoadProviderGenerator {

    private static Map<String, String> providerGeneratorMap = new HashMap<>();

    static {
        //providerGeneratorMap.put()
    }


    @Override
    public String getLoadProvider(String type) {
        return providerGeneratorMap.get(type);
    }
}
