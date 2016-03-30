package com.hon.rxdemo.net.api;


import com.hon.rxdemo.model.Gank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.api
 * @Date 2016/1/11 16:50
 * @Des
 */
public interface GithubService {

    //http://gank.avosapps.com/api/data/%E7%A6%8F%E5%88%A9/10/6
    @GET("data/%E7%A6%8F%E5%88%A9/10/{page}") Gank getGank(@Path("page") int page);

    @GET("data/%E7%A6%8F%E5%88%A9/10/{page}") Observable<Gank> getGankData(@Path("page") int page);



}
