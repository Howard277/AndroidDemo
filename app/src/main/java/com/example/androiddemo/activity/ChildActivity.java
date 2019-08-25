package com.example.androiddemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androiddemo.R;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
    }
}
