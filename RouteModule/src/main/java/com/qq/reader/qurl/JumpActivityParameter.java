package com.qq.reader.qurl;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by yangtao on 2016/3/21.
 */
public class JumpActivityParameter implements Parcelable {

    public static final int FORRESULT_CODE_NONE = -1;
    public static final int JUMP_ACTIVITY_FLAG_DEFAULT = 0;

    //intent 所需flag
    private int flag = 0x00000000;
    //requestcode,默认不需要，-1
    private int requestCode = FORRESULT_CODE_NONE;
    private String qUrl = "";
    private Object objParamater = null;


    public JumpActivityParameter() {

    }

    public JumpActivityParameter(Parcel in) {
        readFromParcel(in);
    }

    public JumpActivityParameter setJsonParamater(Object p) {
        objParamater = p;
        return this;
    }

    public JumpActivityParameter setQurl(String url) {
        if (url != null) {
            qUrl = url;
        }
        return this;
    }

    public JumpActivityParameter setRequestCode(int request) {
        requestCode = request;
        return this;
    }

    public JumpActivityParameter setFlag(int f) {
        flag = f;
        return this;
    }

    public Object getJsonParamater() {
        return objParamater;
    }

    public int getFlag() {
        return flag;
    }

    public int getRequestCode() {
        return requestCode;
    }


    public String getQurl() {
        return qUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(flag);
        out.writeInt(requestCode);
        out.writeString(qUrl);
    }

    public void readFromParcel(Parcel in) {
        flag = in.readInt();
        requestCode = in.readInt();
        qUrl = in.readString();
    }

    public static final Creator<JumpActivityParameter> CREATOR = new Creator<JumpActivityParameter>() {
        public JumpActivityParameter createFromParcel(Parcel in) {
            return new JumpActivityParameter(in);
        }

        public JumpActivityParameter[] newArray(int size) {
            return new JumpActivityParameter[size];
        }
    };
}
