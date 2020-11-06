package com.qq.reader.provider.parser;

/**
 * 解析器
 * @param <P> 数据类型
 */
public interface IParser<P> {
    P parseData(String jsonStr, Class<P> clazz);
}
