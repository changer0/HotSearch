package com.yuewen.component.router.builder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.yuewen.component.router.bean.YWRouterParam;

/**
 * Created by ronaldo on 2019/4/12.
 */

public class YWRouterParamBuilder {
    public static class Builder {
        private YWRouterParam mParam = new YWRouterParam();
        public Builder setBundle(Bundle bundle) {
            mParam.setBundle(bundle);
            return this;
        }

        public Bundle getBundle() {
            return mParam.getBundle();
        }

        public int getFlags() {
            return mParam.getFlags();
        }

        public Builder setFlags(int flags) {
            mParam.setFlags(flags);
            return this;
        }

        public String getAction() {
            return mParam.getAction();
        }

        public Builder setAction(String action) {
            mParam.setAction(action);
            return this;
        }

        public Builder setPath(String path) {
            mParam.setPath(path);
            return this;
        }

        public Builder setUri(String uri) {
            mParam.setPath(uri);
            return this;
        }

        public String getPath() {
            return mParam.getPath();
        }

        public Uri getUri() {
            return mParam.getUri();
        }

        public Object getTag() {
            return mParam.getTag();
        }

        public Builder setTag(Object tag) {
            mParam.setTag(tag);
            return this;
        }

        public int getTimeout() {
            return mParam.getTimeout();
        }

        public Builder setTimeout(int timeout) {
            mParam.setTimeout(timeout);
            return this;
        }

        public boolean getIsGreenChannel() {
            return mParam.getIsGreenChannel();
        }

        public Builder setGreenChannel(boolean greenChannel) {
            mParam.setGreenChannel(greenChannel);
            return this;
        }

        public int getEnterAnim() {
            return mParam.getEnterAnim();
        }

        public Builder setAnim(int enterAnim, int exitAnim) {
            mParam.setEnterAnim(enterAnim);
            mParam.setExitAnim(exitAnim);
            return this;
        }

        public int getExitAnim() {
            return mParam.getExitAnim();
        }

        public NavigationCallback getNavigationCallback() {
            return mParam.getNavigationCallback();
        }

        public Builder setNavigationCallback(NavigationCallback navigationCallback) {
            mParam.setNavigationCallback(navigationCallback);
            return this;
        }

        public Context getContext() {
            return mParam.getContext();
        }

        public Builder setContext(Context context) {
            mParam.setContext(context);
            mParam.setRequestCode(-1);
            return this;
        }

        public Builder setContextForResult(Activity activity, int requestCode) {
            mParam.setContext(activity);
            mParam.setRequestCode(requestCode);
            return this;
        }

        public int getRequestCode() {
            return mParam.getRequestCode();
        }

    }
}
