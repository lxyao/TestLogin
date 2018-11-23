package com.cfwin.base.http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 服务器返回信息基础类
 * created by Yao on 2018-06-12
 */
public class ResponseInfo implements Parcelable{

    protected int result;
    protected String message;

    private String mSource;

    /**
     * 请求状态
     * @return 请求状态
     */
    public int getResult() {
        return result;
    }

    /**
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    public String getSource() {
        return mSource;
    }

    /**
     * @param mSource 源信息格式
     */
    public void setSource(String mSource) {
        this.mSource = mSource;
    }

    public ResponseInfo(){}

    protected ResponseInfo(Parcel in) {
        result = in.readInt();
        message = in.readString();
        mSource = in.readString();
    }

    public static final Creator<ResponseInfo> CREATOR = new Creator<ResponseInfo>() {
        @Override
        public ResponseInfo createFromParcel(Parcel in) {
            return new ResponseInfo(in);
        }

        @Override
        public ResponseInfo[] newArray(int size) {
            return new ResponseInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(result);
        parcel.writeString(message);
        parcel.writeString(mSource);
    }
}
