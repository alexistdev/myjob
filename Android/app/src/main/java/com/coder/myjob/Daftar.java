package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.coder.myjob.API.APIService;
import com.coder.myjob.model.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Daftar extends AppCompatActivity {
    private ProgressDialog pDialog;
    private TextView mLogin;
    private ImageView mDaftar;
    private EditText mEmail,mNama,mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        init();
        Spinner mySpinner = (Spinner) findViewById(R.id.tipe);

        mLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Daftar.this, Login.class);
            startActivity(intent);
            finish();
        });
        mDaftar.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String nama = mNama.getText().toString();
            String password = mPassword.getText().toString();
            String tipe = mySpinner.getSelectedItem().toString();
            if (email.trim().length() > 0 && password.trim().length() > 0 && nama.trim().length() > 0 && tipe.trim().length() > 0) {

                if(tipe.equals("Job Seeker")){
                    simpanData(email,nama,password,"2");
                } else {
                    simpanData(email,nama,password,"3");
                }
            } else{
                pesan("Semua kolom harus diisi!");
            }
        });

    }

    private void simpanData(String email, String password, String nama, String tipe) {
        try{
            tampilkanDialog();
            Call<LoginModel> call = APIService.Factory.create(getApplicationContext()).daftarUser(email,password,nama,tipe);
            call.enqueue(new Callback<LoginModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            pesan("sudah mendaftar");
                            Intent intent = new Intent(Daftar.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
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

    public void init() {
        mLogin = findViewById(R.id.txt_login);
        mDaftar = findViewById(R.id.btn_daftar);
        mEmail = findViewById(R.id.txt_email);
        mNama = findViewById(R.id.txt_nama);
        mPassword = findViewById(R.id.txt_password);
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