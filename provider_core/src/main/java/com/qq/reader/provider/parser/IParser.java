package com.qq.reader.provider.parser;

/**
 * 解析器
 * @param <T> 数据类型
 */
public interface IParser<T> {
    T parseData(String jsonStr, Class<T> clazz);
}
