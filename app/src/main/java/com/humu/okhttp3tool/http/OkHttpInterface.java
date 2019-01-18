package com.humu.okhttp3tool.http;

import android.text.TextUtils;

import com.humu.myokhttp3.bean.MultiFile;
import com.humu.myokhttp3.http.OkHttpParams;
import com.humu.myokhttp3.http.OkHttpResponseCallback;
import com.humu.myokhttp3.http.OkHttpUtil;
import com.humu.okhttp3tool.model.BaseActModel;

import java.io.File;
import java.util.List;

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
        params.add("key",appKey);
        if(!TextUtils.isEmpty(type)){
            params.add("type",type);
        }
        OkHttpUtil.getInstance().request(params,callback);
    }

    //get请求测试
    public static void getTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        //设置为get请求
        params.setGetRequest();
        params.add("key",appKey);
        if(!TextUtils.isEmpty(type)){
            params.add("type",type);
        }
        OkHttpUtil.getInstance().request(params,callback);
    }


    //文件上传测试
    public static void postFileTest(String tag, File file, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        //TODO 接口路径自己设置
        params.setUrl("");
        params.putFile(tag,file);
        OkHttpUtil.getInstance().request(params,callback);
    }

    //文件列表上传测试
    public static void postFiles(List<MultiFile> files, OkHttpResponseCallback<BaseActModel> callback){
        OkHttpParams params = new OkHttpParams();
        //TODO 接口路径自己设置
        params.setUrl("");
        params.putMultiFiles(files);
        OkHttpUtil.getInstance().request(params,callback);
    }

}
