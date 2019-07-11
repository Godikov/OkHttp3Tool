package com.humu.myokhttp3.http2;

import android.net.Uri;
import android.text.TextUtils;

import com.humu.myokhttp3.bean.MultiFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2019/3/26.
 */

public class OkHttp3Tool2 {

    private static volatile OkHttp3Tool2 instance;
    private static final String TYPE = "application/octet-stream";
    private String url; //请求地址
    private Map<String,String> params = new HashMap<>(); //请求参数集合
    private static OkHttpClient okHttpClient;
    private boolean postReq = true; //默认是post请求
    private static ExecutorService executorService;
    private List<MultiFile> fileList = new ArrayList<>();

    private OkHttp3Tool2(){
        executorService = Executors.newCachedThreadPool();
    }

    private OkHttpClient getOkHttpClient(){
        if(okHttpClient == null){
            initOkHttpClient();
        }
        return okHttpClient;
    }

    /**
     * 初始化OkHttpClient
     */
    private void initOkHttpClient(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
    }

    /**
     * 获取实例
     * @return
     */
    public static OkHttp3Tool2 getInstance(){
        if(instance == null){
            synchronized (OkHttp3Tool2.class){
                if(instance == null){
                    instance = new OkHttp3Tool2();
                }
            }
        }
        return instance;
    }

    /**
     * 设置请求地址
     * @param url
     */
    public OkHttp3Tool2 url(String url){
        this.url = url;
        return this;
    }

    /**
     * 添加参数
     * @param key 参数key
     * @param value 参数value
     */
    public OkHttp3Tool2 add(String key,String value){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
            params.put(key,value);
        }
        return this;
    }

    /**
     * 添加文件
     * @param multiFile
     * @return
     */
    public OkHttp3Tool2 putFile(MultiFile multiFile){
        fileList.add(multiFile);
        return this;
    }

    public OkHttp3Tool2 putFiles(List<MultiFile> multiFiles){
        if(multiFiles != null && multiFiles.size() > 0){
            fileList.addAll(multiFiles);
        }
        return this;
    }

    /**
     * 设置为get请求
     * @return
     */
    public OkHttp3Tool2 get(){
        postReq = false;
        return this;
    }

    /**
     * 设置为post请求
     * @return
     */
    public OkHttp3Tool2 post(){
        postReq = true;
        return this;
    }

    /**
     * 发送get请求
     * @param url
     * @param callback2
     */
    private void getReq(String url, OkHttpResponseCallback2 callback2){
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(callback2);
        postReq = true;
    }

    /**
     * 发送post请求
     * @param requestBody
     * @param callback2
     */
    private void postReq(RequestBody requestBody,OkHttpResponseCallback2 callback2) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        getOkHttpClient().newCall(request).enqueue(callback2);
        postReq = true;
    }

    /**
     * 发送请求
     * @param callback2
     */
    public void send(final OkHttpResponseCallback2 callback2){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if(postReq){
                    if(fileList.size() == 0){
                        //无文件上传post请求
                        FormBody.Builder builder = new FormBody.Builder();
                        if(params.keySet().size() > 0){
                            Set<String> keys = params.keySet();
                            for(String key: keys){
                                builder.add(key,params.get(key));
                            }
                            params.clear();
                        }
                        postReq(builder.build(),callback2);
                    }else if(fileList.size() >= 1){
                        //文件上传post请求
                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        if(params.keySet().size() > 0){
                            Set<String> keys = params.keySet();
                            for(String key: keys){
                                builder.addFormDataPart(key,params.get(key));
                            }
                            params.clear();
                        }
                        for(MultiFile multiFile: fileList){
                            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE),multiFile.getFileBody().getFile());
                            builder.addFormDataPart(multiFile.getKey(),multiFile.getFileBody().getFile().getName(),
                                    fileBody);
                        }
                        RequestBody requestBody = builder
                                .setType(MultipartBody.FORM)
                                .build();
                        postReq(requestBody,callback2);
                        fileList.clear();
                    }
                }else{
                    //get请求
                    Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
                    if(params.keySet().size() > 0){
                        Set<String> keys = params.keySet();
                        for(String key: keys){
                            uriBuilder.appendQueryParameter(key,params.get(key));
                        }
                        params.clear();
                    }
                    getReq(uriBuilder.toString(),callback2);
                }
            }
        });
    }
}
