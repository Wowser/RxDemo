package com.hon.rxdemo.net.api;


import com.hon.rxdemo.model.UrlCheck;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.api
 * @Date 2016/1/12 16:25
 * @Des
 */
public interface GfycatService {

    //
    @GET("cajax/checkUrl/{url}") Observable<UrlCheck> checkUrl(@Path("url") String url);
}
