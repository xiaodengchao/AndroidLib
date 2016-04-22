package com.mine.van.androidlib.network.okhttp.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

/**
 * Created by fanjh on 2016/4/22.
 */
public abstract class GzipCallback implements Callback{
    /**
     * 运行在后台线程
     */
    public abstract void handlerThreadError();
    /**
     * 运行在后台线程
     */
    public abstract void handlerGzipString(String json);
    @Override
    public void onFailure(Call call, IOException e) {
        handlerThreadError();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //response源码中已经设定不为null,否则会抛出异常
        Source source = response.body().source();
        GzipSource gzipSource = new GzipSource(source);
        BufferedSource bufferedSource = Okio.buffer(gzipSource);
        String json = bufferedSource.readUtf8();
        handlerGzipString(json);
    }
}
