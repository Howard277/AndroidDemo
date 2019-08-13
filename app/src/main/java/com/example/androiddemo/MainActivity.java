package com.example.androiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartupH5;
    Button btnStartupHttp;

    /**
     * 视图创建
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartupH5 = findViewById(R.id.btn_startup_h5);
        btnStartupH5.setOnClickListener(this);

        btnStartupHttp = findViewById(R.id.btn_startup_http);
        btnStartupHttp.setOnClickListener(this);
    }

    /**
     * 视图点击，响应方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startup_h5:
                // url，指定web view 加载的页面。
                Intent intent = new Intent(MainActivity.this, H5Activity.class);
                intent.putExtra("url", "http://www.jikexueyuan.com/");
                startActivity(intent);
                break;
            case R.id.btn_startup_http:
                startActivity(new Intent(MainActivity.this, HTTPActivity.class));
                break;
        }
    }
}
