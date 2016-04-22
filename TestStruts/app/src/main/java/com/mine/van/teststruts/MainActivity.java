package com.mine.van.teststruts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.mine.van.androidlib.network.okhttp.OKHttpControls;
import com.mine.van.androidlib.network.okhttp.callback.DefaultCallback;
import com.mine.van.androidlib.network.okhttp.callback.GzipCallback;
import com.mine.van.androidlib.network.okhttp.entity.FileEntity;
import com.mine.van.androidlib.network.okhttp.request.GetRequest;
import com.mine.van.androidlib.network.okhttp.request.PostFileRequest;
import com.mine.van.androidlib.network.okhttp.request.PostFormRequest;
import com.mine.van.androidlib.network.okhttp.request.PostStringRequest;
import com.mine.van.androidlib.network.okhttp.utils.GzipUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by fanjh on 2016/4/17.
 */
public class MainActivity extends Activity{
    public static final String URL = "http://172.30.66.187:8080/TestOkHttp/servlet/TestServlet";
    public static final String FILE_URL = "http://172.30.66.187:8080/TestOkHttp/servlet/FileServlet";
    private TextView textView;
    private DefaultCallback defaultCallback = new DefaultCallback() {
        @Override
        public void handlerResponse(Response response) {
            Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_LONG).show();
        }

        @Override
        public void receiverResponseError() {
            Toast.makeText(getApplicationContext(),"receiver error",Toast.LENGTH_LONG).show();
        }

        @Override
        public void handlerError() {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView1);
        //get();
        //postString();
        //postFile();
        //postForm();
        postGzipString();
    }

    private void postGzipString(){
        Map<String,String> map = new HashMap<>();
        map.put("name", "xiaoxiao");
        map.put("name1", "xiaoxiao1");
        map.put("name2", "xiaoxiao2");
        map.put("name3", "xiaoxiao3");
        GzipUtils.execute(this, URL, map, null, textView, null, new GzipCallback() {
            @Override
            public void handlerThreadError() {

            }

            @Override
            public void handlerGzipString(String json) {
                Log.i("Gzip",json);
            }
        });
    }

    private void postForm(){
        Map<String,String> map = new HashMap<>();
        map.put("name", "xiaoxiao");
        map.put("name1", "xiaoxiao1");
        map.put("name2", "xiaoxiao2");
        List<FileEntity> fileEntityList = new ArrayList<>();
        fileEntityList.add(new FileEntity("a1.zip", "image", new File(getExternalCacheDir() + "/1.zip")));
        PostFormRequest postFormRequest = new PostFormRequest(FILE_URL,map,fileEntityList,null,textView,null);
        OKHttpControls.getInstance(this).execute(this, postFormRequest, defaultCallback);
    }

    private void postFile(){
        List<FileEntity> fileEntityList = new ArrayList<>();
        fileEntityList.add(new FileEntity("a1.zip", "image", new File(getExternalCacheDir() + "/1.zip")));
        PostFileRequest postFileRequest = new PostFileRequest(FILE_URL,fileEntityList,null,textView,null);
        OKHttpControls.getInstance(this).execute(this, postFileRequest, defaultCallback);
    }

    private void postString(){
        Map<String,String> map = new HashMap<>();
        map.put("name", "xiaoxiao");
        map.put("name1", "xiaoxiao1");
        map.put("name2", "xiaoxiao2");
        map.put("name3", "xiaoxiao3");
        PostStringRequest postStringRequest = new PostStringRequest(URL,map,null,textView,null);
        OKHttpControls.getInstance(this).execute(this,postStringRequest,defaultCallback);
    }


    private void get(){
        Map<String,String> map = new HashMap<>();
        map.put("name","xiaoxiao");
        GetRequest getRequest = new GetRequest(URL,map,null,textView,null);
        OKHttpControls.getInstance(this).execute(this, getRequest,defaultCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OKHttpControls.getInstance(this).cancelAllRequest(textView);
        defaultCallback.cancelAllRunnable();
    }
}
