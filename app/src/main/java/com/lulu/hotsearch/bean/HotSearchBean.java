/**
  * Copyright 2021 bejson.com 
  */
package com.lulu.hotsearch.bean;

import java.util.List;

/**
 * Auto-generated: 2021-02-13 11:22:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HotSearchBean extends BaseBean {

    private int code;
    private String msg;
    private String type;
    private double expired_time;
    private double time_stamp;

    private List<Result> result;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setResult(List<Result> result) {
         this.result = result;
     }
     public List<Result> getResult() {
         return result;
     }

    public double getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(double expired_time) {
        this.expired_time = expired_time;
    }

    public double getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(double time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}