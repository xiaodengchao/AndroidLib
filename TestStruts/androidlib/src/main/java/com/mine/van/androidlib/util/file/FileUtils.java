package com.mine.van.androidlib.util.file;

import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fanjh on 2016/4/24.
 */
public class FileUtils {
    public static final long GB = 1073741824; // 1024 * 1024 * 1024
    public static final long MB = 1048576; // 1024 * 1024
    public static final long KB = 1024;

    /**
     * 判断文件是否存在，推荐输入都为文件路径
     * @param filePath 文件路径
     * @return true，文件存在，否则不存在
     */
    public static final boolean isFileExist(String filePath){
        if(null == filePath)
            return false;
        File file = new File(filePath);
        return (file.isFile() && file.exists());
    }

    /**
     * 判断目录是否存在
     * @param directoryPath 目录路径
     * @return true，目录存在，否则不存在
     */
    public static final boolean isDirectoryExist(String directoryPath){
        if(null == directoryPath)
            return false;
        File file = new File(directoryPath);
        return (file.isDirectory() && file.exists());
    }

    /**
     * 返回文件权限
     * @param filePath
     * @return 4代表可读,2代表可写，1代表可执行，可能为其中的某种组合，结果为各种组合之和
     */
    public static final int getFileMod(String filePath) throws NullPointerException{
        if(null == filePath)
            throw new NullPointerException("文件路径是空指针！");
        File file = new File(filePath);
        return (file.canRead() ? 4:0) + (file.canWrite() ? 2:0) + (file.canExecute() ? 1:0);
    }

    /**
     * 获得当前目录下的所有文件（目录不算）
     * @param directoryPath
     * @return
     * @throws FileNotFoundException
     * @throws NullPointerException
     * @throws Exception
     */
    public static final List<File> getFileList(String directoryPath) throws FileNotFoundException,NullPointerException,Exception{
        if(null == directoryPath)
            throw new NullPointerException("目录路径是空指针！");
        List<File> fileList = new ArrayList<>();
        File file = new File(directoryPath);
        if(!file.exists())
            throw new FileNotFoundException("该文件或目录不存在！");
        if(file.isFile()) {
            fileList.add(file);
        }else if(file.isDirectory()){
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return false;
                }
            });
            for(File tempFile:files){
                if(tempFile.isFile())
                    fileList.add(tempFile);
            }
        }else
            throw new Exception("类型异常！");
        return fileList;
    }

    public static final List<File> getAllFileList(String directoryPath) throws NullPointerException,FileNotFoundException{
        if(null == directoryPath)
            throw new NullPointerException("目录路径为空！");
        List<File> files = new ArrayList<>();
        File file = new File(directoryPath);
        if(!file.exists())
            throw new FileNotFoundException("目录不存在！");
        if(file.isFile()) {
            files.add(file);
            return files;
        }
        if(file.isDirectory()){
           fn(files,file);
        }
        return files;
    }

    /**
     * 递归获取当前目录下所有文件
     * @param fileList 用于接收所有文件的列表
     * @param file 目录
     */
    private static void fn(List<File> fileList,File file){
        if(null == file || null == fileList)
            return ;
        if(file.isFile()) {
            fileList.add(file);
            return ;
        }
        if(file.isDirectory()){
            File []files = file.listFiles();
            for(int i=0;i<files.length;i++){
                fn(fileList, files[i]);
            }
        }
    }

    /**
     * 创建新文件
     * @param filePath 文件路径
     * @return 是否创建成功
     */
    public static final boolean createFile(String filePath){
        if(null == filePath)
            return false;
        File file = new File(filePath);
        if (file.exists()) {
            return false;
        }
        //以/结尾
        if (filePath.endsWith(File.separator)) {
            return false;
        }
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        // 创建目标文件
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件或文件夹，会将文件夹中的所有文件都删除，包括子文件夹
     * @param filePath 文件路径
     * @return 删除是否成功
     */
    public static final boolean deleteFile(String filePath){
        if(null == filePath)
            return false;
        File file = new File(filePath);
        if(!file.exists())
            return false;
        if(file.isFile())
            return file.delete();
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File tempFile : files){
                if(!deleteFile(tempFile.getAbsolutePath()))
                    return false;
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 文件大小获取
     *
     * @param file File对象
     * @return 文件大小字符串
     */
    public static String getFileSize(File file) throws NullPointerException{
        if(null == file)
            throw new NullPointerException("文件为空！");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int length = fis.available();
            if (length >= GB) {
                return String.format("%.2f GB", length * 1.0 / GB);
            } else if (length >= MB) {
                return String.format("%.2f MB", length * 1.0 / MB);
            } else {
                return String.format("%.2f KB", length * 1.0 / KB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "未知";
    }

    /**
     * 把uri转为File对象
     *
     * @param activity Activity
     * @param uri      文件Uri
     * @return File对象
     */
    public static File uri2File(Activity activity, Uri uri) {
        if (Build.VERSION.SDK_INT < 11) {
            // 在API11以下可以使用：managedQuery
            String[] proj = {MediaStore.Images.Media.DATA};
            @SuppressWarnings("deprecation")
            Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null,
                    null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            return new File(img_path);
        } else {
            // 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(activity, uri, projection, null,
                    null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return new File(cursor.getString(column_index));
        }
    }

}
