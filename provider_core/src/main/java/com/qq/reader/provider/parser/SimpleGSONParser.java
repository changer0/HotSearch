package com.qq.reader.provider.parser;

import com.qq.reader.provider.bean.BaseBean;
import com.qq.reader.provider.utils.GSONUtil;

/**
 * 简易 GSON 解析器
 * @param <P>
 */
public class SimpleGSONParser<P extends BaseBean> implements IParser<P> {
    @Override
    public P parseData(String jsonStr, Class<P> clazz) {
        return GSONUtil.parseJsonWithGSON(jsonStr, clazz);
    }
}
