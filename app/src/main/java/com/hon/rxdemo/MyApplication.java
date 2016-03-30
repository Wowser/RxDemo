package com.hon.rxdemo;

import android.app.Application;
import android.content.Context;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo
 * @Date 2016/1/11 16:48
 * @Des
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
