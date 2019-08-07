package com.humu.okhttp3tool.http;

import com.humu.myokhttp3.bean.MultiFile;
import com.humu.myokhttp3.http.OKHttp3Tool;
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
        params.add("type",type);
        OkHttpUtil.getInstance().request(params,callback);
    }

    //get请求测试
    public static void getTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        OKHttp3Tool.getInstance().url(TEST_URL).get().add("key",appKey).add("type",type).send(callback);
    }


    //文件上传测试
    public static void postFileTest(String tag, File file, OkHttpResponseCallback<BaseActModel> callback){
        OKHttp3Tool.getInstance()
                .url("/peccancy/getPeccancyByFile")
                .putFile(tag,file)
                .send(callback);
    }

    //文件列表上传测试
    public static void postFiles(List<MultiFile> files, OkHttpResponseCallback<BaseActModel> callback){
        OKHttp3Tool.getInstance()
                .url("/face/searchUserByFaceImages")
                .putFiles(files)
                .send(callback);
    }

}
