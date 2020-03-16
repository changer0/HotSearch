package com.qq.reader.module.props.bean;

import com.qq.reader.common.gsonbean.BaseBean;

import java.util.List;

/**
 * Created by zhanglulu on 2019/4/17.
 * for
 */
public class PropsBean extends BaseBean {

    //免广告劵
    public static final int PROPS_TYPE_REMOVE_AD = 1;
    //下载劵
    public static final int PROPS_TYPE_DOWNLOAD = 2;
    /**
     * type : 1
     * countdown : 11111
     * objectId : 1
     * propsInfo : [{"id":1,"term":10,"startTime":111,"endTime":111}]
     */

    private int type;
    private long countdown;
    private String objectId;
    private List<PropsInfoBean> propsInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public List<PropsInfoBean> getPropsInfo() {
        return propsInfo;
    }

    public void setPropsInfo(List<PropsInfoBean> propsInfo) {
        this.propsInfo = propsInfo;
    }

    @Override
    public String toString() {
        return "PropsBean{" +
                "type=" + type +
                ", countdown=" + countdown +
                ", objectId='" + objectId + '\'' +
                ", propsInfo=" + propsInfo +
                '}';
    }

    public static class PropsInfoBean extends BaseBean{
        /**
         * id : 1
         * term : 10
         * startTime : 111
         * endTime : 111
         */

        private String id;
        private long term;
        private long startTime;
        private long endTime;
        private long countdown;
        private String objectId;
        private String subObjectId;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTerm() {
            return term;
        }

        public void setTerm(long term) {
            this.term = term;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getCountdown() {
            return countdown;
        }

        public void setCountdown(long countdown) {
            this.countdown = countdown;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getSubObjectId() {
            return subObjectId;
        }

        public void setSubObjectId(String subObjectId) {
            this.subObjectId = subObjectId;
        }

        @Override
        public String toString() {
            return "PropsInfoBean{" +
                    "id='" + id + '\'' +
                    ", term=" + term +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", countdown=" + countdown +
                    ", objectId='" + objectId + '\'' +
                    ", subObjectId='" + subObjectId + '\'' +
                    '}';
        }
    }
}
