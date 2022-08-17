package com.coder.myjob.fragment2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.coder.myjob.API.APIService;
import com.coder.myjob.API.NoConnectivityException;
import com.coder.myjob.Daftar;
import com.coder.myjob.Login;
import com.coder.myjob.R;
import com.coder.myjob.TambahJob;
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
    private static final String TAG = "PESAN";
    private Context mContext;
    private JobAdapter jobAdapter;
    private List<JobModel> daftarJob;
    private ImageView mTambah;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
                Intent intent = new Intent(getActivity(), TambahJob.class);
                startActivity(intent);
            }
        });
        setData(mContext,idUser,token);
        return mview;
    }

    public void setData(Context mContext,String idUser, String token) {
        try {
            Call<GetJob> call= APIService.Factory.create(mContext).dapatseekerjob(idUser,token);
            call.enqueue(new Callback<GetJob>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetJob> call, Response<GetJob> response) {

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
                    Log.i(TAG,t.getMessage());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }

    private void dataInit(View mview){
        gridJob = mview.findViewById(R.id.rcJob);
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