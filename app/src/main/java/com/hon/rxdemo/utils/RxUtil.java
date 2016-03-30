package com.hon.rxdemo.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @Author liuhehong
 * @Project IMessage
 * @PackageName com.zf.imessage.utils
 * @Date 2016/1/11 16:17
 * @Des
 */
public class RxUtil {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }
}
