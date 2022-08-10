package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.coder.myjob.API.APIService;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.JobModel;
import com.coder.myjob.model.KategoriModel;
import com.coder.myjob.response.ResponseKategori;
import com.coder.myjob.utils.ErrorUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TambahJob extends AppCompatActivity {
    private Spinner mSpinKategori;
    private ProgressDialog pDialog;
    private TextView mKategori;
    private Button mTambah;
    private EditText mJudul,mDeskripsi,mFee,mTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_job);
        init();
        populateSpinner();
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Tambah Job");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        mSpinKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kategoriTerpilih = parent.getItemAtPosition(position).toString();
                mKategori.setText(kategoriTerpilih);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mKategori.setText("");
            }
        });
        mTambah.setOnClickListener(view -> {

            String kategori = mKategori.getText().toString();
            String judul = mJudul.getText().toString();
            String deskripsi = mDeskripsi.getText().toString();
            String fee = mFee.getText().toString();
            String deadline = mTanggal.getText().toString();
            if(kategori.trim().length() > 0 && judul.trim().length() > 0 && deskripsi.trim().length() > 0
                    && fee.trim().length() > 0 && deadline.trim().length() > 0){
                simpanData(kategori,judul,deskripsi,fee,deadline);
            } else {
                pesan("Semua kolom harus diisi!");
            }
        });
    }
    private void simpanData(String kategori, String judul, String deskripsi, String fee, String deadline){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        tampilkanDialog();
        try{
            Call<JobModel> call=APIService.Factory.create(getApplicationContext()).tambahJob(idUser,kategori,judul,deskripsi,fee,deadline);
            call.enqueue(new Callback<JobModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<JobModel> call, Response<JobModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            Intent intent = new Intent(TambahJob.this, DashboardSeeker.class);
                            pesan("Job berhasil ditambahkan!");
                            startActivity(intent);
                            finish();
                        }
                    }else{
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

    private void populateSpinner() {
            Call<ResponseKategori> call= APIService.Factory.create(getApplicationContext()).spinnerKategori();
            call.enqueue(new Callback<ResponseKategori>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                    if(response.body() != null){
                        List<KategoriModel> kategoriModels = response.body().getItemKategori();
                        List<String> listSpinner = new ArrayList<>();
                        for (int i = 0; i < kategoriModels.size(); i++){
                            listSpinner.add(kategoriModels.get(i).getNamaKategori());
                            /* Memasukkan data ke dalam spinner */
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinKategori.setAdapter(adapter);
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<ResponseKategori> call, Throwable t) {
                    pesan(t.getMessage());
                }
            });

    }

    public void init() {
        mSpinKategori = findViewById(R.id.spinnerKategori);
        mKategori = findViewById(R.id.txt_kategori);
        mTambah = findViewById(R.id.btn_tambah);
        mJudul = findViewById(R.id.edJudul);
        mDeskripsi = findViewById(R.id.ed_deskripsi);
        mFee = findViewById(R.id.ed_fee);
        mTanggal = findViewById(R.id.ed_deadline);
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