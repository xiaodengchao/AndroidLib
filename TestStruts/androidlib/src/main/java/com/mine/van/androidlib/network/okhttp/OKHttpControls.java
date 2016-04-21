package com.mine.van.androidlib.network.okhttp;

import android.content.Context;

import com.mine.van.androidlib.network.okhttp.cache.DefaultCache;
import com.mine.van.androidlib.network.okhttp.request.BaseRequest;


import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;


/**
 * Created by fanjh on 2016/4/17.
 */
public class OKHttpControls implements Serializable{
    private static final long serialVersionUID = 8636181206003237857L;
    public static final int DEFAULT_READ_TIMEOUT = 10;
    public static final int DEFAULT_WRITE_TIMEOUT = 10;
    public static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static OKHttpControls okHttpControls;
    private static OkHttpClient okHttpClient;
    private static DefaultCache defaultCache;

    private OKHttpControls(Context context) {
        initOkHttpClient(context);
    }

    private void initOkHttpClient(Context context){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();
            try {
                defaultCache = new DefaultCache(context);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            okHttpClient = okHttpClient.newBuilder().connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT,TimeUnit.SECONDS).readTimeout(DEFAULT_READ_TIMEOUT,TimeUnit.SECONDS)
                    .cache(defaultCache.getCache()).build();
        }
    }

    /**
     * 通过DCL单例，保证okHttpClient的唯一性
     * @param context
     * @return
     */
    public static OKHttpControls getInstance(Context context){
        if(okHttpControls == null){
            synchronized (OKHttpControls.class){
                if(okHttpControls == null)
                    okHttpControls = new OKHttpControls(context);
            }
        }
        return okHttpControls;
    }

    public final void execute(Context context,BaseRequest baseRequest,Callback callback){
        initOkHttpClient(context);
        okHttpClient.newCall(baseRequest.request()).enqueue(callback);
    }

    /**
     * 关闭所有没完成或还在队列的网络请求
     */
    public void cancelAllRequest(){
        okHttpClient.dispatcher().cancelAll();
    }

    /**
     * 关闭指定的没完成或还在队列的网络请求
     */
    public void cancelAllRequest(Object tag){
        for(Call call:okHttpClient.dispatcher().queuedCalls()){
            if(tag.equals(call.request().tag()))
                call.cancel();
        }
        for(Call call:okHttpClient.dispatcher().runningCalls()){
            if(tag.equals(call.request().tag()))
                call.cancel();
        }
    }

}
