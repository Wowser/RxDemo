package com.hon.rxdemo.net;

import android.widget.Toast;

import com.hon.rxdemo.ui.base.BaseActivity;
import com.hon.rxdemo.ui.base.BaseFragment;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.manager
 * @Date 2016/1/14 9:33
 * @Des Retrofit网络请求管理类
 */
public abstract class RequestManager<T> implements Callback<T> {

    private   BaseActivity mActivity;
    private   BaseFragment mFragment;
    protected boolean      mIsShowDialog;
    protected Class<T>     tClass;

    protected WeakReference<BaseActivity> mWeakActivity;
    protected WeakReference<BaseFragment> mWeakFragment;

    protected RequestManager(BaseActivity activity) {
        this(activity, false);
    }

    protected RequestManager(BaseActivity activity, boolean isShowDialog) {
        this.mActivity = activity;
        init(isShowDialog);
        if (this.mWeakActivity == null) {
            this.mWeakActivity = new WeakReference<>(mActivity);
        }
    }

    protected RequestManager(BaseFragment fragment) {
        this(fragment, false);
    }

    protected RequestManager(BaseFragment fragment, boolean isShowDialog) {
        this.mFragment = fragment;
        init(isShowDialog);
        if (this.mWeakFragment == null) {
            this.mWeakFragment = new WeakReference<>(this.mFragment);
        }
    }

    void init(boolean isShowDialog) {
        this.mIsShowDialog = isShowDialog;
        initClass();
    }

    private void initClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        tClass = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    public BaseActivity getActivity() {
        return mWeakActivity.get();
    }

    public BaseFragment getFragment() {
        return mWeakFragment.get();
    }

    @Override public void onResponse(Response<T> response) {
        if (response.code() == 200) {
            T t = response.body();
            onSuccess(t);
        }else {
            Toast.makeText(this.mActivity, "网络请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void onFailure(Throwable t) {
        t.printStackTrace();
    }

    protected abstract void onSuccess(T t);
}
