package com.mine.van.androidlib.network.okhttp.utils;

import android.content.Context;

import com.mine.van.androidlib.network.okhttp.OKHttpControls;
import com.mine.van.androidlib.network.okhttp.callback.GzipCallback;
import com.mine.van.androidlib.network.okhttp.interceptor.GzipRequestInterceptor;
import com.mine.van.androidlib.network.okhttp.request.PostStringRequest;

import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Callback;

/**
 * Created by fanjh on 2016/4/21.
 */
public class GzipUtils {

    public static void execute(Context context,String url,Map<String,String> params,Map<String,String> headers, Object tag, CacheControl cacheControl, GzipCallback gzipCallback){
        PostStringRequest postStringRequest = new PostStringRequest(url,params,headers,tag,cacheControl);
        OKHttpControls.getInstance(context).execute(context,new GzipRequestInterceptor(),postStringRequest,gzipCallback);
    }

}
