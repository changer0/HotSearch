package com.yuewen.component.router.exception;

/**
 * Created by ronaldo on 2019/4/12.
 * 路由自定义异常
 */

public class YWRouterException extends Exception{
    private int mErrCode;
    private String mErrMsg;

    public YWRouterException(int errCode, String errMsg) {
        mErrCode = errCode;
        mErrMsg = errMsg;
    }

    public int getErrCode() {
        return mErrCode;
    }

    public void setErrCode(int mErrCode) {
        this.mErrCode = mErrCode;
    }

    public String getErrMsg() {
        return mErrMsg;
    }

    public void setErrMsg(String mErrMsg) {
        this.mErrMsg = mErrMsg;
    }
}
