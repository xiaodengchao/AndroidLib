package com.mine.van.androidlib.network.okhttp.callback;



import android.os.Handler;
import android.os.Message;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by fanjh on 2016/4/17.
 */
public abstract class DefaultCallback implements Callback {
    private Handler handler = new MyHandler();
    public static final int DEFAULT_FAILURE = 1;
    public static final int DEFAULT_RESPONSE = 2;

    @Override
    public void onFailure(Call call, IOException e) {
        handler.sendEmptyMessage(DEFAULT_FAILURE);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        handler.obtainMessage(DEFAULT_RESPONSE, response).sendToTarget();
    }

    /**
     * UI主线程方法，如果需要后台线程，可以直接使用OKHttp的Callback
     *
     * @param response
     */
    public abstract void handlerResponse(Response response);

    public abstract void receiverResponseError();

    public abstract void handlerError();

    private class MyHandler extends Handler{
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DEFAULT_FAILURE:
                    handlerError();
                    break;
                case DEFAULT_RESPONSE:
                    Response response = (Response) msg.obj;
                    if (response != null)
                        handlerResponse(response);
                    else
                        receiverResponseError();
                    break;
            }
        }
    }

    public void cancelAllRunnable(){
        handler.removeCallbacksAndMessages(null);
    }
}
