package com.mine.van.androidlib.network.okhttp.request;

import com.mine.van.androidlib.network.okhttp.entity.FileEntity;

import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Request;

/**
 * Created by fanjh on 2016/4/17.
 */
public abstract class BaseRequest {
    protected String url;
    protected Map<String,String> params;
    protected List<FileEntity> files;
    protected Map<String,String> headers;
    protected Object tag;
    protected CacheControl cacheControl;

    public BaseRequest(String url, Map<String, String> params, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        this.url = url;
        this.params = params;
        this.headers = headers;
        this.tag = tag;
        this.cacheControl = cacheControl;
    }

    public BaseRequest(String url, List<FileEntity> files, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        this.url = url;
        this.files = files;
        this.headers = headers;
        this.tag = tag;
        this.cacheControl = cacheControl;
    }

    public BaseRequest(String url, Map<String, String> params, List<FileEntity> files, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        this.url = url;
        this.params = params;
        this.files = files;
        this.headers = headers;
        this.tag = tag;
        this.cacheControl = cacheControl;
    }

    public abstract Request request();

    /**
     * 初始化cacheControl、tag和headers
     * @param builder
     */
    protected void setCommonBuilder(Request.Builder builder){
        if(null != cacheControl)
            builder.cacheControl(cacheControl);
        if(null != tag)
            builder.tag(tag);
        if(null != headers) {
            for (String key : headers.keySet())
                builder.addHeader(key, headers.get(key));
        }
    }

}
