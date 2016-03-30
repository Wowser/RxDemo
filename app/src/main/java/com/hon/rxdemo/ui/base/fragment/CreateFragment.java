package com.hon.rxdemo.ui.base.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.rxdemo.R;
import com.hon.rxdemo.databinding.FragmentCreateBinding;
import com.hon.rxdemo.ui.base.BaseFragment;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.orhanobut.logger.Logger;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * @author liuhehong
 * @project RxDemo
 * @packageName com.zf.rxdemo.fragment
 * @date 2016/3/30 11:26
 * @des 创建操作
 */
public class CreateFragment extends BaseFragment {

    private FragmentCreateBinding mBinding;

    @Override protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false);
        setData();
        return mBinding.getRoot();
    }

    /**
     * this::onCreateClicked  --> Lambda表达式中的方法引用:方法参数与返回值和Lambda表达式一样时, 可直接使用方法名代替
     *
     */
    private void setData() {
        RxView.clickEvents(mBinding.btnCreate).subscribe(this::clickCreate);
        RxView.clickEvents(mBinding.btnDefer).subscribe(this::clickDefer);
    }

    /**
     * Create 从头创建一个Observable
     * @param viewClickEvent
     */
    private void clickCreate(ViewClickEvent viewClickEvent) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        for (int i=0; i<5; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                }catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override public void onCompleted() {
                Logger.w("onCompleted");
            }

            @Override public void onError(Throwable e) {
                Logger.e("onError:"+e.toString());
            }

            @Override public void onNext(Integer integer) {
                mBinding.tvResult.setText(integer);
            }
        });
    }

    /**
     * Defer 直到有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
     * @param viewClickEvent
     */
    private void clickDefer(ViewClickEvent viewClickEvent) {
        Observable.defer(new Func0<Observable<String>>() {
            @Override public Observable<String> call() {
                return Observable.just("1");
            }
        }).subscribe(new Observer<String>() {
            @Override public void onNext(String s) {
                mBinding.tvResult.setText(s);
            }

            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {

            }
        });
    }

    /**
     * From 可转换Future, Iterable, 数组类型
     * @param event
     */
    private void clickFrom(ViewClickEvent event) {
        Integer[] items = {0, 1, 2, 3, 4};
        Observable.from(items).subscribe(new Action1<Integer>() {
            @Override public void call(Integer integer) {
                mBinding.tvResult.setText(integer);
            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
                Logger.e("onError:"+throwable.toString());
            }
        }, new Action0() {
            @Override public void call() {
                Logger.w("onCompleted");
            }
        });
    }

    /**
     * Interval 创建一个按固定时间间隔来发射整数序列的Observable
     * @param event
     */
    private void clickInterval(ViewClickEvent event) {
        Observable.interval(1000, TimeUnit.MILLISECONDS).subscribe((aLong -> {
            mBinding.tvResult.setText(aLong+"");
        }));
    }

    /**
     * Just 创建一个发射指定值的Observable
     * @param event
     */
    private void clickJust(ViewClickEvent event) {
        Observable.just(1).subscribe((integer -> {
            mBinding.tvResult.setText(integer);
        }));
    }
}

