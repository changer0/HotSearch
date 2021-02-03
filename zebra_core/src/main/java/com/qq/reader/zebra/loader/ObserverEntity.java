package com.qq.reader.zebra.loader;
import com.qq.reader.zebra.Zebra;
import com.qq.reader.zebra.define.ZebraConstants;

/**
 * @author zhanglulu on 2019/11/11.
 * for Provider 观察者实体
 */
@SuppressWarnings("rawtypes")
public class ObserverEntity {


    /** 数据状态*/
    public int state;

    /** 数据实体 */
    public Zebra<?> zebra;

    /** 异常信息 */
    public Throwable throwable;

    /** 是否成功 */
    public boolean isSuccess() {
        return state == ZebraConstants.ZEBRA_DATA_SUCCESS;
    }
}
