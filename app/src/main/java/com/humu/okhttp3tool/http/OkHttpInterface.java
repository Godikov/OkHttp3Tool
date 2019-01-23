package com.humu.okhttp3tool.http;

import com.humu.myokhttp3.bean.MultiFile;
import com.humu.myokhttp3.http.AppRequestParams;
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
        AppRequestParams.getInstance()
                .url(TEST_URL)
                .add("key",appKey)
                .add("type",type)
                .request(callback);
    }

    //get请求测试
    public static void getTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){
        AppRequestParams.getInstance()
                .url(TEST_URL)
                .setGetRequest()
                .add("key",appKey)
                .add("type",type)
                .request(callback);
    }


    //文件上传测试
    public static void postFileTest(String tag, File file, OkHttpResponseCallback<BaseActModel> callback){
        AppRequestParams.getInstance().url("https://mss.tchcn.com/peccancy/getPeccancyByFile")
                .putFile(tag,file)
                .request(callback);
/*        OkHttpParams params = new OkHttpParams();
        //TODO 接口路径自己设置
        params.setUrl("https://mss.tchcn.com/peccancy/getPeccancyByFile");
        params.putFile(tag,file);
        OkHttpUtil.getInstance().request(params,callback);*/
    }

    //文件列表上传测试
    public static void postFiles(List<MultiFile> files, OkHttpResponseCallback<BaseActModel> callback){
        AppRequestParams.getInstance().url("https://mss.tchcn.com/face/searchUserByFaceImages")
                .putMultiFiles(files)
                .request(callback);
/*        OkHttpParams params = new OkHttpParams();
        //TODO 接口路径自己设置
        params.setUrl("https://mss.tchcn.com/face/searchUserByFaceImages");
        params.putMultiFiles(files);
        OkHttpUtil.getInstance().request(params,callback);*/
    }

}
