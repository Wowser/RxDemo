package com.hon.rxdemo.manager;

import com.hon.rxdemo.model.Gank;
import com.hon.rxdemo.model.Taobao;
import com.hon.rxdemo.model.WeatherData;
import com.hon.rxdemo.net.RxFactory;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author LLhon
 * @Project RxDemo
 * @Package com.zf.rxdemo.manager
 * @Date 2016/1/11 23:10
 * @description 将实现一个API管理器负责生成observable对象，并完成多并发调用（每个调用都请求同一个地址，但参数不同）
 */
public class ApiManager {

    public static Observable<WeatherData> getWethterData(final String city) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override public void call(Subscriber<? super WeatherData> subscriber) {
                try {
                    subscriber.onNext(RxFactory.getWeatherApi().getWeather(city, "metric"));
                    subscriber.onCompleted();
                }catch (Exception e) {
                    subscriber.onError(e);
                }
                //return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.io());
    }

//    public static Observable<WeatherData> getWeathData(final double lat, final double lon) {
//        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
//            @Override public void call(Subscriber<? super WeatherData> subscriber) {
//                try {
//                    subscriber.onNext(RxFactory.getService().getWeath(lat, lon));
//                    subscriber.onCompleted();
//                }catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        }).subscribeOn(Schedulers.io());
//    }

    public static Observable<Gank> getGank(final int page) {
        return Observable.create(new Observable.OnSubscribe<Gank>() {
            @Override public void call(Subscriber<? super Gank> subscriber) {
                try {
                    subscriber.onNext(RxFactory.getService().getGank(page));
                    subscriber.onCompleted();
                }catch (Exception e) {
                    subscriber.onError(e);
                }
                //return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static Observable<Taobao> getTaobao(final String code, final String keyword) {
        return Observable.create(new Observable.OnSubscribe<Taobao>() {
            @Override public void call(Subscriber<? super Taobao> subscriber) {
                try {
                    subscriber.onStart();
                    subscriber.onNext(RxFactory.getTaobaoApi().getTaobao(code, keyword));
                    subscriber.onCompleted();
                }catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static Observable<String> getTaobao2(final Observable<String> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<String, Observable<? extends String>>() {
            @Override public Observable<? extends String> call(String s) {
                return RxFactory.getTaobaoApi().getTaobaoData2(s);
            }
        });
    }

    public static Observable<String> requestFeedBack(final Observable<String> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<String, Observable<? extends String>>() {
                    @Override public Observable<? extends String> call(String s) {
                        return RxFactory.getDatatongApi().postToModel(s);
                    }
                });
    }
}
