package com.qq.reader.provider.builder;

import com.qq.reader.provider.utils.CastUtils;

public class ClassLoaderUtils {

    public static <T> T newInstance(ClassLoader loader, String className, Class<T> tClass) {
        try {
            Class<?> aClass = loader.loadClass(className);
            return CastUtils.cast(aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
