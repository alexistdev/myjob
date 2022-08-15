package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coder.myjob.API.APIService;
import com.coder.myjob.adapter.BalasAdapter;
import com.coder.myjob.adapter.BidderAdapter;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.BalasModel;
import com.coder.myjob.model.ResponseBalas;
import com.coder.myjob.response.GetBalas;
import com.coder.myjob.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Detailchat extends AppCompatActivity {
    private RecyclerView gridBalas;
    private BalasAdapter balasAdapter;
    private List<BalasModel> daftarbalas;
    private ProgressDialog pDialog;
    private Button mBalas;
    private EditText mPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailchat);
        dataInit();
        setupRecyclerView();
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Chat");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        Intent iin= getIntent();
        Bundle extra = iin.getExtras();
        if(extra != null) {
            String idJob = extra.getString("idjob","");
            pesan(idJob);
            setData(idJob);
        }

    }

    public void setData(String idJob)
    {
        tampilkanDialog();
        try{
            Call<GetBalas> call= APIService.Factory.create(getApplicationContext()).getDetailBalas(idJob);
            call.enqueue(new Callback<GetBalas>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetBalas> call, Response<GetBalas> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarbalas = response.body().getDaftarBalas();
                            balasAdapter.replaceData(daftarbalas);
                            mBalas.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String pesan = mPesan.getText().toString();
                                    if (pesan.trim().length() > 0){
                                        pesan("Semua kolom harus diisi!");
                                    } else {
                                        //todo simpan data
                                        
                                    }
                                }
                            });
                        }
                    }else{
                        APIError error = ErrorUtils.parseError(response);
                        pesan(error.message());
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GetBalas> call, Throwable t) {
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

        gridBalas = findViewById(R.id.rcChat);
        mBalas = findViewById(R.id.btn_balas);
        mPesan = findViewById(R.id.edx_pesan);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        balasAdapter = new BalasAdapter(new ArrayList<>());
        gridBalas.setLayoutManager(linearLayoutManager);
        gridBalas.setAdapter(balasAdapter);
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