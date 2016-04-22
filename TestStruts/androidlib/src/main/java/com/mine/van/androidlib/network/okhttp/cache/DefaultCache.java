package com.mine.van.androidlib.network.okhttp.cache;

import android.content.Context;
import android.os.Environment;

import com.mine.van.androidlib.util.FileUtils;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by fanjh on 2016/4/17.
 */
public class DefaultCache {
    //默认缓存大小10M
    public static final int DEFAULT_CACHE_SIZE = 1024 * 1024 * 10;
    //默认缓存路径，当前应用/network/cache
    private String cachePath;
    private Cache cache;

    public Cache getCache() {
        return cache;
    }

    public DefaultCache(Context context) {
        cachePath = getCachePath(context);
        if(FileUtils.isFileExist(cachePath)){
            File file = new File(cachePath);
            cache = new Cache(file, DEFAULT_CACHE_SIZE);
        }else {
            if (FileUtils.CreateFile(cachePath)) {
                File file = new File(cachePath);
                cache = new Cache(file, DEFAULT_CACHE_SIZE);
            }
        }
    }

    /**
     * 获取缓存路径，默认存放在SD卡
     * @return
     */
    private String getCachePath(Context context){
        String path = null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = context.getExternalCacheDir();
            if(null != file)
                path = file.getAbsolutePath();
            else
                path = context.getCacheDir().getAbsolutePath();
        }
        else
            path = context.getCacheDir().getAbsolutePath();
        return path + "/network";
    }

}
