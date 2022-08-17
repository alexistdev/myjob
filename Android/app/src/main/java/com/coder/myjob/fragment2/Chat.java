package com.coder.myjob.fragment2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coder.myjob.API.APIService;
import com.coder.myjob.API.NoConnectivityException;
import com.coder.myjob.R;
import com.coder.myjob.adapter.ChatAdapter;
import com.coder.myjob.adapter.JobAdapter;
import com.coder.myjob.config.Constants;
import com.coder.myjob.model.ChatModel;
import com.coder.myjob.model.JobModel;
import com.coder.myjob.response.GetChat;
import com.coder.myjob.response.GetJob;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class Chat extends Fragment {
    private RecyclerView gridChat;
    private List<ChatModel> daftarChat;
    private ChatAdapter chatAdapter;
    private Context mContext;

    private JobAdapter jobAdapter;
    private List<JobModel> daftarJob;
    private static final String TAG = "PESAN";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_chat, container, false);
        dataInit(mview);
        setupRecyclerView();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                Constants.KEY_USER_SESSION, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        setData(mContext,idUser);
        return mview;
    }



    public void setData(Context mContext,String idUser) {
        try {
            Call<GetChat> call= APIService.Factory.create(mContext).dataChatSeeker(idUser);
            call.enqueue(new Callback<GetChat>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetChat> call, Response<GetChat> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            daftarChat = response.body().getDaftarChat();
                            chatAdapter.replaceData(daftarChat);
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GetChat> call, Throwable t) {
                    Log.i(TAG,t.getMessage());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }

    private void dataInit(View mview) {
        gridChat = mview.findViewById(R.id.rcSeekerChat);

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        chatAdapter = new ChatAdapter(new ArrayList<>());
        gridChat.setLayoutManager(linearLayoutManager);
        gridChat.setAdapter(chatAdapter);
    }

    public void pesan(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}