package com.qq.reader.provider.simple;

import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.provider.inter.IParser;
import com.qq.reader.provider.utils.GSONUtil;

/**
 * 简易 GSON 解析器
 * @param <P>
 */
public class SimpleGSONParser<P extends BaseDataBean> implements IParser<P> {
    @Override
    public P parseData(String jsonStr, Class<P> clazz) {
        return GSONUtil.parseJsonWithGSON(jsonStr, clazz);
    }
}
