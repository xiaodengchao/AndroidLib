package com.mine.van.androidlib.network.okhttp.request;

import com.mine.van.androidlib.network.okhttp.entity.FileEntity;

import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by fanjh on 2016/4/21.
 */
public class PostFormRequest extends BaseRequest{
    public static final String BINARY_TYPE = "application/octet-stream";

    public PostFormRequest(String url, Map<String, String> params, List<FileEntity> files, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        super(url, params, files, headers, tag, cacheControl);
    }

    @Override
    public Request request() {
        Request.Builder builder = new Request.Builder();
        setCommonBuilder(builder);
        MultipartBody.Builder mBuilder = new MultipartBody.Builder();
        if(null != params) {
            for(String key:params.keySet()){
                String value = params.get(key);
                if(null != value)
                    mBuilder.addFormDataPart(key,value);
            }
        }
        if(null != files){
            for(FileEntity fileEntity : files){
                if(null != fileEntity && null != fileEntity.getFile())
                    mBuilder.addFormDataPart(fileEntity.getFormFileName(), fileEntity.getSaveFileName(), RequestBody.create(MediaType.parse(BINARY_TYPE),fileEntity.getFile()));
            }
        }
        System.out.println(mBuilder.toString());
        System.out.println(mBuilder.build().toString());
        builder.post(mBuilder.build());
        builder.url(url);
        return builder.build();
    }
}
