package com.qq.reader.bookstore;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.qq.reader.zebra.utils.CastUtils;

/**
 * @author zhanglulu
 */

public class LaunchParams implements Parcelable {
    private LaunchParams() { }
    private boolean pullRefreshEnable = false;
    private boolean loadMoreEnable = false;
    private String title;
    private Class<? extends BaseBookStoreViewModel> viewModelClass;
    private Bundle extras;


    public boolean isPullRefreshEnable() {
        return pullRefreshEnable;
    }

    public boolean isLoadMoreEnable() {
        return loadMoreEnable;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends BaseBookStoreViewModel> getViewModelClass() {
        return viewModelClass;
    }

    public Bundle getExtras() {
        return extras;
    }

    protected LaunchParams(Parcel in) {
        pullRefreshEnable = in.readByte() != 0;
        loadMoreEnable = in.readByte() != 0;
        title = in.readString();
        viewModelClass = CastUtils.cast(in.readSerializable());
        extras = in.readBundle(getClass().getClassLoader());
    }

    public static final Creator<LaunchParams> CREATOR = new Creator<LaunchParams>() {
        @Override
        public LaunchParams createFromParcel(Parcel in) {
            return new LaunchParams(in);
        }

        @Override
        public LaunchParams[] newArray(int size) {
            return new LaunchParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (pullRefreshEnable ? 1:0));
        dest.writeByte((byte) (loadMoreEnable ? 1:0));
        dest.writeString(title);
        dest.writeSerializable(viewModelClass);
        dest.writeBundle(extras);
    }

    public static class Builder {
        protected LaunchParams P = P();
        protected LaunchParams P() {
            return new LaunchParams();
        }

        public Builder setPullRefreshEnable(boolean pullRefreshEnable) {
            P.pullRefreshEnable = pullRefreshEnable;
            return this;
        }

        public Builder setLoadMoreEnable(boolean loadMoreEnable) {
            P.loadMoreEnable = loadMoreEnable;
            return this;
        }

        public Builder setExtras(Bundle extras) {
            P.extras = extras;
            return this;
        }

        public Builder setTitle(String title) {
            P.title = title;
            return this;
        }

        public Builder setViewModelClass(Class<? extends BaseBookStoreViewModel> viewModelClass) {
            P.viewModelClass = viewModelClass;
            return this;
        }

        public LaunchParams build() {
            return P;
        }
    }
}