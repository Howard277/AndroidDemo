package com.example.androiddemo.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androiddemo.vo.UserVO;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
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

    private static Gson gson = new Gson();
    private static OkHttpClient client = new OkHttpClient();

    /**
     * 异步GET请求
     *
     * @param tag     用来区别每一个请求；并会作为日志中的tag
     * @param url     请求地址
     * @param params  请求参数
     * @param handler 返回值处理函数
     * @param context 上下文对象。当网络异常时用来显示提示信息。如果是后台请求或不需要显示提示文字，可以为null
     */
    public static void get(final String tag, String url, Map<String, String> params, final HttpResponseHandler handler, final Context context) {
        try {
            if (StringUtils.isBlank(tag)) {
                Log.w("SYS", "tag是空");
                return;
            }
            if (StringUtils.isBlank(url)) {
                Log.w("SYS", "url是空");
                return;
            }
            //构造Request对象
            if ((null != params) && (params.size() > 0)) {
                //将参数拼接在url之后
                String strParams = "";
                for (String key : params.keySet()) {
                    strParams += String.format("&%s=%s", key, params.get(key));
                }
                url += "?" + strParams.substring(1);
            }
            // 记录网络请求数据
            Log.i(tag, String.format("请求地址：%s \n请求参数：%s", url, gson.toJson(params)));
            //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
            Request request = new Request.Builder().get().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                // 请求失败处理函数
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(tag, String.format("网络请求异常：%s", gson.toJson(e)));
                    // 异常处理时，如果上下文不为空，就显示提示信息
                    if (null != context) {
                        Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG);
                    }
                }

                // 请求成功处理函数
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String strBody = response.body().string();
                        Log.i(tag, String.format("请求结果：%s", strBody));
                        // 调用外部处理函数
                        handler.DealwithHttpResponse(tag, strBody);
                    } else {
                        Log.e(tag, String.format("网络请求异常：%s", gson.toJson(response)));
                        // 异常处理时，如果上下文不为空，就显示提示信息
                        if (null != context) {
                            Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG);
                        }
                    }
                }
            });
        } catch (Exception ex) {
            Log.e(tag, String.format("网络请求异常：%s", gson.toJson(ex)));
        }
    }
}
