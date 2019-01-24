package com.humu.myokhttp3.http;

import com.humu.myokhttp3.bean.MultiFile;

import java.io.File;
import java.util.List;

/** 链式请求方式
 * Created by humu on 2019/1/22.
 */

public class AppRequestParams {

    private static AppRequestParams mInstance;
    private OkHttpParams okHttpParams;

    private AppRequestParams(){
        okHttpParams = new OkHttpParams();
    }

    public static AppRequestParams getInstance() {
        mInstance = new AppRequestParams();
        return mInstance;
    }

    public AppRequestParams url(String url){
        okHttpParams.setUrl(url);
        return mInstance;
    }

    /**
     * 设置为post请求
     */
    public AppRequestParams setPostRequest(){
        okHttpParams.setPostRequest();
        return mInstance;
    }

    /**
     * 设置为get请求
     */
    public AppRequestParams setGetRequest(){
        okHttpParams.setGetRequest();
        return mInstance;
    }

    /**
     * 设置参数
     * @param tag
     * @param value
     */
    public AppRequestParams add(String tag, String value){
        okHttpParams.add(tag,value);
        return mInstance;
    }

    /**
     * post请求添加文件
     * */
    public AppRequestParams putFile(String tag, File file){
        okHttpParams.putFile(tag,file);
        return mInstance;
    }

    /**
     * post上传文件列表
     * @param multiFiles
     */
    public AppRequestParams putMultiFiles(List<MultiFile> multiFiles){
        okHttpParams.putMultiFiles(multiFiles);
        return mInstance;
    }

    /**
     * 发起请求
     * @param callback 回调
     */
    public final void request(OkHttpResponseCallback callback){
        OkHttpUtil.getInstance().request(okHttpParams,callback);
    }

}
