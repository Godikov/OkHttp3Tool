package com.humu.okhttp3tool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.humu.myokhttp3.model.BaseActModel;
import com.humu.myokhttp3.http.OkHttpInterface;
import com.humu.myokhttp3.http.OkHttpResponseCallback;

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
                Log.d(TAG,bodyStr);
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
}
