package com.humu.myokhttp3.http;

import android.net.Uri;
import android.text.TextUtils;

import com.humu.myokhttp3.bean.FileBody;
import com.humu.myokhttp3.bean.MultiFile;

import java.io.File;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 请求参数设置工具
 * Created by humu on 2018/9/27.
 */
public class OkHttpParams {

    private final FormBody.Builder builder;
    private final MultipartBody.Builder multiBuilder;
    private String url = "";
    private Uri.Builder uriBuilder;

    private final String POST = "post";
    private final String GET = "get";

    private final String FORM = "form"; //普通参数请求
    private final String MULTI = "multi"; //包含文件的请求

    private String requestType = POST; //默认是post请求

    private String paramType = FORM; //默认只是普通请求

    public OkHttpParams(){
        builder = new FormBody.Builder();
        multiBuilder = new MultipartBody.Builder();
        multiBuilder.setType(MultipartBody.FORM);
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
     * 设置为post请求
     */
    public void setPostRequest(){
        requestType = POST;
    }

    /**
     * 设置为get请求
     */
    public void setGetRequest(){
        requestType = GET;
    }

    /**
     * 判断是否为post请求
     * @return
     */
    public boolean isPostRequest(){
        return POST.equals(requestType);
    }

    /**
     * 设置参数
     * @param tag
     * @param value
     */
    public void add(String tag,String value){
        if(POST.equals(requestType)){
            put(tag,value);
        }else if(GET.equals(requestType)){
            addParam(tag,value);
        }
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
    public void putFile(String tag, File file){
        String TYPE = "application/octet-stream";
        RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE),file);
                multiBuilder.addFormDataPart(tag,file.getName(),fileBody);
        if(!MULTI.equals(paramType)){
            paramType = MULTI;
        }
    }

    /**
     * post上传文件列表
     * @param multiFiles
     */
    public void putMultiFiles(List<MultiFile> multiFiles){
        if(multiFiles != null && multiFiles.size() > 0){
            for(MultiFile multiFile: multiFiles){
                FileBody fileBody = multiFile.getFileBody();
                putFile(multiFile.getKey(),fileBody.getFile());
            }
        }
    }

    /**
     * get请求添加参数
     * */
    private void addParam(String tag,String value){
        uriBuilder.appendQueryParameter(tag, value);
        multiBuilder.addFormDataPart(tag,value);
    }

    public String getUriBuilderUrl(){
        return uriBuilder.toString();
    }

}
