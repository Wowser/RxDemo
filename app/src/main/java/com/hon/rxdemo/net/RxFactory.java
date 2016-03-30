package com.hon.rxdemo.net;

import com.hon.rxdemo.net.api.DatatongApi;
import com.hon.rxdemo.net.api.GfycatService;
import com.hon.rxdemo.net.api.GithubService;
import com.hon.rxdemo.net.api.TaobaoApi;
import com.hon.rxdemo.net.api.WeatherApi;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.manager
 * @Date 2016/1/11 18:20
 * @Des
 */
public class RxFactory {

    protected static final Object        obj            = new Object();
    static                 GithubService mService       = null;
    static                 GfycatService mGfycatService = null;
    static                 WeatherApi    mWeatherApi    = null;
    static                 TaobaoApi     mTaobaoApi     = null;
    static                 DatatongApi   mDatatongApi   = null;

    public static GithubService getService() {
        synchronized (obj) {
            if (mService == null) {
                mService = new RxRetrofit().getService();
            }
            return mService;
        }
    }

    public static GfycatService getGfycatService() {
        synchronized (obj) {
            if (mGfycatService == null) {
                mGfycatService = new RxRetrofit().getGfycatService();
            }
            return mGfycatService;
        }
    }

    public static WeatherApi getWeatherApi() {
        synchronized (obj) {
            if (mWeatherApi == null) {
                mWeatherApi = new RxRetrofit().getWeatherApi();
            }
            return mWeatherApi;
        }
    }

    public static TaobaoApi getTaobaoApi() {
        synchronized (obj) {
            if (mTaobaoApi == null) {
                mTaobaoApi = new RxRetrofit().getTaobaoApi();
            }
            return mTaobaoApi;
        }
    }

    public static DatatongApi getDatatongApi() {
        synchronized (obj) {
            if (mDatatongApi == null) {
                mDatatongApi = new RxRetrofit().getDatatongApi();
            }
            return mDatatongApi;
        }
    }
}
