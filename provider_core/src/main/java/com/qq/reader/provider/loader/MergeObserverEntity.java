package com.qq.reader.provider.loader;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.define.ProviderConstants;

import java.util.List;

/**
 * @author zhanglulu on 2019/11/11.
 * for Provider 观察者实体
 */
@SuppressWarnings("rawtypes")
public class MergeObserverEntity {

    /** 数据状态*/
    public int state;

    /** 异常信息 */
    public Throwable throwable;

    /** 是否成功 */
    public boolean isSuccess() {
        return state == ProviderConstants.PROVIDER_DATA_SUCCESS;
    }

    /**合并数据结果*/
    public List<BaseViewBindItem> mergeResultList;
}
