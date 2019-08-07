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
/*
        OKHttp3Tool.getInstance()
                .url(TEST_URL)
                .add("key",appKey)
                .add("type",type)
                .send(callback);
*/
        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        params.add("key",appKey);
        params.add("type",type);
        OkHttpUtil.getInstance().request(params,callback);
    }

/*    public static void postTest2(String appKey,String type,OkHttpResponseCallback2<CommonActModel> callback2){
        OkHttp3Tool2.getInstance().url(TEST_URL).post().add("key",appKey).add("type",type).send(callback2);
    }*/

    //get请求测试
    public static void getTest(String appKey, String type, OkHttpResponseCallback<BaseActModel> callback){

        OKHttp3Tool.getInstance().url(TEST_URL).get().add("key",appKey).add("type",type).send(callback);

/*        OkHttpParams params = new OkHttpParams();
        params.setUrl(TEST_URL);
        params.setGetRequest();
        params.add("key",appKey);
        params.add("type",type);
        OkHttpUtil.getInstance().request(params,callback);*/
    }

/*    public static void getTest2(String appKey, String type, OkHttpResponseCallback2<CommonActModel> listener){
        OkHttp3Tool2.getInstance().url(TEST_URL).get().add("key",appKey).add("type",type).send(listener);
    }*/


    //文件上传测试
    public static void postFileTest(String tag, File file, OkHttpResponseCallback<BaseActModel> callback){

        OKHttp3Tool.getInstance()
                .url("https://mss.tchcn.com/peccancy/getPeccancyByFile")
                .putFile(tag,file)
                .send(callback);

/*        OkHttpParams params = new OkHttpParams();
        //TODO 接口路径自己设置
        params.setUrl("https://mss.tchcn.com/peccancy/getPeccancyByFile");
        params.putFile(tag,file);
        OkHttpUtil.getInstance().request(params,callback);*/
    }

/*    public static void postFile(MultiFile multiFile,OkHttpResponseCallback2<BaseActModel> callback2){
        OkHttp3Tool2.getInstance()
                .url("https://api.tchcn.com:31013/face/searchUserByFaceImages")
                .putFile(multiFile)
                .send(callback2);
    }

    public static void postFiles(List<MultiFile> multiFiles,OkHttpResponseCallback2<BaseActModel> callback2){
        OkHttp3Tool2.getInstance()
                .url("https://api.tchcn.com:31013/face/searchUserByFaceImages")
                .putFiles(multiFiles)
                .send(callback2);
    }*/

    //文件列表上传测试
    public static void postFiles(List<MultiFile> files, OkHttpResponseCallback<BaseActModel> callback){
        OKHttp3Tool.getInstance()
                .url("https://api.tchcn.com:31013/face/searchUserByFaceImages")
                .putFiles(files)
                .send(callback);
/*        OkHttpParams params = new OkHttpParams();
        //TODO 接口路径自己设置
        params.setUrl("https://mss.tchcn.com/face/searchUserByFaceImages");
        params.putMultiFiles(files);
        OkHttpUtil.getInstance().request(params,callback);*/
    }

}
