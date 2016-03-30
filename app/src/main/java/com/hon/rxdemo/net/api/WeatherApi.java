package com.hon.rxdemo.net.api;


import com.hon.rxdemo.model.WeatherData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.api
 * @Date 2016/1/12 17:42
 * @Des
 */
public interface WeatherApi {

    //http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=2de143494c0b295cca9337e1e96b00e0
    //@Query注释用于组装请求参数，我们这有两个参数，一个是place（代表位置），另一个是units计量单位。
    @GET("weather") Observable<WeatherData> getWeather(@Query("q") String city);

    @GET("weather") WeatherData getWeather(@Query("q") String place, @Query("units") String units);

    //http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=2de143494c0b295cca9337e1e96b00e0
    @GET("weather") Observable<WeatherData> getWeath(@Query("lat") double lat, @Query("lon") double lon);

}
