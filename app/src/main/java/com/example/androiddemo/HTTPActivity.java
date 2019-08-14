package com.example.androiddemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androiddemo.utils.Constant;
import com.example.androiddemo.utils.HttpManager;
import com.example.androiddemo.utils.HttpResponseHandler;
import com.example.androiddemo.vo.UserVO;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HTTPActivity extends AppCompatActivity implements View.OnClickListener, HttpResponseHandler {

    Button btnGet;
    Button btnPost;
    Gson gson = new Gson();
    private final String TEST = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
    }

    /**
     * 视图点击处理函数
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                Map<String,String> params = new HashMap<>();
                params.put("name","吴克涛");
                HttpManager.get(TEST, Constant.userFindAll, params, HTTPActivity.this, this);
                break;
            case R.id.btn_post:
                break;
        }
    }

    /**
     * 处理HTTP请求
     *
     * @param tag          请求标志，用来区分不同请求
     * @param responseBody 请求处理结果内容
     */
    @Override
    public void DealwithHttpResponse(String tag, String responseBody) {
        switch (tag) {
            case TEST:
                break;
        }
    }
}
