package com.qq.reader.provider.inter;

import androidx.annotation.NonNull;
import com.qq.reader.provider.BaseViewBindItem;
import java.util.List;

/**
 * 填充器
 */
public interface IFiller<P> {
    /**数据填充*/
    List<BaseViewBindItem> fillData(@NonNull P data);
    /**过期时间，用户*/
    long getExpiredTime(P mData);
}
