package com.humu.okhttp3tool.okhttp3;

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

    public final void post(OkHttpParams params,OkHttpResponseCallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params.getUrl())
                    .post(params.getRequestBody())
                    .build();
            okHttpClient.newCall(request).enqueue(callback);
    }

    public final void get(OkHttpParams params,OkHttpResponseCallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(params.getUriBuilderUrl())
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
