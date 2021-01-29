package com.yuewen.component.router.bean;

/**
 * Created by ronaldo on 2019/4/12.
 * 用于返回module服务的处理结果
 */

public class YWRouterResult {
    // 保留字段，用于返回方法处理异常
    private int code;
    private int msg;

    // 用于返回结果 四种基本类型 int long string boolean 其余用object
    private int resultInt;
    private long resultLong;
    private String resultStr;
    private boolean resultBoolean;
    private Object resultObject;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public int getResultInt() {
        return resultInt;
    }

    public void setResultInt(int resultInt) {
        this.resultInt = resultInt;
    }

    public long getResultLong() {
        return resultLong;
    }

    public void setResultLong(long resultLong) {
        this.resultLong = resultLong;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public boolean getResultBoolean() {
        return resultBoolean;
    }

    public void setResultBoolean(boolean resultBoolean) {
        this.resultBoolean = resultBoolean;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }
}
