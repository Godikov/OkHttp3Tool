package com.humu.myokhttp3.bean;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by Administrator on 2019/1/18.
 */

public class FileBody {

    private final File file;
    private final String fileName;
    private final String contentType;

    public FileBody(File file) {
        this(file, "", (String)null);
    }

    public FileBody(File file, String contentType) {
        this(file, contentType, (String)null);
    }

    public FileBody(File file, String contentType, String fileName) {
        this.file = file;
        if(TextUtils.isEmpty(contentType)) {
            this.contentType = "application/octet-stream";
        } else {
            this.contentType = contentType;
        }

        this.fileName = fileName;
    }

    public File getFile() {
        return this.file;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

}
