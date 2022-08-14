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

import com.coder.myjob.API.APIService;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.JobModel;
import com.coder.myjob.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Detailjobfreelancer extends AppCompatActivity {
    private Button mAplied,mBidder;
    private TextView mJudul,mDeadline,mFee,mDeskripsi,mNamaBidder;
    private ProgressDialog pDialog;

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
        Intent iin= getIntent();
        Bundle extra = iin.getExtras();
        if(extra != null) {
            String idJob = extra.getString("idJob","");
            String judul = extra.getString("judul","");
            String deadline = extra.getString("deadline","");
            String fee = extra.getString("fee","");
            String deskripsi = extra.getString("deskripsi","");
            String bidder = extra.getString("bidder","");
            mNamaBidder.setText(bidder);
            mJudul.setText(judul);
            mDeskripsi.setText(deskripsi);
            mDeadline.setText(getString(R.string.singlejob2,deadline));
            mFee.setText(getString(R.string.singlejob1,fee));
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                    Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            String idUser = sharedPreferences.getString("idUser", "");
            if(role.equals("3")){
                if(bidder.equals("-")){
                    mBidder.setVisibility(View.VISIBLE);
                }else{
                    mBidder.setVisibility(View.INVISIBLE);
                }
                mAplied.setVisibility(View.INVISIBLE);
            }else{
                cekJob(idJob,idUser);
            }
            mAplied.setOnClickListener(view -> appliedJob(idJob,idUser));
            mBidder.setOnClickListener(view -> {
                Intent intent = new Intent(Detailjobfreelancer.this, DaftarBidder.class);
                intent.putExtra("idjob", idJob);
                startActivity(intent);
                finish();
            });
        }
    }

    private void appliedJob(String idJob, String idUser){
        tampilkanDialog();
        try{
            Call<JobModel> call=APIService.Factory.create(getApplicationContext()).applyJob(idUser,idJob);
            call.enqueue(new Callback<JobModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<JobModel> call, Response<JobModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            pesan("Anda telah melakukan penawaran, silahkan ditunggu untuk di validasi oleh Pemilik Pekerjaan!");
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<JobModel> call, Throwable t) {
                    sembunyikanDialog();
                    pesan(t.getMessage());
                }
            });
        }catch (Exception e){
            sembunyikanDialog();
            e.printStackTrace();
            pesan(e.getMessage());
        }

    }

    private void cekJob(String idJob,String idUser){
        tampilkanDialog();
        try{
            Call<JobModel> call= APIService.Factory.create(getApplicationContext()).cekStatusJob(idUser,idJob);
            call.enqueue(new Callback<JobModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<JobModel> call, Response<JobModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            String statusJob = response.body().getStatusBid();
                            mBidder.setVisibility(View.INVISIBLE);
                            if(statusJob.equals("1")){
                                mAplied.setVisibility(View.INVISIBLE);
                                pesan("Anda sudah pernah melakukan penawaran!");
                            } else{
                                mAplied.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        APIError error = ErrorUtils.parseError(response);
                        pesan(error.message());
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<JobModel> call, Throwable t) {
                    sembunyikanDialog();
                    pesan(t.getMessage());
                }
            });
        }catch (Exception e){
            sembunyikanDialog();
            e.printStackTrace();
            pesan(e.getMessage());
        }

    }

    private void dataInit(){
        mAplied = findViewById(R.id.btn_applied);
        mJudul = findViewById(R.id.text_judul);
        mDeadline = findViewById(R.id.txt_deadline);
        mFee = findViewById(R.id.txt_fee);
        mDeskripsi = findViewById(R.id.txt_deskripsi);
        mBidder = findViewById(R.id.btn_bidder);
        mNamaBidder = findViewById(R.id.text_bidder);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
    }

    private void tampilkanDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void sembunyikanDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public void pesan(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}