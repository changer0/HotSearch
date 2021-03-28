/**
  * Copyright 2021 bejson.com 
  */
package com.lulu.hotsearch.bean;

import com.lulu.basic.bean.BaseBean;

import java.util.List;

/**
 * Auto-generated: 2021-02-17 14:57:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HotSearchConfigBean extends BaseBean {

    private List<HotSearchListBean> hotSearchList;

    private boolean isUseDefaultBrowser = true;

    public List<HotSearchListBean> getHotSearchList() {
        return hotSearchList;
    }

    public void setHotSearchList(List<HotSearchListBean> hotSearchList) {
        this.hotSearchList = hotSearchList;
    }

    public boolean isUseDefaultBrowser() {
        return isUseDefaultBrowser;
    }

    public void setUseDefaultBrowser(boolean useDefaultBrowser) {
        isUseDefaultBrowser = useDefaultBrowser;
    }

    public static class HotSearchListBean extends BaseBean {

        private String name;
        private String title;
        private String icon;
        private String type;
        private String iconLocal;//icon 本地路径

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
        public String getIcon() {
            return icon;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public String getIconLocal() {
            return iconLocal;
        }

        public void setIconLocal(String iconLocal) {
            this.iconLocal = iconLocal;
        }
    }
}