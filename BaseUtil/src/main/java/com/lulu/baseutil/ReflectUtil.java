package com.lulu.baseutil;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static <T> T getFieldValue(Object target, String field) {
        try {
            Field declaredField = target.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            return (T) declaredField.get(target);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
