package com.yuewen.reader.zebra.loader;
import com.yuewen.reader.zebra.Zebra;
import com.yuewen.reader.zebra.define.ZebraConstants;

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
