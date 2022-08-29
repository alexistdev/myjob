package com.coder.myjob.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coder.myjob.R;
import com.coder.myjob.model.BidderModel;
import com.coder.myjob.model.JobModel;

import java.util.List;

public class BidderAdapter extends RecyclerView.Adapter<BidderAdapter.MyBidderHolder> {
    List<BidderModel> mBidderList;
    public BidderAdapter.ClickListener clicklistener;

    public BidderAdapter(List<BidderModel> mBidderList, ClickListener clickListener) {
        this.mBidderList = mBidderList;
        this.clicklistener = clickListener;
    }

    @NonNull
    @Override
    public MyBidderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bidder, parent, false);
        BidderAdapter.MyBidderHolder holder;
        holder = new MyBidderHolder(mView);
        return holder;
    }

    public static class MyBidderHolder extends RecyclerView.ViewHolder {
        private final TextView mNama,mEmail;
        private final Button mApprove;

        MyBidderHolder(@NonNull View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.txt_nama);
            mEmail = itemView.findViewById(R.id.txt_email);
            mApprove = itemView.findViewById(R.id.btn_approve);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull MyBidderHolder holder, @SuppressLint("RecyclerView") final int position){
        holder.mNama.setText(mBidderList.get(position).getNamaUser());
        holder.mEmail.setText(mBidderList.get(position).getEmailUser());
        holder.mApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicklistener.dataBidder(mBidderList.get(position).getIdBidder());
            }
        });
    }


    @Override
    public int getItemCount () {
        return mBidderList.size();
    }

    public void replaceData(List<BidderModel> bidderModels) {
        this.mBidderList = bidderModels;
        notifyDataSetChanged();
    }

    public interface ClickListener{
        void dataBidder(String idBidder);
    }

}
