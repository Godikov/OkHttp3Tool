package com.humu.okhttp3tool.okhttp3;

import android.text.TextUtils;

import com.humu.okhttp3tool.model.BaseActModel;

/**
 * Created by humu on 2018/9/26.
 */

public class OkHttpInterface {

    private static final String TAG = "OkHttpInterface";

    //接口测试路径，为聚合数据新闻头条免费路径。
    private static final String TEST_URL = "http://v.juhe.cn/toutiao/index";

    //post请求测试
    public static void postTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        params.put("key",appKey);
        if(!TextUtils.isEmpty(type)){
            params.put("type",type);
        }
        OkHttpUtil.getInstance().post(params,callback);
    }

    //get请求测试
    public static void getTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        params.addParam("key",appKey);
        if(!TextUtils.isEmpty(type)){
            params.addParam("type",type);
        }
        OkHttpUtil.getInstance().get(params,callback);
    }

}
