package com.qq.reader.entity;

import com.qq.reader.common.gsonbean.BaseBean;

/**
 * 记录是否阅读到最后bean
 * Created by qijunqing on 2018/5/16.
 */

public class ReadEndBean extends BaseBean{
    /** 记录最后阅读的时间*/
    private long finallyReadTime;
    /** 书籍Id*/
    private String bookId;
    /** 记录是否读到最后一页,true代表是*/
    private boolean isReadEnd;
    /** 记录最后读的章节id 主要当type为txt时的判断条件*/
    private int finallyReadChapterId;
    /** 记录是否完结，true代表完结，false相反  主要当type为txt时的判断条件*/
    private boolean isFinish;
    /** 是否是试读版 主要当type为精排时的判断条件*/
    private boolean isTrial;
    /** 书籍类型，2代表txt,3代表精排*/
    private int type;

    public long getFinallyReadTime() {
        return finallyReadTime;
    }

    public void setFinallyReadTime(long finallyReadTime) {
        this.finallyReadTime = finallyReadTime;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean isReadEnd() {
        return isReadEnd;
    }

    public void setReadEnd(boolean readEnd) {
        isReadEnd = readEnd;
    }

    public int getFinallyReadChapterId() {
        return finallyReadChapterId;
    }

    public void setFinallyReadChapterId(int finallyReadChapterId) {
        this.finallyReadChapterId = finallyReadChapterId;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean isTrial() {
        return isTrial;
    }

    public void setTrial(boolean trial) {
        isTrial = trial;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
