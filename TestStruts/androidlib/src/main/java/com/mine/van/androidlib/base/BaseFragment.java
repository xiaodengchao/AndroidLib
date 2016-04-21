package com.mine.van.androidlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fanjh on 2016/4/17.
 */
public abstract class BaseFragment extends Fragment{
    private View view;
    /**
     * 推荐重写参数，并且设置static的模式，根据使用情况设定arguments，实现封装化
     * @return
     */
    public abstract Fragment newInstance(String ...params);

    private BaseFragment() {
        throw new Error("don't need init");
    }

    /**
     * 处理转屏和异常重启的保存参数获取，将在initView之前执行
     * @param bundle
     */
    public abstract void initVariable(Bundle bundle);

    /**
     * 初始化视图
     * @param inflater 用于加载xml
     * @param container 父布局
     * @return 视图
     */
    public abstract View initView(LayoutInflater inflater,ViewGroup container);

    /**
     * 获取数据并进行相应处理，访问网络可以
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
     * 当页面结束时，停止所有不必要的网络访问
     */
    public abstract void cancelRequest();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            initVariable(savedInstanceState);
        }
        if(view == null){
            view = initView(inflater,container);
            initData();
            initListener();
            initAdapter();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup viewGroup = (ViewGroup)(view.getParent());
        viewGroup.removeView(view);
        cancelRequest();
    }
}
