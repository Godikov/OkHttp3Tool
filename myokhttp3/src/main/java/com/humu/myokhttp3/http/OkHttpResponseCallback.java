package com.humu.myokhttp3.http;

import android.os.Handler;
import android.os.Looper;

import com.humu.myokhttp3.utils.JsonUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 接口请求结果回调
 * Created by humu on 2018/9/26.
 */

public abstract class OkHttpResponseCallback<T> implements Callback {

    private Handler handler = new Handler(Looper.getMainLooper());

    public Class<T> clazz;

    public Type getType(Class<?> clazz, int index) {
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
    public void onFailure(final Call call, IOException e) {
        if(this.isMainLooper()){
            //此处可以做接口请求失败的公共处理，如失败提醒等。
            failure(call);
        }else{
            this.handler.post(new Runnable() {
                @Override
                public void run() {
                    //此处可以做接口请求失败的公共处理，如失败提醒等。
                    failure(call);
                }
            });
        }
        finish();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String bodyStr = response.body().string();
        Type type = this.getType(this.getClass(), 0);
        if(type instanceof Class) {
            this.clazz = (Class)type;
        }
        if(JsonUtil.isJson(bodyStr)){
            final T model = parseActModel(bodyStr,this.clazz);
            if(this.isMainLooper()){
                //此处可以做接口请求成功的公共处理，如进度条消失等。
                //bodyStr:json字符串，方便打印
                //model：json转对象之后的对象，方便对数据操作
                onSuccess(bodyStr,model);
                finish();
            }else{
                this.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //此处可以做接口请求成功的公共处理，如进度条消失等。
                        OkHttpResponseCallback.this.onSuccess(bodyStr,model);
                        finish();
                    }
                });
            }
        }
    }

    /**
     * 将json格式数据转换为对象
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T parseActModel(String result, Class<T> clazz) {
        return JsonUtil.json2Object(result, clazz);
    }

    private boolean isMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public abstract void onSuccess(String bodyStr,T actModel);

    public void failure(Call call){
        //接口失败
    }

    public void finish(){
        //自定义接口调完之后的逻辑,无论是失败还是成功都会最终执行。
    }

}