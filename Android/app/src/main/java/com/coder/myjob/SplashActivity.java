package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.coder.myjob.config.Constants;
import com.coder.myjob.utils.SessionHandle;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(SessionHandle.isLoggedIn(this)){
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if(Objects.equals(role, "2")){
                Intent intent = new Intent(SplashActivity.this, DashboardFreelancer.class);
                startActivity(intent);
                finish();
            } else if(Objects.equals(role, "3")){
                Intent intent = new Intent(SplashActivity.this, DashboardSeeker.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();
            }

        }

        int timeout = 3000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent homepage = new Intent(SplashActivity.this, Splash2Activity.class);
                startActivity(homepage);
            }
        }, timeout);
    }
}