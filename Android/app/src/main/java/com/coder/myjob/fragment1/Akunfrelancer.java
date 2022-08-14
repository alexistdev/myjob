package com.coder.myjob.fragment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coder.myjob.API.APIService;
import com.coder.myjob.API.NoConnectivityException;
import com.coder.myjob.Login;
import com.coder.myjob.R;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.AkunModel;
import com.coder.myjob.model.JobModel;
import com.coder.myjob.utils.ErrorUtils;
import com.coder.myjob.utils.SessionHandle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class Akunfrelancer extends Fragment {
    private Button mLogout;
    private EditText mNama,mEmail,mPhone,mPassword;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_akunfrelancer, container, false);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        dataInit(mview);
        setData(idUser);
        mLogout.setOnClickListener(view -> {
            SessionHandle.logout(requireContext());
            Intent intent = new Intent(getActivity(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if(getActivity()!= null){
                getActivity().finish();
            }
        });
        return mview;
    }

    private void setData(String idUser){
        tampilkanDialog();
        try{
            Call<AkunModel> call= APIService.Factory.create(getContext()).getDataAkun(idUser);
            call.enqueue(new Callback<AkunModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<AkunModel> call, Response<AkunModel> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            mNama.setText(response.body().getNamaPengguna());
                            mEmail.setText(response.body().getEmailPengguna());
                            mPhone.setText(response.body().getPhonePengguna());
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<AkunModel> call, Throwable t) {
                    sembunyikanDialog();
                    if(t instanceof NoConnectivityException) {
                        pesan("Offline, cek koneksi internet anda!");
                    }
                }
            });
        } catch (Exception e){
            sembunyikanDialog();
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }


    private void dataInit(View mview){
        mLogout = mview.findViewById(R.id.btn_logout);
        mNama = mview.findViewById(R.id.edx_nama);
        mEmail = mview.findViewById(R.id.edx_email);
        mPhone = mview.findViewById(R.id.edx_phone);
        pDialog = new ProgressDialog(getContext());
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}