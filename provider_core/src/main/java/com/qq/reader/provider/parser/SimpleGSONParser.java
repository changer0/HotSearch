package com.qq.reader.provider.parser;
import com.qq.reader.provider.utils.GSONUtil;

/**
 * 简易 GSON 解析器
 * @param <T>
 */
public class SimpleGSONParser<T> implements IParser<T> {
    @Override
    public T parseData(String jsonStr, Class<T> clazz) {
        return GSONUtil.parseJsonWithGSON(jsonStr, clazz);
    }
}
