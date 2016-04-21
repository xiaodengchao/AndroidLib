package com.mine.van.androidlib.network.okhttp.request;

import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Request;

/**
 * Created by fanjh on 2016/4/17.
 */
public class GetRequest extends BaseRequest{

    public GetRequest(String url, Map<String, String> params, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        super(url, params, headers, tag, cacheControl);
    }

    public Request request(){
        if(url == null)
            return null;
        StringBuffer stringBuffer = new StringBuffer(url+"?");
        Request.Builder builder = new Request.Builder();
        setCommonBuilder(builder);
        if(null != params) {
            for (String key : params.keySet())
                stringBuffer.append(key + "=" + params.get(key) + "&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        builder.url(stringBuffer.toString());
        return builder.build();
    }

}
