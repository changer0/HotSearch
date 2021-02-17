/**
  * Copyright 2021 bejson.com 
  */
package com.lulu.hotsearch.bean;

import com.lulu.hotsearch.bean.BaseBean;

/**
 * Auto-generated: 2021-02-13 11:22:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result extends BaseBean {
    private String url;
    private String realUrl;
    private String title;
    private String hotNum;
    private String tag;
    private String order;

    public void setUrl(String url) {
         this.url = url;
     }
    public String getUrl() {
     return url;
    }

    public void setTitle(String title) {
         this.title = title;
     }

    public String getTitle() {
     return title;
    }

    public String getHotNum() {
        return hotNum;
    }

    public void setHotNum(String hotNum) {
        this.hotNum = hotNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }
}