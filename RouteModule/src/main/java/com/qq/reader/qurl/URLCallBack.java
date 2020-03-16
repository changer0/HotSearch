package com.qq.reader.qurl;

import android.os.Message;

/**
 * Created by yangtao on 2015/8/24.
 */
public interface URLCallBack {
    /**
     * 给与调用这一个修改将被执行的intent的机会，
     * <p/>
     * // @ TODO 15.8.24这里主要考虑到业务需求的细分；比较担心开放这个接口被乱用；     *

     */
    //void beforeJump(Intent intent);

    /**
     *
     * @param msg 回调消息封装在message
     * @return 外部是否吃掉这个回调，返回true，内部不再处理，否则内部也会处理
     */
    public boolean qURLJumpResult(Message msg);

}
