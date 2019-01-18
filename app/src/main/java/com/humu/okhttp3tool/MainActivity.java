package com.humu.okhttp3tool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.humu.myokhttp3.bean.FileBody;
import com.humu.myokhttp3.bean.MultiFile;
import com.humu.myokhttp3.model.BaseActModel;
import com.humu.myokhttp3.http.OkHttpResponseCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void postTest(View view) {
        OkHttpInterface.postTest("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                //接口回调成功
                //bodyStr: 返回的Json格式数据
                //actModel: json转换成的对象
                Log.d(TAG,bodyStr);
            }

            @Override
            public void finish() {
                super.finish();
                //失败回调
            }
        });
    }

    public void getTest(View view) {
        OkHttpInterface.getTest("c40cd8f68b25f2930319130e567e1a16", "", new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                Log.d(TAG,bodyStr);
            }
        });

    }

    public void postFileTest(View view) {
        OkHttpInterface.postFileTest("frontFile", new File("/storage/emulated/0/Downloads/timg.jpg"), new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                Log.d(TAG,bodyStr);
            }
        });
    }

    public void postFilesTest(View view) {
        List<MultiFile> multiFiles = new ArrayList<>();

        MultiFile multiFile = new MultiFile();
        multiFile.setKey("face_images");
        FileBody fileBody = new FileBody(new File("/storage/emulated/0/Downloads/timg.jpg"));
        multiFile.setFileBody(fileBody);

        MultiFile multiFile2 = new MultiFile();
        multiFile2.setKey("face_images");
        FileBody fileBody2 = new FileBody(new File("/storage/emulated/0/Downloads/timg.jpg"));
        multiFile2.setFileBody(fileBody2);

        multiFiles.add(multiFile);
        multiFiles.add(multiFile2);

        OkHttpInterface.postFiles(multiFiles, new OkHttpResponseCallback<BaseActModel>() {
            @Override
            public void onSuccess(String bodyStr, BaseActModel actModel) {
                Log.d(TAG,bodyStr);
            }
        });

    }
}