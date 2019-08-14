package com.example.androiddemo;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    EditText etName;
    EditText etEmail;
    private final String TEST = "test";
    private final String TEST2 = "test2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
    }

    /**
     * 视图点击处理函数
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Map<String, String> params = new HashMap<>();
        switch (v.getId()) {
            case R.id.btn_get:
                HttpManager.get(TEST, Constant.userFindAll, null, this, this);
                break;
            case R.id.btn_post:
                params.put("name", etName.getText().toString());
                params.put("email", etEmail.getText().toString());
                HttpManager.postJSON(TEST2, Constant.userSave, params, this, this);
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
        Looper.prepare();
        switch (tag) {
            case TEST:
                Toast.makeText(HTTPActivity.this, responseBody, Toast.LENGTH_LONG).show();
                break;
            case TEST2:
                Toast.makeText(HTTPActivity.this, responseBody, Toast.LENGTH_LONG).show();
                break;
        }
        Looper.loop();
    }
}
