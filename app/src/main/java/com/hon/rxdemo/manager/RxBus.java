package com.hon.rxdemo.manager;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.manager
 * @Date 2016/1/19 15:16
 * @Des 使用RxJava实现EventBus
 */
public class RxBus {

    private static RxBus _rxBus = null;

    private RxBus() {

    }

    public static RxBus getSingleton() {
        if (_rxBus == null) {
            synchronized (RxBus.class) {
                if (_rxBus == null) {
                    _rxBus = new RxBus();
                }
            }
        }
        return _rxBus;
    }

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object obj) {
        _bus.onNext(obj);
    }

    public Observable<Object> toObservable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}
