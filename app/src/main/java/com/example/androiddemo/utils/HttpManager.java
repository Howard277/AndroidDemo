package com.example.androiddemo.utils;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTP请求管理器
 */
public class HttpManager {

    public static Response get(String url, Map<String, Object> params) {
        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception ex) {
            Log.e("{}", ex.toString());
        }
        return response;
    }

    public static void getSync(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url(url).build();
        try {
            client.newCall(request).enqueue(callback);
        } catch (Exception ex) {
            Log.e("{}", ex.toString());
        }
    }
}
