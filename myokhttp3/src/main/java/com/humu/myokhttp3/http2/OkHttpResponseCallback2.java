package com.humu.myokhttp3.http2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.humu.myokhttp3.utils.JsonUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 接口请求结果回调
 * Created by humu on 2018/9/26.
 */

public abstract class OkHttpResponseCallback2<T> implements Callback {

    private static final int SUCCESS = 0;
    private static final int FORMAT_ERROR = 1;

    private Class<T> clazz;

    protected T actModel;
    protected String bodyStr;

    private Type getType(Class<?> clazz, int index) {
        Type type = null;
        Type[] types = this.getType(clazz);
        if(types != null && index >= 0 && types.length > index) {
            type = types[index];
        }
        return type;
    }

    private Type[] getType(Class<?> clazz) {
        Type[] types = null;
        if(clazz != null) {
            Type type = clazz.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType)type;
            types = parameterizedType.getActualTypeArguments();
        }
        return types;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        if(e instanceof SocketTimeoutException){
            //连接超时异常
            onTimeOut();
        }
        if(e instanceof ConnectException){
            //连接异常
            onConnectFail();
        }
        if(e instanceof FileNotFoundException){
            //上传的文件不存在
            onFileNotFound();
        }
        onFinish();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        bodyStr = response.body().string();
        if(JsonUtil.isJson(bodyStr)){
            Type type = this.getType(this.getClass(), 0);
            if(type instanceof Class) {
                this.clazz = (Class)type;
            }
            actModel = JSON.parseObject(bodyStr,this.clazz);
            if(this.isMainLooper()){
                //此处可以做接口请求成功的公共处理，如进度条消失等。
                //bodyStr:json字符串，方便打印
                //model：json转对象之后的对象，方便对数据操作
                onSuccess();
                onFinish();
            }else{
                handler.sendEmptyMessage(SUCCESS);
            }
        }else{
            //非json格式数据
            //回调格式错误方法
            if(this.isMainLooper()){
                onFormatError();
                onFinish();
            }else{
                handler.sendEmptyMessage(FORMAT_ERROR);
            }
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    onSuccess();
                    onFinish();
                    break;
                case FORMAT_ERROR:
                    onFormatError();
                    onFinish();
                    break;
            }
        }
    };

    private boolean isMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    protected abstract void onSuccess();

    protected void onFinish(){
        //自定义接口调完之后的逻辑,无论是失败还是成功都会最终执行。
    }

    protected void onTimeOut(){
        //连接超时
    }

    protected void onConnectFail(){
        //连接异常
    }

    protected void onFormatError(){
        //返回数据格式错误(非json格式数据)
    }

    protected void onFileNotFound(){
        //上传的文件不存在
    }

}
