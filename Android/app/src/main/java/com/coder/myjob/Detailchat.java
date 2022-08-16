package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.coder.myjob.API.APIService;
import com.coder.myjob.adapter.BalasAdapter;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.BalasModel;
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
    private String idJob;

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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        Intent iin= getIntent();
        Bundle extra = iin.getExtras();
        if(extra != null) {
            idJob = extra.getString("idjob","");
            setData(idJob);
        }
        mBalas.setOnClickListener(v -> {
            String mKirim = mPesan.getText().toString();
            if (mKirim.trim().length() > 0 && idJob.length() > 0){
                kirimPesan(idJob,mKirim,idUser);
            } else {
                pesan("Semua kolom harus diisi!");
            }
        });
    }

    public void kirimPesan(String idJob, String xMsg,String userId){
        tampilkanDialog();
        try{
            Call<BalasModel> call=APIService.Factory.create(getApplicationContext()).balasPesan(userId,idJob,xMsg);
            call.enqueue(new Callback<BalasModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<BalasModel> call, Response<BalasModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            pesan("Data berhasil disimpan!");
                            setData(idJob);
                            mPesan.setText("");
                        }

                    }else{
                        APIError error = ErrorUtils.parseError(response);
                        pesan(error.message());
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<BalasModel> call, Throwable t) {
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
        idJob = "";
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