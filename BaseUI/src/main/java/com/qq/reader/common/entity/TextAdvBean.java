package com.qq.reader.common.entity;

import androidx.annotation.IdRes;

/**
 * Author: p_bbinlliu
 * Date: 2018/9/2 15:19
 * Description: 文字链广告实体类
 */

public class TextAdvBean {
    private String mIconUrl;
    private String mContent;
    
    @IdRes
    private int mIconPath;

    public int getIconPath() {
        return mIconPath;
    }

    public void setIconPath(int iconPath) {
        mIconPath = iconPath;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
