package com.hon.rxdemo.ui.base.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.rxdemo.R;
import com.hon.rxdemo.databinding.FragmentCombineBinding;
import com.hon.rxdemo.ui.base.BaseFragment;


/**
 * @author liuhehong
 * @project RxDemo
 * @packageName com.zf.rxdemo.fragment
 * @date 2016/3/30 11:29
 * @des
 */
public class CombineFragment extends BaseFragment {

    @Override protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        FragmentCombineBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_combine, container, false);
        return binding.getRoot();
    }
}
