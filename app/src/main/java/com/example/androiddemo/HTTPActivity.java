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
import com.example.androiddemo.vo.UserVO;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HTTPActivity extends AppCompatActivity implements View.OnClickListener, Callback {

    Button btnGet;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                HttpManager.getSync(Constant.userFindAll, this);
                break;
            case R.id.btn_post:
                break;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            String strBody = response.body().string();
            Gson gson = new Gson();
            List<UserVO> vo = gson.fromJson(strBody, List.class);
            Log.i("test", gson.toJson(vo));
        }
    }
}
