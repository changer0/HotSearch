package com.yuewen.component.router.bean;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Created by ronaldo on 2019/9/29.
 * 组件跳转参数
 */
public class YWRouterParam {
    private String action;
    public Bundle bundle;
    private int flags = -1;         // Flags of route

    public String path;
    public Uri uri;
    private Object tag;             // A tag prepare for some thing wrong.
    private int timeout = 300;      // Navigation timeout, TimeUnit.Second
    private boolean greenChannel;
    private int enterAnim = -1;
    private int exitAnim = -1;
    private NavigationCallback navigationCallback;
    private Context context;
    private int requestCode = -1;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean getIsGreenChannel() {
        return greenChannel;
    }

    public void setGreenChannel(boolean greenChannel) {
        this.greenChannel = greenChannel;
    }

    public int getEnterAnim() {
        return enterAnim;
    }

    public void setEnterAnim(int enterAnim) {
        this.enterAnim = enterAnim;
    }

    public int getExitAnim() {
        return exitAnim;
    }

    public void setExitAnim(int exitAnim) {
        this.exitAnim = exitAnim;
    }

    public NavigationCallback getNavigationCallback() {
        return navigationCallback;
    }

    public void setNavigationCallback(NavigationCallback navigationCallback) {
        this.navigationCallback = navigationCallback;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
