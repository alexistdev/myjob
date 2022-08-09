package com.coder.myjob.fragment1;

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


public class Homefrelancer extends Fragment {
    private RecyclerView gridJob;
    private JobAdapter jobAdapter;
    private List<JobModel> daftarJob;
    private ProgressDialog pDialog;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_homefrelancer, container, false);
        mContext = getContext();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String idUser = sharedPreferences.getString("idUser", "");
        dataInit(mview);
        setupRecyclerView();
        setData(mContext,idUser,token);
        return mview;

    }

    public void setData(Context mContext,String idUser, String token) {
        try {
            tampilkanDialog();
            Call<GetJob> call= APIService.Factory.create(mContext).dapatfreelancerjob(idUser,token);
            call.enqueue(new Callback<GetJob>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetJob> call, Response<GetJob> response) {
                    sembunyikanDialog();
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarJob = response.body().getListJob();
                            jobAdapter.replaceData(daftarJob);
                        }
                    }
//                    }else{
//                        APIError error = ErrorUtils.parseError(response);
//                        pesan(error.message());
//                    }
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

    private void dataInit(View mview){
        gridJob = mview.findViewById(R.id.rcJob);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading.....");

        //mSwipeRefreshLayout = mview.findViewById(R.id.refresh);
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