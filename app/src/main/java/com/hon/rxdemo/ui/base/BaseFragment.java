package com.hon.rxdemo.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.base
 * @Date 2016/1/14 9:39
 * @Des
 */
public abstract class BaseFragment extends Fragment {

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

  /**
   * compose()是唯一一个能够从数据流中得到原始Observable<T>的操作符，所以，那些需要对整个数据流产生作用的操作（比如，subscribeOn()和observeOn()）需要使用compose()来实现。相较而言，如果在flatMap()中使用subscribeOn()或者observeOn()，那么它仅仅对在flatMap()中创建的Observable起作用，而不会对剩下的流产生影响（译者注：深坑，会在后面的系列着重讲解，欢迎关注）。
   当创建Observable流的时候，compose()会立即执行，犹如已经提前写好了一个操作符一样，而flatMap()则是在onNext()被调用后执行，onNext()的每一次调用都会触发flatMap()，也就是说，flatMap()转换每一个事件，而compose()转换的是整个数据流。
   因为每一次调用onNext()后，都不得不新建一个Observable，所以flatMap()的效率较低。事实上，compose()操作符只在主干数据流上执行操作。
   */
  final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override public Object call(Object o) {
            return ((Observable)o).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    <T> Observable.Transformer<T, T> applaySchedulers() {
        return (Observable.Transformer<T, T>)schedulersTransformer;
    }

    @Override public void onStart() {
        super.onStart();
    }

    @Override public void onResume() {
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onStop() {
        super.onStop();
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
    }
}
