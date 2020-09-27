package com.leo.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.newrelic.agent.android.NewRelic;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by spf on 2018/11/5.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        NewRelic.withApplicationToken(
                "AA934b16a73bb4ed5b267fec78c7852539ed134f25"
        ).start(this.getApplication());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, ProductActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
