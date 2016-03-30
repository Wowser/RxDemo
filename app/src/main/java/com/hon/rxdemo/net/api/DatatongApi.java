package com.hon.rxdemo.net.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.api
 * @Date 2016/2/17 17:26
 * @Des
 */
public interface DatatongApi {

    @FormUrlEncoded @POST("FeedBack") Observable<String> postToModel(@Field("Json") String json);

}
