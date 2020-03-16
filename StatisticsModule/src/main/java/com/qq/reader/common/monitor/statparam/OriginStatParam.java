package com.qq.reader.common.monitor.statparam;

import android.os.Parcel;
import android.os.Parcelable;

import com.qq.reader.common.gsonbean.BaseBean;

/**
 * @author zhanglulu on 2019/9/20.
 * for
 */
public class OriginStatParam extends BaseBean implements Parcelable {
    private String alg;
    private String origin;

    public OriginStatParam() {
    }

    protected OriginStatParam(Parcel in) {
        alg = in.readString();
        origin = in.readString();
    }


    public OriginStatParam setAlg(String alg) {
        this.alg = alg;
        return this;
    }

    public OriginStatParam setOrigin(String origin) {
        this.origin = origin;
        return this;
    }


    public String getAlg() {
        return alg;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OriginStatParam> CREATOR = new Creator<OriginStatParam>() {
        @Override
        public OriginStatParam createFromParcel(Parcel in) {
            return new OriginStatParam(in);
        }

        @Override
        public OriginStatParam[] newArray(int size) {
            return new OriginStatParam[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(alg);
        dest.writeString(origin);
    }
}
