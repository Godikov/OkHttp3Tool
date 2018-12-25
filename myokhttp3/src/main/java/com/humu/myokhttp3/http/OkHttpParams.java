package com.humu.myokhttp3.http;

import android.net.Uri;
import android.text.TextUtils;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 请求参数设置工具
 * Created by humu on 2018/9/27.
 */

public class OkHttpParams {

    private final FormBody.Builder builder;
    private String url = "";
    private Uri.Builder uriBuilder;

    public OkHttpParams(){
        builder = new FormBody.Builder();
    }

    /**
     * 设置请求路径
     */
    public void setUrl(String url){
        this.url = url;
        uriBuilder = Uri.parse(url).buildUpon();
    }

    public String getUrl(){
        return url;
    }

    public RequestBody getRequestBody(){
        if(builder != null){
            return builder.build();
        }
        return null;
    }

    /**
     * post请求添加参数
     * */
    public void put(String tag, String value){
        if(builder != null){
            if(!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(value)){
                builder.add(tag,value);
            }
        }
    }

    /**
     * get请求添加参数
     * */
    public void addParam(String tag,String value){
        uriBuilder.appendQueryParameter(tag, value);
    }

    public String getUriBuilderUrl(){
        return uriBuilder.toString();
    }

}
