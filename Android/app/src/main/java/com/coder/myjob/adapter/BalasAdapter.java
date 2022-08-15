package com.coder.myjob.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coder.myjob.R;
import com.coder.myjob.model.BalasModel;
import com.coder.myjob.model.BidderModel;

import java.util.List;

public class BalasAdapter extends RecyclerView.Adapter<BalasAdapter.MyBalasHolder> {
    List<BalasModel> mBalasList;

    public BalasAdapter(List<BalasModel> mBalasList) {
        this.mBalasList = mBalasList;
    }

    @NonNull
    @Override
    public MyBalasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_detailpesan, parent, false);
        BalasAdapter.MyBalasHolder holder;
        holder = new MyBalasHolder(mView);
        return holder;
    }

    public static class MyBalasHolder extends RecyclerView.ViewHolder {
        private final TextView mPesan,mPengguna;

        MyBalasHolder(@NonNull View itemView) {
            super(itemView);
            mPesan = itemView.findViewById(R.id.txtPesan);
            mPengguna = itemView.findViewById(R.id.txtPengguna);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull MyBalasHolder holder,final int position){
        holder.mPesan.setText(mBalasList.get(position).getPesan());
        holder.mPengguna.setText(mBalasList.get(position).getNamaPengguna());
    }

    @Override
    public int getItemCount () {
        return mBalasList.size();
    }

    public void replaceData(List<BalasModel> balasModels) {
        this.mBalasList = balasModels;
        notifyDataSetChanged();
    }
}
