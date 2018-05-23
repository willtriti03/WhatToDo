package com.example.jungjune.whattodo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.jungjune.whattodo.R;
import com.example.jungjune.whattodo.Service.BackgroundService;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();    // 액티비티 종료
            }
        };
        handler.sendEmptyMessageDelayed(0, 1500);
    }
}
