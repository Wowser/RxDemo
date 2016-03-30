package com.hon.rxdemo.ui.base.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hon.rxdemo.R;
import com.hon.rxdemo.databinding.ActivityMainBinding;
import com.hon.rxdemo.event.Event;
import com.hon.rxdemo.manager.ApiManager;
import com.hon.rxdemo.manager.RxBus;
import com.hon.rxdemo.model.Gank;
import com.hon.rxdemo.model.Taobao;
import com.hon.rxdemo.model.WeatherData;
import com.hon.rxdemo.net.RequestManager;
import com.hon.rxdemo.net.RxFactory;
import com.hon.rxdemo.ui.base.BaseActivity;
import com.hon.rxdemo.ui.base.fragment.AssistFragment;
import com.hon.rxdemo.ui.base.fragment.BooleanFragment;
import com.hon.rxdemo.ui.base.fragment.CombineFragment;
import com.hon.rxdemo.ui.base.fragment.ConcatFragment;
import com.hon.rxdemo.ui.base.fragment.ConnectFragment;
import com.hon.rxdemo.ui.base.fragment.CreateFragment;
import com.hon.rxdemo.ui.base.fragment.ErrorFragment;
import com.hon.rxdemo.ui.base.fragment.FilterFragment;
import com.hon.rxdemo.ui.base.fragment.ToFragment;
import com.hon.rxdemo.ui.base.fragment.TransformFragment;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity {

    /**
     * Observable 可观察的对象, 一旦数据产生或发生改变,就会通过某种方式通知给观察者.
     * Observer 观察者对象, 监听Observable反射的数据并作出响应. Subscriber是它的特殊实现.
     *
     * 1.Observable和Subscriber可以做任何事情
     * Observable可以是一个数据库查询，Subscriber用来显示查询结果；
     * Observable可以是屏幕上的点击事件，Subscriber用来响应点击事件；
     * Observable可以是一个网络请求，Subscriber用来显示请求结果。
     * <p/>
     * 2.Observable和Subscriber是独立于中间的变换过程的。
     * 在Observable和Subscriber中间可以增减任何数量的map。
     * 整个系统是高度可组合的，操作数据是一个很简单的过程。
     *
     */

    private String[] cities = {"Budapest,hu", "London,uk"};
    private RxBus    _rxBus = null;
    private CompositeSubscription _subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        _subscriptions = new CompositeSubscription();
        _rxBus = RxBus.getSingleton();

        binding.viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new CreateFragment();
                    case 1:
                        return new TransformFragment();
                    case 2:
                        return new FilterFragment();
                    case 3:
                        return new CombineFragment();
                    case 4:
                        return new ErrorFragment();
                    case 5:
                        return new AssistFragment();
                    case 6:
                        return new BooleanFragment();
                    case 7:
                        return new ConcatFragment();
                    case 8:
                        return new ConnectFragment();
                    case 9:
                        return new ToFragment();
                    default:
                        return new CreateFragment();
                }
            }

            @Override public int getCount() {
                return 10;
            }
        });
        binding.tabs.setupWithViewPager(binding.viewpager);
    }

    @Override protected void onStart() {
        super.onStart();
    }

    @Override protected void onStop() {
        super.onStop();
        _subscriptions.unsubscribe();
    }

    public void clickFAB(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void initData() {
        //requestToRetrofit();
        //rxSend();
        rxUpdateView();
    }

    class TaobaoRequest extends RequestManager<Taobao> {
        public TaobaoRequest(BaseActivity activity) {
            super(activity);
        }

        @Override protected void onSuccess(Taobao taobao) {
            Logger.w("Retrofit---->>"+taobao.toString());
        }
    }

    /**
     * 实现并发网络请求, 每个请求都使用不同的调用参数异步调用同一个url
     */
    private void rxSend() {
        //1.
        //观察者
//        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("hello rxjava");
//                subscriber.onCompleted();
//            }
//        });
//        //订阅者
//        Subscriber<String> mySubscriber = new Subscriber<String>() {
//            @Override public void onCompleted() {
//                Logger.w("onCompleted");
//            }
//
//            @Override public void onError(Throwable e) {
//                Logger.w("onError:"+e.getMessage());
//            }
//
//            @Override public void onNext(String s) {
//                Logger.w("onNext:"+s);
//            }
//        };
//        //观察者与订阅者的关联
//        myObservable.subscribe(mySubscriber);
        //2.简洁写法
        //如果仅仅为了发布一个事件就结束, 可以使用.just()方法.
        //如果我们不关心onCompleted和onError()方法, 可以使用Action1类
        Observable.just("hello rxjava").subscribe(new Action1<String>() {
            @Override public void call(String s) {
                Logger.w(s);
            }
        });
        //3.使用map操作符可以把一个事件转化另一个事件.
        Observable.just("hello rxjava")
                .map(new Func1<String, String>() {
                    @Override public String call(String s) {
                        return s + "--->map()";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override public void call(String s) {
                        Logger.w(s);
                    }
                });
        //这里我们将最初返回的Integer类型转化成了String类型
        Observable.just("hello rxjava")
                .map(new Func1<String, Integer>() {
                    @Override public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override public String call(Integer integer) {
                        return integer.toString();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override public void call(String s) {
                        Logger.w(s);
                    }
                });
        Logger.w("---------------------------------------------------");
        //4.Observable.flatMap()接收一个Observabel的输出作为输入, 同时输出另一个Observabel对象.
       /* RxFactory.getService().getGank(6)
                .flatMap(new Func1<Gank, Observable<Gank.ResultsEntity>>() {
                    @Override public Observable<Gank.ResultsEntity> call(Gank gank) {
                        return Observable.from(gank.getResults());
                    }
                })
                .map(new Func1<Gank.ResultsEntity, String>() {
                    @Override public String call(Gank.ResultsEntity s) {
                        return s.getWho();
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override public void call(String s) {
                        //使用doOnNext()方法可以在输入结果之前做一些额外的事情
                        Logger.w("doOnNext--->"+s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override public void call(String s) {
                        Logger.w(s);
                    }
                });*/
        ApiManager.getGank(6)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Gank>() {
                    @Override public void call(Gank s) {
                        Logger.w("Gank--->>" + s.getResults().get(0).getUrl());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Logger.w("Error:" + throwable.toString());
                    }
                });
        /*=============================== begin ===============================*/
        //5.from()方法可以使城市名称数组转化为一个Observable对象.将数组里的字符串提供给不同的线程.
        //mapMany()方法将会把前者提供的每一个字符串都转化为observable对象（译注：新对象包含的是weatherData对象数据）。
        //这里的转化通过调用ApiManager.getWeatherData()完成。
        Observable.from(cities)
                .flatMap(new Func1<String, Observable<WeatherData>>() {
                    @Override public Observable<WeatherData> call(String s) {
                        return ApiManager.getWethterData(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherData>() {
                    @Override public void call(WeatherData weather) {
                        //在这里可以执行自己的操作
                        Logger.w("weather--->>" + weather.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        //在这里可以做一些异常处理
                        Logger.w("Error:" + throwable.toString());
                    }
                });
        RxFactory.getWeatherApi().getWeath(35, 139)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherData>() {
                    @Override public void call(WeatherData s) {
                        Logger.w("getWeathData--->>" + s.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Logger.w("Error:" + throwable.toString());
                    }
                });
        ApiManager.getTaobao("utf-8", "%E6%89%8B%E6%9C%BA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Taobao>() {
                    @Override public void call(Taobao taobao) {
                        Logger.w("Taobao--->>" + taobao.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Logger.w("Error:" + throwable.toString());
                    }
                });
    }

    /**
     * RxBinding是基于RxJava的API
     * 使用它可以很好的提高程序的扩展性
     *
     */

    private void rxUpdateView() {
        Observable<Object> observable = _rxBus.toObservable().share();
        _subscriptions.add(observable.subscribe(new Action1<Object>() {
            @Override public void call(Object event) {
                if (event instanceof Event.IntEvent) {
                    _showRxBus();
                }
            }
        }));
        //定义一个定时器
        _subscriptions.add(Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Long>() {
                @Override public void call(Long aLong) {
                    Toast.makeText(MainActivity.this, "Observable.timer", Toast.LENGTH_SHORT).show();
                }
            }));
    }

    private void _showRxBus() {
        Toast.makeText(MainActivity.this, "RxBus", Toast.LENGTH_SHORT).show();
    }

    private String encyptMap(String code, String q) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("q", q);
        return new Gson().toJson(map);
    }


    private void getFeedback() {
        RxFactory.getDatatongApi().postToModel(encyptMap2("+8618679181858", "haha"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override public void call() {
                        Logger.w("doOnSubscribe()"); //1
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override public void call(String s) {
                        Logger.w("结果--------->>" + s);
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        Logger.w("Error:"+throwable.toString());
                    }
                });
        //Error:retrofit2.HttpException:HTTP 500 Internal Server Error
    }

    private String encyptMap2(String tell, String text) {
        Map<String, Object> map = new HashMap<>();
        map.put("Tell", tell);
        map.put("Text", text);
        return new Gson().toJson(map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
