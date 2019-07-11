package com.humu.okhttp3tool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.humu.myokhttp3.bean.FileBody;
import com.humu.myokhttp3.bean.MultiFile;
import com.humu.myokhttp3.http.OkHttpResponseCallback;
import com.humu.myokhttp3.http2.OkHttpResponseCallback2;
import com.humu.okhttp3tool.http.OkHttpInterface;
import com.humu.okhttp3tool.model.BaseActModel;
import com.humu.okhttp3tool.model.CommonActModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void postTest(View view) {

        OkHttpInterface.postTest2("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback2<CommonActModel>() {
            @Override
            protected void onSuccess() {
                Log.d(TAG,actModel.getReason());
            }
        });

/*
        OkHttpInterface.postTest("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                //接口回调成功
                //bodyStr: 返回的Json格式数据
                //actModel: json转换成的对象
                Log.d(TAG,bodyStr);
            }

            @Override
            public void onTimeOut() {
                super.onTimeOut();
                Log.d(TAG,"onTimeOut");
                //连接超时
            }

            @Override
            public void onConnectFail() {
                super.onConnectFail();
                Log.d(TAG,"onConnectFail");
                //连接失败
            }

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Log.d(TAG,"onFailure");
                //接口请求失败
            }

            @Override
            public void onFormatError() {
                super.onFormatError();
                //接口返回数据格式错误（非json格式数据时回调）
                Log.d(TAG,"onFormatError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d(TAG,"onFinish");
                //接口请求结束回调
            }
        });
*/
    }

    public void getTest(View view) {
/*        OkHttpInterface.getTest("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                Log.d(TAG,bodyStr);
            }
        });*/

        OkHttpInterface.getTest2("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback2<CommonActModel>() {
            @Override
            protected void onSuccess() {
                Log.d(TAG,actModel.getReason());
            }
        });

    }

    public void postFileTest(View view) {
/*
        OkHttpInterface.postFileTest("frontFile", new File("/storage/emulated/0/Huawei/Themes/HWWallpapers/800003362.jpg"), new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                Log.d(TAG,bodyStr);
            }
        });
*/

        MultiFile multiFile = new MultiFile();
        multiFile.setKey("face_images");
        FileBody fileBody = new FileBody(new File("/storage/emulated/0/Huawei/Themes/HWWallpapers/800003362.jpg"));
        multiFile.setFileBody(fileBody);

        List<MultiFile> multiFiles = new ArrayList<>();
        multiFiles.add(multiFile);
        multiFiles.add(multiFile);
        OkHttpInterface.postFiles(multiFiles, new OkHttpResponseCallback2<BaseActModel>() {
            @Override
            protected void onSuccess() {
                Log.d("postFile",bodyStr);
            }

            @Override
            protected void onFileNotFound() {
                super.onFileNotFound();
                Log.d("postFile","文件不存在");
            }
        });

    }

    public void postFilesTest(View view) {
        List<MultiFile> multiFiles = new ArrayList<>();

        MultiFile multiFile = new MultiFile();
        multiFile.setKey("face_images");
        FileBody fileBody = new FileBody(new File("/storage/emulated/0/Huawei/Themes/HWWallpapers/800005091.jpg"));
        multiFile.setFileBody(fileBody);

        MultiFile multiFile2 = new MultiFile();
        multiFile2.setKey("face_images");
        FileBody fileBody2 = new FileBody(new File("/storage/emulated/0/Huawei/Themes/HWWallpapers/800005080.jpg"));
        multiFile2.setFileBody(fileBody2);

        multiFiles.add(multiFile);
        multiFiles.add(multiFile2);

        OkHttpInterface.postFiles(multiFiles, new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                Log.d(TAG,bodyStr);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Log.d(TAG,"======onFailure====="+e.getMessage());
            }

            @Override
            public void onTimeOut() {
                super.onTimeOut();
                Log.d(TAG,"=====onTimeOut====");
            }

            @Override
            public void onConnectFail() {
                super.onConnectFail();
                Log.d(TAG,"======connectFail======");
            }
        });

    }
}
