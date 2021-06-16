package com.example.hhuclassschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动界面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置此界面为
        // 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText("HHU课程表");
        //利用timer让此界面延迟1秒后跳转，timer有一个线程，该线程不断执行task
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //跳转后关闭当前欢迎页面
                SplashActivity.this.finish();
             }
         };
        //调度执行timerTask，第二个参数传入延迟时间（毫秒）
        timer.schedule(timerTask, 1000);
    }
}