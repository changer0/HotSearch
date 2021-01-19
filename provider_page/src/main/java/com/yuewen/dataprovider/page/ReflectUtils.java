package com.yuewen.dataprovider.page;

import android.app.Application;


import androidx.annotation.Nullable;

import com.qq.reader.provider.DataProviderConfig;
import com.qq.reader.provider.page.PageBuilderConstants;
import com.qq.reader.provider.utils.CastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 反射工具类
 */
public class ReflectUtils {
    public static ClassLoader getClassLoader() {
        Application app = DataProviderConfig.getApplication();
        return app.getClassLoader();
    }

    public static IPageFactory getPageFactory() {
        ClassLoader classLoader = getClassLoader();
        try {
            Class<?> pageFactory = classLoader.loadClass(PageBuilderConstants.BUILDER_FACTORY_IMPL_CLASS_NAME);
            Field instanceField = pageFactory.getDeclaredField("instance");
            return (IPageFactory) instanceField.get(IPageFactory.class);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T newInstance(String className, Class<T> tClass) {
        try {
            Class<?> aClass = getClassLoader().loadClass(className);
            return CastUtils.cast(aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
