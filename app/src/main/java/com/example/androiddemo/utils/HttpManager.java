package com.example.androiddemo.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * HTTP请求管理器
 */
public class HttpManager {

    private static Gson gson = new Gson();
    private static OkHttpClient client = new OkHttpClient();

    /**
     * 数据检查
     *
     * @param tag
     * @param url
     * @return
     */
    private static boolean dataCheck(String tag, String url) {
        if (StringUtils.isBlank(tag)) {
            Log.w("SYS", "tag是空");
            return false;
        }
        if (StringUtils.isBlank(url)) {
            Log.w("SYS", "url是空");
            return false;
        }
        return true;
    }


    /**
     * 将请求放入队列
     */
    private static void requestEnqueue(final String tag, final HttpResponseHandler handler, final Context context, Request request) {
        client.newCall(request).enqueue(new Callback() {
            // 请求失败处理函数
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(tag, String.format("网络请求异常：%s", gson.toJson(e)));
                // 异常处理时，如果上下文不为空，就显示提示信息
                if (null != context) {
                    Looper.prepare();
                    Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG);
                    Looper.loop();
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
                        Looper.prepare();
                        Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG);
                        Looper.loop();
                    }
                }
            }
        });
    }

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
            // 数据检查不通过，直接返回
            if (!dataCheck(tag, url)) {
                return;
            }
            // 记录网络请求数据
            Log.i(tag, String.format("请求地址：%s \n请求参数：%s", url, gson.toJson(params)));
            //构造Request对象
            if ((null != params) && (params.size() > 0)) {
                //将参数拼接在url之后
                String strParams = "";
                for (String key : params.keySet()) {
                    strParams += String.format("&%s=%s", key, params.get(key));
                }
                url += "?" + strParams.substring(1);
            }
            //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
            Request request = new Request.Builder().get().url(url).build();
            requestEnqueue(tag, handler, context, request);
        } catch (Exception ex) {
            Log.e(tag, String.format("网络请求异常：%s", gson.toJson(ex)));
        }
    }

    /**
     * 已Form表单提交的方式发起POST请求
     *
     * @param tag     用来区别每一个请求；并会作为日志中的tag
     * @param url     请求地址
     * @param params  请求参数
     * @param handler 返回值处理函数
     * @param context 上下文对象。当网络异常时用来显示提示信息。如果是后台请求或不需要显示提示文字，可以为null
     */
    public static void postForm(final String tag, String url, Map<String, String> params, final HttpResponseHandler handler, final Context context) {
        try {
            // 数据检查不通过，直接返回
            if (!dataCheck(tag, url)) {
                return;
            }
            // 记录网络请求数据
            Log.i(tag, String.format("请求地址：%s \n请求参数：%s", url, gson.toJson(params)));
            //构建FormBody，传入要提交的参数
            FormBody.Builder builder = new FormBody.Builder();
            if ((null != params) && (params.size() > 0)) {
                for (String key : params.keySet()) {
                    builder.add(key, params.get(key));
                }
            }
            FormBody formBody = builder.build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            requestEnqueue(tag, handler, context, request);
        } catch (Exception ex) {
            Log.e(tag, String.format("网络请求异常：%s", gson.toJson(ex)));
        }
    }

    /**
     * 已JSON提交的方式发起POST请求
     *
     * @param tag     用来区别每一个请求；并会作为日志中的tag
     * @param url     请求地址
     * @param params  请求参数
     * @param handler 返回值处理函数
     * @param context 上下文对象。当网络异常时用来显示提示信息。如果是后台请求或不需要显示提示文字，可以为null
     */
    public static void postJSON(final String tag, String url, Map<String, String> params, final HttpResponseHandler handler, final Context context) {
        try {
            // 数据检查不通过，直接返回
            if (!dataCheck(tag, url)) {
                return;
            }
            // 记录网络请求数据
            Log.i(tag, String.format("请求地址：%s \n请求参数：%s", url, gson.toJson(params)));
            //构建
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                    gson.toJson(params));
            Request request = new Request.Builder().url(url).post(requestBody).build();
            requestEnqueue(tag, handler, context, request);
        } catch (Exception ex) {
            Log.e(tag, String.format("网络请求异常：%s", gson.toJson(ex)));
        }
    }
}
