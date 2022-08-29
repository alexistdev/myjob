package com.coder.myjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.coder.myjob.API.APIService;
import com.coder.myjob.API.NoConnectivityException;
import com.coder.myjob.adapter.BidderAdapter;
import com.coder.myjob.model.AkunModel;
import com.coder.myjob.model.BidderModel;
import com.coder.myjob.response.GetBidder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DaftarBidder extends AppCompatActivity implements BidderAdapter.ClickListener {
    private RecyclerView gridBidder;
    private BidderAdapter bidderAdapter;
    private List<BidderModel> daftarBidder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bidder);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Daftar Proposal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        dataInit();
        setupRecyclerView();
        Intent iin= getIntent();
        Bundle extra = iin.getExtras();
        if(extra != null) {
            String idJob = extra.getString("idjob","");
            setData(idJob);
        }

    }

    public void setData(String idJob) {
        try {
            Call<GetBidder> call = APIService.Factory.create(getApplicationContext()).dataBidder(idJob);
            call.enqueue(new Callback<GetBidder>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetBidder> call, Response<GetBidder> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            daftarBidder = response.body().getListBidder();
                            bidderAdapter.replaceData(daftarBidder);
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GetBidder> call, Throwable t) {
                    if(t instanceof NoConnectivityException) {
                        pesan("Offline, cek koneksi internet anda!");
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            pesan(e.getMessage());
        }
    }

    private void approval(String idBidder){
        try{

            Call<AkunModel> call=APIService.Factory.create(getApplicationContext()).approveBidder(idBidder);
            call.enqueue(new Callback<AkunModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<AkunModel> call, Response<AkunModel> response) {
                    if(response.isSuccessful()) {
                        pesan("Anda berhasil memilih pekerja untuk project Anda");
                        Intent intent = new Intent(DaftarBidder.this, DashboardSeeker.class);
                        startActivity(intent);
                        finish();
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<AkunModel> call, Throwable t) {
                    pesan(t.getMessage());
                }
            });
        }catch (Exception e){

            e.printStackTrace();
            pesan(e.getMessage());
        }
    }


    private void dataInit(){
        gridBidder = findViewById(R.id.rcJob);
    }


    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        bidderAdapter = new BidderAdapter(new ArrayList<>(),this);
        gridBidder.setLayoutManager(linearLayoutManager);
        gridBidder.setAdapter(bidderAdapter);
    }


    public void pesan(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }



    @Override
    public void dataBidder(String idBidder) {
        approval(idBidder);
    }
}