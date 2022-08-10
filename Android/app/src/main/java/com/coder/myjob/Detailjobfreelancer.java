package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.myjob.config.Constants;
import com.coder.myjob.model.JobModel;

public class Detailjobfreelancer extends AppCompatActivity {
    private Button mAplied,mBidder;
    private TextView mJudul,mDeadline,mFee,mDeskripsi;
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
        Intent iin= getIntent();
        Bundle extra = iin.getExtras();
        if(extra != null) {
            String idJob = extra.getString("idJob","");
            String judul = extra.getString("judul","");
            String deadline = extra.getString("deadline","");
            String fee = extra.getString("fee","");
            String deskripsi = extra.getString("deskripsi","");
            mJudul.setText(judul);
            mDeskripsi.setText(deskripsi);
            mDeadline.setText(getString(R.string.singlejob2,deadline));
            mFee.setText(getString(R.string.singlejob1,fee));
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                    Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if(role.equals("3")){
                mBidder.setVisibility(View.VISIBLE);
                mAplied.setVisibility(View.INVISIBLE);
            }else{
                mBidder.setVisibility(View.INVISIBLE);
                mAplied.setVisibility(View.VISIBLE);
            }
        }
    }

    private void dataInit(){
        mAplied = findViewById(R.id.btn_applied);
        mJudul = findViewById(R.id.text_judul);
        mDeadline = findViewById(R.id.txt_deadline);
        mFee = findViewById(R.id.txt_fee);
        mDeskripsi = findViewById(R.id.txt_deskripsi);
        mBidder = findViewById(R.id.btn_bidder);
    }

    public void pesan(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}