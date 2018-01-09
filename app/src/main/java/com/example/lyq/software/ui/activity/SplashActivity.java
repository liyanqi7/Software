package com.example.lyq.software.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lyq.software.R;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.utils.SpUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, Constants.FIRST_OPEN);
        if (isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHomeActivity();
            }
        },2000);
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

