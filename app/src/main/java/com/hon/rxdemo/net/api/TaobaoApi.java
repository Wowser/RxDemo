package com.hon.rxdemo.net.api;


import com.hon.rxdemo.model.Taobao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.api
 * @Date 2016/1/12 18:28
 * @Des
 */
public interface TaobaoApi {

    //post和get的区别当中有一点就是参数的位置，get放在url路径当中，post放在请求体当中
    //@Query用于组装请求参数, 用于查询(一个键值对)
    //@QueryMap 可以用于查询更复杂的数据(多对键值对)
    //@Path  用于GET请求
    //@Field
    //@FormUrlEncoded修饰表单域，每个表单域子件key-value采用@Field修饰
    //@Multipart修饰用于文件上传，每个Part元素用@Part修饰

    //https://suggest.taobao.com/sug?code=utf-8&q=%E6%89%8B%E6%9C%BA
    @GET("sug") Call<Taobao> getTaobao2(@Query("code") String code, @Query("q") String keyword);

    @GET("sug") Taobao getTaobao(@Query("code") String code, @Query("q") String keyword);

    @GET("sug") Observable<Taobao> getTaobaoData(@Query("code") String code, @Query("q") String keyword);

    @FormUrlEncoded @POST("sug") Observable<String> getTaobaoData2(@Field("Json") String json);
}
