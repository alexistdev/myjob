package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.myjob.API.APIService;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.LoginModel;
import com.coder.myjob.utils.ErrorUtils;
import com.coder.myjob.utils.SessionHandle;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Login extends AppCompatActivity {
    private ImageView btnLogin;
    private EditText mEmail, mPassword;
    private ProgressDialog pDialog;
    private TextView btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        if (SessionHandle.isLoggedIn(this)) {
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if (Objects.equals(role, "2")) {
                Intent intent = new Intent(Login.this, DashboardFreelancer.class);
                startActivity(intent);
                finish();
            } else if (Objects.equals(role, "3")) {
                Intent intent = new Intent(Login.this, DashboardFreelancer.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
                finish();
            }

        }
        init();
        btnLogin.setOnClickListener(v -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            if (email.trim().length() > 0 && password.trim().length() > 0) {
                if (cekEmail(email)) {
                    checkLogin(email, password);
                } else {
                    pesan("Masukkan email yang valid !");
                }
            } else {
                pesan("Semua kolom harus diisi!");
            }
        });
        btnDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Daftar.class);
            startActivity(intent);
            finish();
        });


    }
    private void checkLogin(final String email, final String password) {
        tampilkanDialog();
        try {
            Call<LoginModel> call = APIService.Factory.create(getApplicationContext()).loginUser(email,password);
            call.enqueue(new Callback<LoginModel>() {
               @EverythingIsNonNull
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            if (SessionHandle.login(Login.this, response.body().getIdUser(), response.body().getToken(),response.body().getRole())){
                                if(response.body().getRole().equals("2")){
                                    Intent intent = new Intent(Login.this, DashboardFreelancer.class);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Intent intent = new Intent(Login.this, DashboardFreelancer.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    } else {
                        sembunyikanDialog();
                        APIError error = ErrorUtils.parseError(response);
                        pesan(error.message());
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
        btnLogin = findViewById(R.id.btnLogin);
        btnDaftar = findViewById(R.id.btn_daftar);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
    }

    private boolean cekEmail(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
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
