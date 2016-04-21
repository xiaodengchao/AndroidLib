package com.mine.van.androidlib.network.okhttp.request;

import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by fanjh on 2016/4/19.
 */
public class PostStringRequest extends BaseRequest{

    public PostStringRequest(String url, Map<String, String> params, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        super(url, params, headers, tag, cacheControl);
    }

    @Override
    public Request request() {
        if(url == null)
            return null;
        Request.Builder builder = new Request.Builder();
        setCommonBuilder(builder);
        FormBody.Builder fBuilder = new FormBody.Builder();
        if(params != null) {
            for(String key:params.keySet()) {
                String value = params.get(key);
                if(value != null)
                    fBuilder.add(key, params.get(key));
                else
                    fBuilder.add(key,"");
            }
        }
        builder.post(fBuilder.build());
        builder.url(url);
        return builder.build();
    }
}
