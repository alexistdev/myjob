package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coder.myjob.config.Constants;
import com.coder.myjob.utils.SessionHandle;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Splash2Activity extends AppCompatActivity {
    private Button mFreelancer,mJobOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        if(SessionHandle.isLoggedIn(this)){
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if(Objects.equals(role, "2")){
                Intent intent = new Intent(Splash2Activity.this, DashboardFreelancer.class);
                startActivity(intent);
                finish();
            } else if(Objects.equals(role, "3")){
                Intent intent = new Intent(Splash2Activity.this, DashboardSeeker.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Splash2Activity.this, Login.class);
                startActivity(intent);
                finish();
            }

        }
        init();
        mFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash2Activity.this, Login.class);
                intent.putExtra("nama","Freelancer");
                startActivity(intent);
                finish();
            }
        });
        mJobOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash2Activity.this, Login.class);
                intent.putExtra("nama","Job Owner");
                startActivity(intent);
            }
        });

    }

    public void init() {
        mFreelancer = findViewById(R.id.btn_login1);
        mJobOwner = findViewById(R.id.btn_login2);

    }
}