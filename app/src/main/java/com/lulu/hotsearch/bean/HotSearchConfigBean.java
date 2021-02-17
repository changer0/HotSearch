/**
  * Copyright 2021 bejson.com 
  */
package com.lulu.hotsearch.bean;

/**
 * Auto-generated: 2021-02-17 14:57:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HotSearchConfigBean {

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