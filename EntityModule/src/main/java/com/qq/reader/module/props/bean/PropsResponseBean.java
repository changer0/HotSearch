package com.qq.reader.module.props.bean;

import com.qq.reader.common.gsonbean.BaseBean;

import java.util.List;

/**
 * Created by zhanglulu on 2019/4/17.
 * for
 */
public class PropsResponseBean extends BaseBean {
    /**
     * message :
     * code : 0
     * body : {"timestamp":100,"props":[{"type":1,"countdown":11111,"objectId":1,"propsInfo":[{"id":1,"term":10,"termUnit":1,"type":1,"startTime":111,"endTime":111}]}],"unuseProps":[{"type":1,"propsInfo":[{"id":1,"term":10,"termUnit":1,"type":1,"startTime":111,"endTime":111}]}]}
     */

    private String message;
    private int code;
    private BodyBean body;

    /**
     * 是否为本地数据 <br/>
     * 客户端专用，后台不返回
     */
    private boolean isLocalData = false;

    /**
     * 检查是否为 本地数据
     * @return
     */
    public boolean isLocalData() {
        return isLocalData;
    }

    public void setLocalData(boolean localData) {
        isLocalData = localData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PropsResponseBean{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", body=" + body +
                ", isLocalData=" + isLocalData +
                '}';
    }

    public static class BodyBean extends BaseBean{
        /**
         * timestamp : 100
         * props : [{"type":1,"countdown":11111,"objectId":1,"propsInfo":[{"id":1,"term":10,"termUnit":1,"type":1,"startTime":111,"endTime":111}]}]
         * unuseProps : [{"type":1,"propsInfo":[{"id":1,"term":10,"termUnit":1,"type":1,"startTime":111,"endTime":111}]}]
         */

        private long timestamp;
        private List<PropsBean> props;
        private List<PropsBean> unuseProps;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public List<PropsBean> getProps() {
            return props;
        }

        public void setProps(List<PropsBean> props) {
            this.props = props;
        }

        public List<PropsBean> getUnuseProps() {
            return unuseProps;
        }

        public void setUnuseProps(List<PropsBean> unuseProps) {
            this.unuseProps = unuseProps;
        }

        @Override
        public String toString() {
            return "StackCategoryBean{" +
                    "timestamp=" + timestamp +
                    ", props=" + props +
                    ", unuseProps=" + unuseProps +
                    '}';
        }
    }
}
