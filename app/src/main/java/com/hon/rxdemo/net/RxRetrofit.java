package com.hon.rxdemo.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hon.rxdemo.MyApplication;
import com.hon.rxdemo.net.api.DatatongApi;
import com.hon.rxdemo.net.api.GfycatService;
import com.hon.rxdemo.net.api.GithubService;
import com.hon.rxdemo.net.api.TaobaoApi;
import com.hon.rxdemo.net.api.WeatherApi;

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
 * @PackageName com.zf.rxdemo.manager
 * @Date 2016/1/11 18:17
 * @Des
 */
public class RxRetrofit {

    GithubService mService;
    private static int maxCacheSize = 36000;
    private static final String BASE_URL = "http://gank.avosapps.com/api/";
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String WEATHER_KEY = "27a8b6ede26e762b53e150f41b64e8ef";
    public static final String EXAMPLE_KEY = "2de143494c0b295cca9337e1e96b00e0";
    private static final String GFYCAT_URL = "http://upload.gfycat.com/";
    private static final String TAOBAO_URL = "https://suggest.taobao.com/";
    private static final String DATATONG_URL = "http://192.168.0.201:8805/WebService/DaDaTongService.asmx/";

    private final GfycatService mGfycatService;
    private final WeatherApi    mWeatherApi;
    private final TaobaoApi     mTaobaoApi;
    private final DatatongApi   mDatatongApi;

    RxRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .cache(new Cache(MyApplication.getContext().getCacheDir(), maxCacheSize))
                .addNetworkInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")    //先移除默认的缓存请求头
                                .header("Cache-Control", String.format("max-age=%d", maxCacheSize))  //添加自定义的缓存请求头
                                .build();
                    }
                })
                .build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mService = retrofit.create(GithubService.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                 .baseUrl(WEATHER_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mWeatherApi = retrofit2.create(WeatherApi.class);

        Retrofit retrofit1 = new Retrofit.Builder()
                 .baseUrl(GFYCAT_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mGfycatService = retrofit1.create(GfycatService.class);

        Retrofit retrofit3 = new Retrofit.Builder()
                 .baseUrl(TAOBAO_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mTaobaoApi = retrofit3.create(TaobaoApi.class);

        Retrofit retrofit5 = new Retrofit.Builder()
                .baseUrl(DATATONG_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mDatatongApi = retrofit5.create(DatatongApi.class);
    }

    public GithubService getService() {
        return mService;
    }

    public GfycatService getGfycatService() {
        return mGfycatService;
    }

    public WeatherApi getWeatherApi() {
        return mWeatherApi;
    }

    public TaobaoApi getTaobaoApi() {
        return mTaobaoApi;
    }

    public DatatongApi getDatatongApi() {
        return mDatatongApi;
    }
}
