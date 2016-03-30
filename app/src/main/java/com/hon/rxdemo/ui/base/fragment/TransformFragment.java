package com.hon.rxdemo.ui.base.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.rxdemo.R;
import com.hon.rxdemo.databinding.FragmentTransformBinding;
import com.hon.rxdemo.ui.base.BaseFragment;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;


/**
 * @author liuhehong
 * @project RxDemo
 * @packageName com.zf.rxdemo.fragment
 * @date 2016/3/30 11:27
 * @des 变换操作
 */
public class TransformFragment extends BaseFragment {

    private FragmentTransformBinding mBinding;

    @Override protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transform, container, false);
        setData();
        return mBinding.getRoot();
    }

    private void setData() {
        RxView.clickEvents(mBinding.btnFlatMap).subscribe(this::clickFlatMap);
    }

    /**
     * FlatMap  使用一个指定的函数对原始Observable发射的每一项数据执行变换操作，这个函数返回一个本身也发射数据的Observable，
     *          然后FlatMap合并这些Observables发射的数据，最后将合并后的结果当做它自己的数据序列发射
     * @param event
     */
    private void clickFlatMap(ViewClickEvent event) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("1");
                }
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<String, Observable<Integer>>() {
            @Override public Observable<Integer> call(String s) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override public void call(Subscriber<? super Integer> subscriber) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(8);
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribe(new Action1<Integer>() {
            @Override public void call(Integer integer) {
                mBinding.tvResult.setText(integer);
            }
        });
    }

    private void clickGroupBy(ViewClickEvent event) {
        Observable.just(1)
                .groupBy(new Func1<Integer, String>() {
            @Override public String call(Integer integer) {
                return integer.toString();
            }
        }).subscribe(new Action1<GroupedObservable<String, Integer>>() {
            @Override public void call(GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
                Logger.w("groupBy:"+stringIntegerGroupedObservable.toString());
            }
        });
    }


}
