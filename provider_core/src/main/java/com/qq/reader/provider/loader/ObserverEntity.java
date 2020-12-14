package com.qq.reader.provider.loader;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.define.ProviderConstants;

/**
 * @author zhanglulu on 2019/11/11.
 * for Provider 观察者实体
 */
@SuppressWarnings("rawtypes")
public class ObserverEntity {


    /** 数据状态*/
    public int state;

    /** 数据实体 */
    public DataProvider<?> provider;

    /** 异常信息 */
    public Throwable throwable;

    /** 是否成功 */
    public boolean isSuccess() {
        return state == ProviderConstants.PROVIDER_DATA_SUCCESS;
    }
}
