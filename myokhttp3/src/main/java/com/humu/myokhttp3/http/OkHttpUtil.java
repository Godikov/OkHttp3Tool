package com.humu.myokhttp3.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by humu on 2018/9/27.
 */

public class OkHttpUtil {

    private static OkHttpUtil mInstance;

    public static OkHttpUtil getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发起请求
     * @param params 请求参数
     * @param callback 回调
     */
    public final void request(OkHttpParams params,OkHttpResponseCallback callback){
        if(params != null){
            if(params.isPostRequest()){
                //post请求
                post(params,callback);
            }else{
                get(params,callback);
            }
        }
    }

    private final void post(OkHttpParams params,OkHttpResponseCallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params.getUrl())
                    .post(params.getRequestBody())
                    .build();
            okHttpClient.newCall(request).enqueue(callback);
    }

    private final void get(OkHttpParams params,OkHttpResponseCallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(params.getUriBuilderUrl())
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
