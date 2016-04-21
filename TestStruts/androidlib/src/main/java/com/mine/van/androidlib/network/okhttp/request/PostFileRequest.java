package com.mine.van.androidlib.network.okhttp.request;

import com.mine.van.androidlib.network.okhttp.entity.FileEntity;

import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by fanjh on 2016/4/19.
 */
public class PostFileRequest extends BaseRequest{
    public static final String BINARY_TYPE = "application/octet-stream";

    public PostFileRequest(String url, List<FileEntity> files, Map<String, String> headers, Object tag, CacheControl cacheControl) {
        super(url, files, headers, tag, cacheControl);
    }

    @Override
    public Request request() {
        if(url == null)
            return null;
        Request.Builder builder = new Request.Builder();
        setCommonBuilder(builder);
        MultipartBody.Builder mBuilder = new MultipartBody.Builder();
        if(files != null) {
            for(FileEntity file:files) {
                if(file != null && file.getFile() != null) {
                    RequestBody fileBody = RequestBody.create(MediaType.parse(BINARY_TYPE),file.getFile());
                    mBuilder.addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"" + file.getFormFileName() + "\";filename =\""+file.getSaveFileName()+"\""),fileBody);
                }
            }
        }
        builder.post(mBuilder.build());
        builder.url(url);
        return builder.build();
    }
}
