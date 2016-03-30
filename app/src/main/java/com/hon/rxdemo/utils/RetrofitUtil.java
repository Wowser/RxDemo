package com.hon.rxdemo.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hon.rxdemo.conf.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.utils
 * @Date 2016/1/14 10:01
 * @Des Retrofit工具类
 */
public class RetrofitUtil {

    private static Retrofit mRetrofit;

    public static <T> T createApi(Context context, Class<T> clazz) {
        if (mRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofit == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(Constant.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(Constant.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                            .cache(new Cache(context.getCacheDir(), Constant.CACHE_MAX_SIZE))
                            .addNetworkInterceptor(new Interceptor() {
                                @Override public Response intercept(Chain chain) throws IOException {
                                    Response originalResponse = chain.proceed(chain.request());
                                    return originalResponse.newBuilder()
                                            .removeHeader("Pragma")    //先移除默认的缓存请求头
                                            .header("Cache-Control", String.format("max-age=%d", Constant.CACHE_MAX_SIZE))  //添加自定义的缓存请求头
                                            .build();
                                }
                            })
                            .build();
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                            .create();
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }
        return mRetrofit.create(clazz);
    }
}
