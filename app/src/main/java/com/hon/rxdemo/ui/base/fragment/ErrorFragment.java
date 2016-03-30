package com.hon.rxdemo.ui.base.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.rxdemo.R;
import com.hon.rxdemo.databinding.FragmentErrorBinding;
import com.hon.rxdemo.ui.base.BaseFragment;


/**
 * @author liuhehong
 * @project RxDemo
 * @packageName com.zf.rxdemo.fragment
 * @date 2016/3/30 11:29
 * @des
 */
public class ErrorFragment extends BaseFragment {

    @Override protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        FragmentErrorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_error, container, false);
        return binding.getRoot();
    }
}
