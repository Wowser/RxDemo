package com.hon.rxdemo.model;

import java.util.List;

/**
 * @Author liuhehong
 * @Project RxDemo
 * @PackageName com.zf.rxdemo.model
 * @Date 2016/1/12 18:30
 * @Des
 */
public class Taobao {

    private List<List<String>> result;

    public void setResult(List<List<String>> result) { this.result = result;}

    public List<List<String>> getResult() { return result;}

    @Override public String toString() {
        return "Taobao{" +
                "result=" + result +
                '}';
    }
}
