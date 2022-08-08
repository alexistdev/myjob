package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Detailjobfreelancer extends AppCompatActivity {
    private Button mAplied;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailjobfreelancer);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Detail Job");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        dataInit();
        mAplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesan("Anda berhasil untuk melakukan bid pada pekerjaan ini, silakan tunggu notifikasi approval");
            }
        });
    }

    private void dataInit(){
        mAplied = findViewById(R.id.btn_applied);

    }
    public void pesan(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}