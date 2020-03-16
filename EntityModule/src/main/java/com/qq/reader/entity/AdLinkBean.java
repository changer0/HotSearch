package com.qq.reader.entity;

import com.qq.reader.common.gsonbean.BaseBean;

public class AdLinkBean extends BaseBean {


    /**
     * https://yapi.yuewen.com/project/331/interface/api/65336
     *
     * beginTime : 1575349576
     * content :
     * controlParams : {"refInfo":"","showLimit":"0","extImageUrl":"https://console-platform-pt-1252317822.image.myqcloud.com/BookAd/924e_2019-12-03/1575349604139_992167.png","forceLogin":"","material":"","bookNews":"3","mobileStatus":"0","newUser":"","mobile":"","style":"3","value":"https://hd.book.qq.com/noah/855737241","status":"0"}
     * destUrl : https://hd.book.qq.com/noah/855737241
     * endTime : 1575561600
     * id : 70455169
     * linkRefId :
     * linkType : 3
     * logParams : {}
     * priority : 2
     * resourceUrl : https://console-platform-pt-1252317822.image.myqcloud.com/BookAd/924e_2019-12-03/1575349604544_192565.png
     * title :
     */

    private int beginTime;
    private String content;
    private ControlParamsBean controlParams;
    private String destUrl;// 跳转链接
    private int endTime;
    private int id;
    private String linkRefId;// 链接类型关联的Id
    private int linkType;// 链接类型
    private LogParamsBean logParams;// 日志参数（客户端回传参数
    private int priority;// 排序优先级
    private String resourceUrl;// 资源链接
    private String title;

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ControlParamsBean getControlParams() {
        return controlParams;
    }

    public void setControlParams(ControlParamsBean controlParams) {
        this.controlParams = controlParams;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkRefId() {
        return linkRefId;
    }

    public void setLinkRefId(String linkRefId) {
        this.linkRefId = linkRefId;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public LogParamsBean getLogParams() {
        return logParams;
    }

    public void setLogParams(LogParamsBean logParams) {
        this.logParams = logParams;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ControlParamsBean {
        /**
         * refInfo :
         * showLimit : 0
         * extImageUrl : https://console-platform-pt-1252317822.image.myqcloud.com/BookAd/924e_2019-12-03/1575349604139_992167.png
         * isRewardAd : 1   true:1 false:0
         * forceLogin :
         * material :
         * bookNews : 3
         * mobileStatus : 0
         * newUser :
         * mobile :
         * style : 3
         * value : https://hd.book.qq.com/noah/855737241
         * status : 0
         */

        private String refInfo;
        private String showLimit;
        private String extImageUrl;
        private String forceLogin;
        private String material;
        private String bookNews;
        private String mobileStatus;
        private String newUser;
        private String mobile;
        private String style;
        private String value;
        private String status;
        private String adStartChapter;
        private String iconUrl;
        private String tagId;
        private int isRewardAd;

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getIsRewardAd() {
            return isRewardAd;
        }

        public void setIsRewardAd(int isRewardAd) {
            this.isRewardAd = isRewardAd;
        }

        public String getRefInfo() {
            return refInfo;
        }

        public void setRefInfo(String refInfo) {
            this.refInfo = refInfo;
        }

        public String getShowLimit() {
            return showLimit;
        }

        public void setShowLimit(String showLimit) {
            this.showLimit = showLimit;
        }

        public String getExtImageUrl() {
            return extImageUrl;
        }

        public void setExtImageUrl(String extImageUrl) {
            this.extImageUrl = extImageUrl;
        }

        public String getForceLogin() {
            return forceLogin;
        }

        public void setForceLogin(String forceLogin) {
            this.forceLogin = forceLogin;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getBookNews() {
            return bookNews;
        }

        public void setBookNews(String bookNews) {
            this.bookNews = bookNews;
        }

        public String getMobileStatus() {
            return mobileStatus;
        }

        public void setMobileStatus(String mobileStatus) {
            this.mobileStatus = mobileStatus;
        }

        public String getNewUser() {
            return newUser;
        }

        public void setNewUser(String newUser) {
            this.newUser = newUser;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdStartChapter() {
            return adStartChapter;
        }

        public void setAdStartChapter(String adStartChapter) {
            this.adStartChapter = adStartChapter;
        }
    }

    public static class LogParamsBean {
    }
}
