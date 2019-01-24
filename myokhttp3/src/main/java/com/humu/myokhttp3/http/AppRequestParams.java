package com.humu.myokhttp3.http;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.humu.myokhttp3.bean.FileBody;
import com.humu.myokhttp3.bean.MultiFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** 链式请求方式
 * Created by humu on 2019/1/22.
 */

public class AppRequestParams {

    private static AppRequestParams mInstance;
    private String mUrl;
    private Uri.Builder uriBuilder;
    private static FormBody.Builder builder = null;
    private static MultipartBody.Builder multiBuilder = null;

    private final String POST = "post";
    private final String GET = "get";

    private final String FORM = "form"; //普通参数请求
    private final String MULTI = "multi"; //包含文件的请求

    private String requestType = POST; //默认是post请求

    private String paramType = FORM; //默认只是普通请求

    private AppRequestParams(){
        builder = new FormBody.Builder();
        multiBuilder = new MultipartBody.Builder();
        multiBuilder.setType(MultipartBody.FORM);
    }

    public static AppRequestParams getInstance() {
        if (mInstance == null) {
            synchronized (AppRequestParams.class) {
                if (mInstance == null) {
                    mInstance = new AppRequestParams();
                }
            }
        }
        builder = new FormBody.Builder();
        multiBuilder = new MultipartBody.Builder();
        multiBuilder.setType(MultipartBody.FORM);
        return mInstance;
    }

    public AppRequestParams url(String url){
        mUrl = url;
        uriBuilder = Uri.parse(url).buildUpon();
        return mInstance;
    }

    /**
     * 设置为post请求
     */
    public AppRequestParams setPostRequest(){
        requestType = POST;
        return mInstance;
    }

    /**
     * 设置为get请求
     */
    public AppRequestParams setGetRequest(){
        requestType = GET;
        return mInstance;
    }

    /**
     * 设置参数
     * @param tag
     * @param value
     */
    public AppRequestParams add(String tag, String value){
        if(POST.equals(requestType)){
            put(tag,value);
        }else if(GET.equals(requestType)){
            addParam(tag,value);
        }
        return mInstance;
    }

    /**
     * post请求添加参数
     * */
    private void put(String tag, String value){
        if(builder != null){
            if(!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(value)){
                builder.add(tag,value);
            }
        }
        if(multiBuilder != null){
            if(!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(value)){
                multiBuilder.addFormDataPart(tag,value);
            }
        }
    }

    /**
     * post请求添加文件
     * */
    public AppRequestParams putFile(String tag, File file){
        String TYPE = "application/octet-stream";
        multiBuilder.addFormDataPart(tag,file.getName(),RequestBody.create(MediaType.parse(TYPE),file));
        if(!MULTI.equals(paramType)){
            paramType = MULTI;
        }
        return mInstance;
    }

    /**
     * post上传文件列表
     * @param multiFiles
     */
    public AppRequestParams putMultiFiles(List<MultiFile> multiFiles){
        if(multiFiles != null && multiFiles.size() > 0){
            for(MultiFile multiFile: multiFiles){
                FileBody fileBody = multiFile.getFileBody();
                putFile(multiFile.getKey(),fileBody.getFile());
            }
        }
        return mInstance;
    }

    /**
     * get请求添加参数
     * */
    private void addParam(String tag,String value){
        uriBuilder.appendQueryParameter(tag, value);
        multiBuilder.addFormDataPart(tag,value);
    }

    private RequestBody getRequestBody(){
        if(FORM.equals(paramType)){
            if(builder != null){
                return builder.build();
            }
        }else if(MULTI.equals(paramType)){
            if(multiBuilder != null){
                return multiBuilder.build();
            }
        }
        return null;
    }

    /**
     * 发起请求
     * @param callback 回调
     */
    public final void request(OkHttpResponseCallback callback){
        if(POST.equals(requestType)){
            //post请求
            post(callback);
        }else{
            get(callback);
        }
    }

    private final void post(OkHttpResponseCallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        if(!TextUtils.isEmpty(mUrl)){
            Request request = new Request.Builder()
                    .url(mUrl)
                    .post(getRequestBody())
                    .build();
            okHttpClient.newCall(request).enqueue(callback);
        }else{
            //请求地址为空
        }
    }

    private final void get(OkHttpResponseCallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        if(!TextUtils.isEmpty(uriBuilder.toString())){
            Request request = new Request.Builder()
                    .url(uriBuilder.toString())
                    .get()
                    .build();
            okHttpClient.newCall(request).enqueue(callback);
        }else{
            //请求地址为空
        }
    }

}
