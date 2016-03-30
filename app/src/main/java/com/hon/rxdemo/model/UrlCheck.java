package com.hon.rxdemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.model
 * @Date 2016/1/12 17:16
 * @Des
 */
public class UrlCheck implements Gfy{

    @SerializedName("gfyName")
    private boolean mUrlKnown;

    @SerializedName("gfyName")
    private String mGfyName;

    public boolean isUrlKnown() {
        return mUrlKnown;
    }

    public String getGfyName() {
        return mGfyName;
    }

    @Override
    public String toString() {
        return "UrlCheck{" +
                "mUrlKnown=" + mUrlKnown +
                ", mGfyName='" + mGfyName + '\'' +
                '}';
    }
}
