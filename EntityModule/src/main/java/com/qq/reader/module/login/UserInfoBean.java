package com.qq.reader.module.login;

import com.qq.reader.common.gsonbean.BaseBean;

import java.util.List;

/**
 * Author: liujian.a
 * Date: 2019/8/20 10:28
 * Description: 用户信息bean
 */
public class UserInfoBean extends BaseBean {

    /**
     * {
     *   "code": 0,
     *   "message": "",
     *   "body": {
     *     "integral": 1000,
     *     "cash":100,
     *     "itemList":[{
     *              "type":1,//1 我的积分 2 我的现金 3 累计阅读
     *              "name":"我的积分",//我的积分、我的现金、累计阅读
     *              "badge":"去提现",//角标
     *              "qurl":"",
     *              "value":"10000",//积分 现金 阅读时长,
     *              "valueUnit":"元"//积分 现金 阅读时长
     *     }],
     *     "friendList":{
     *         "nameAvatars": [
     *             {
     *                 "avatar": "http://thirdqq.qlogo.cn/g?b=oidb&k=GIMeoaSf4lk21db4BWZAoQ&s=100",// 头像
     *                 "name": "德克萨斯"//名称
     *             }
     *         ],
     *         "total": 2//总数
     *     }
     *
     *   }
     * }
     */

    public static final int USER_INFO_TYPE_MY_INTEGRAL = 1;
    public static final int USER_INFO_TYPE_MY_CASH = 2;
    public static final int USER_INFO_TYPE_READ_TIME = 3;


    /**
     * code : 0
     * message :
     * body : {"integral":1000,"cash":100,"itemList":[{"type":1,"name":"我的积分","badge":"去提现","qurl":"","value":"10000","valueUnit":"元"}],"friendList":{"nameAvatars":[{"avatar":"http://thirdqq.qlogo.cn/g?b=oidb&k=GIMeoaSf4lk21db4BWZAoQ&s=100","name":"德克萨斯"}],"total":2}}
     */

    private int code;
    private String message;
    private BodyBean body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean extends BaseBean{
        /**
         * integral : 1000
         * cash : 100
         * itemList : [{"type":1,"name":"我的积分","badge":"去提现","qurl":"","value":"10000","valueUnit":"元"}]
         * friendList : {"nameAvatars":[{"avatar":"http://thirdqq.qlogo.cn/g?b=oidb&k=GIMeoaSf4lk21db4BWZAoQ&s=100","name":"德克萨斯"}],"total":2}
         * safePhone:"176****0946
         */

        private long integral;
        private int cash;
        private int isNewUser;//是否是新手态 1 是 0 否
        private int privilegeStatus;//1 特权期 2 非特权期 0 默认
        private FriendListBean friendList;
        private List<ItemListBean> itemList;
        private String safePhone;

        public String getSafePhone() {
            return safePhone;
        }

        public void setSafePhone(String safePhone) {
            this.safePhone = safePhone;
        }

        public long getIntegral() {
            return integral;
        }

        public void setIntegral(long integral) {
            this.integral = integral;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public int getIsNewUser() {
            return isNewUser;
        }

        public void setIsNewUser(int isNewUser) {
            this.isNewUser = isNewUser;
        }

        public int getPrivilegeStatus() {
            return privilegeStatus;
        }

        public void setPrivilegeStatus(int privilegeStatus) {
            this.privilegeStatus = privilegeStatus;
        }

        public FriendListBean getFriendList() {
            return friendList;
        }

        public void setFriendList(FriendListBean friendList) {
            this.friendList = friendList;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class FriendListBean extends BaseBean{
            /**
             * nameAvatars : [{"avatar":"http://thirdqq.qlogo.cn/g?b=oidb&k=GIMeoaSf4lk21db4BWZAoQ&s=100","name":"德克萨斯"}]
             * total : 2
             */

            private int total;
            private List<NameAvatarsBean> nameAvatars;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<NameAvatarsBean> getNameAvatars() {
                return nameAvatars;
            }

            public void setNameAvatars(List<NameAvatarsBean> nameAvatars) {
                this.nameAvatars = nameAvatars;
            }

            public static class NameAvatarsBean extends BaseBean{
                /**
                 * avatar : http://thirdqq.qlogo.cn/g?b=oidb&k=GIMeoaSf4lk21db4BWZAoQ&s=100
                 * name : 德克萨斯
                 */

                private String avatar;
                private String name;

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class ItemListBean extends BaseBean{
            /**
             * type : 1
             * name : 我的积分
             * badge : 去提现
             * qurl :
             * value : 10000
             * valueUnit : 元
             */

            private int type;
            private String name;
            private String badge;
            private String qurl;
            private String value;
            private String valueUnit;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBadge() {
                return badge;
            }

            public void setBadge(String badge) {
                this.badge = badge;
            }

            public String getQurl() {
                return qurl;
            }

            public void setQurl(String qurl) {
                this.qurl = qurl;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getValueUnit() {
                return valueUnit;
            }

            public void setValueUnit(String valueUnit) {
                this.valueUnit = valueUnit;
            }
        }
    }
}
