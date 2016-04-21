package com.mine.van.androidlib.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by fanjh on 2016/4/17.
 */
public abstract class BaseActivity extends FragmentActivity{
    /**
     * 初始化变量（主要是获取intent的数据和初始化），同样可以处理异常参数，需要内部处理bundle是否为空的情况
     */
    public abstract void initVariables(Bundle bundle);
    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 获取数据并进行赋值等处理
     */
    public abstract void initData();

    /**
     * 初始化监听
     */
    public abstract void initListener();

    /**
     * 初始化适配器
     */
    public abstract void initAdapter();

    /**
     * 当页面结束时，停止所有不必要的网络访问,如果有handler，需要适当结束Runnable
     */
    public abstract void cancelRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables(savedInstanceState);
        initView();
        initData();
        initListener();
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelRequest();
    }
}
