package com.coder.myjob.fragment2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.coder.myjob.API.APIService;
import com.coder.myjob.API.NoConnectivityException;
import com.coder.myjob.R;
import com.coder.myjob.adapter.JobAdapter;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.APIError;
import com.coder.myjob.model.JobModel;
import com.coder.myjob.response.GetJob;
import com.coder.myjob.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class Homeseeker extends Fragment {
    private RecyclerView gridJob;
    private ProgressDialog pDialog;
    private Context mContext;
    private JobAdapter jobAdapter;
    private List<JobModel> daftarJob;
    private ImageView mTambah;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_homeseeker, container, false);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String idUser = sharedPreferences.getString("idUser", "");
        dataInit(mview);
        setupRecyclerView();
        mTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesan('okay');
            }
        });
//        pesan("id User ="+ idUser + " token: "+ token);
        setData(mContext,idUser,token);
        return mview;
    }

    public void setData(Context mContext,String idUser, String token) {
        try {
            tampilkanDialog();
            Call<GetJob> call= APIService.Factory.create(mContext).dapatseekerjob(idUser,token);
            call.enqueue(new Callback<GetJob>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetJob> call, Response<GetJob> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            daftarJob = response.body().getListJob();
                            jobAdapter.replaceData(daftarJob);
                        }
                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GetJob> call, Throwable t) {
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

    private void dataInit(View mview){
        gridJob = mview.findViewById(R.id.rcJob);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");
        mTambah = mview.findViewById(R.id.btn_tambah);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        jobAdapter = new JobAdapter(new ArrayList<>());
        gridJob.setLayoutManager(linearLayoutManager);
        gridJob.setAdapter(jobAdapter);
    }

    public void pesan(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}