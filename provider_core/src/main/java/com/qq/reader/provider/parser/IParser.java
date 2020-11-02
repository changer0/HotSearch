package com.qq.reader.provider.parser;

import com.qq.reader.provider.bean.BaseDataBean;

/**
 * 解析器
 * @param <P> 数据类型
 */
public interface IParser<P extends BaseDataBean> {
    P parseData(String jsonStr, Class<P> clazz);
}
