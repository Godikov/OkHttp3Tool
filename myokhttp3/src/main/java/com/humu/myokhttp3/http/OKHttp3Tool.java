package com.humu.myokhttp3.http;

import android.text.TextUtils;

import com.humu.myokhttp3.bean.MultiFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  可实现链式请求，代码更简洁。
 *  参数设置无顺序要求。
 * Created by Administrator on 2019/3/6.
 */

public class OKHttp3Tool {

    private static OkHttpParams params;
    private boolean isPostRequest = true;
    private Map<String,String> mapParams = new HashMap<>();
    private Map<String,File> fileMap = new HashMap<>();
    private List<MultiFile> multiFiles = new ArrayList<>();

    private String url = "";

    /**
     * 创建实例
     * @return
     */
    public static OKHttp3Tool getInstance(){
        params = new OkHttpParams();
        return new OKHttp3Tool();
    }

    /**
     * 设置请求地址
     * @param url
     * @return
     */
    public OKHttp3Tool url(String url){
        this.url = url;
        return this;
    }

    /**
     * 设置为get请求
     * @return
     */
    public OKHttp3Tool get(){
        isPostRequest = false;
        return this;
    }

    /**
     * 设置为post请求
     * @return
     */
    public OKHttp3Tool post(){
        isPostRequest = true;
        return this;
    }

    /**
     * 添加参数
     * @param key
     * @param value
     * @return
     */
    public OKHttp3Tool add(String key, String value){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
            mapParams.put(key,value);
        }
        return this;
    }

    /**
     * 添加file文件
     * @param key
     * @param file
     * @return
     */
    public OKHttp3Tool putFile(String key, File file){
        if(!TextUtils.isEmpty(key)){
            if(file != null && file.exists()){
                fileMap.put(key,file);
            }
        }
        return this;
    }

    /**
     * 添加多个文件
     * @param files
     * @return
     */
    public OKHttp3Tool putFiles(List<MultiFile> files){
        if(files != null && files.size() > 0){
            multiFiles = files;
        }
        return this;
    }

    /**
     * 发送请求
     * @param callback 请求结果回调
     */
    public void send(OkHttpResponseCallback callback){
        if(!TextUtils.isEmpty(url)){
            params.setUrl(url);
        }
        if(isPostRequest){
            params.setPostRequest();
        }else{
            params.setGetRequest();
        }
        if(mapParams.keySet().size() > 0){
            Set<String> keys = mapParams.keySet();
            for(String key: keys){
                params.add(key,mapParams.get(key));
            }
        }
        if(fileMap.keySet().size() > 0){
            Set<String> keys = fileMap.keySet();
            for(String key: keys){
                params.putFile(key,fileMap.get(key));
            }
        }
        if(multiFiles != null && multiFiles.size() > 0){
            params.putMultiFiles(multiFiles);
        }
        OkHttpUtil.getInstance().request(params,callback);
    }

}
