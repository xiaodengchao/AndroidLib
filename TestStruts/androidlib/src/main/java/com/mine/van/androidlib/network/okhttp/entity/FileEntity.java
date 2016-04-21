package com.mine.van.androidlib.network.okhttp.entity;

import java.io.File;
import java.io.Serializable;

/**
 * Created by fanjh on 2016/4/19.
 */
public class FileEntity implements Serializable{
    private static final long serialVersionUID = -3369175802017100786L;
    private File file;
    private String saveFileName;
    private String formFileName;

    public FileEntity(String saveFileName, String formFileName, File file) {
        this.saveFileName = saveFileName;
        this.formFileName = formFileName;
        this.file = file;
    }

    public String getSaveFileName() {
        if(saveFileName == null)
            return "";
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFormFileName() {
        if(formFileName == null)
            return "";
        return formFileName;
    }

    public void setFormFileName(String formFileName) {
        this.formFileName = formFileName;
    }
}
