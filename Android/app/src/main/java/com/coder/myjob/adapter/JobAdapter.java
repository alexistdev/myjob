package com.coder.myjob.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.coder.myjob.Detailjobfreelancer;
import com.coder.myjob.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.coder.myjob.model.JobModel;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyJobHolder> {
    List<JobModel> mJobList;

    public JobAdapter(List<JobModel> mJobList) {
        this.mJobList = mJobList;
    }

    @NonNull
    @Override
    public MyJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_job_freelancer, parent, false);
        JobAdapter.MyJobHolder holder;
        holder = new MyJobHolder(mView);
        return holder;
    }

    public static class MyJobHolder extends RecyclerView.ViewHolder {
        private final TextView mJudul,mDeskripsi,mFee,mTanggal;

        MyJobHolder(@NonNull View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.txt_judul);
            mDeskripsi = itemView.findViewById(R.id.txt_deskripsi);
            mFee = itemView.findViewById(R.id.txt_fee);
            mTanggal = itemView.findViewById(R.id.txt_tanggal);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull MyJobHolder holder,final int position){
        holder.mJudul.setText(mJobList.get(position).getJudulJob());
        holder.mDeskripsi.setText(mJobList.get(position).getDeskripsi());
        String fee = holder.itemView.getContext().getString(R.string.singlejob1, mJobList.get(position).getFee());
        String deadline = holder.itemView.getContext().getString(R.string.singlejob2, mJobList.get(position).getDeadline());
        holder.mFee.setText(fee);
        holder.mTanggal.setText(deadline);
        holder.itemView.setOnClickListener(v -> {
            Intent mIntent = new Intent(v.getContext(), Detailjobfreelancer.class);
            mIntent.putExtra("judul", mJobList.get(position).getJudulJob());
            mIntent.putExtra("deadline", mJobList.get(position).getDeadline());
            mIntent.putExtra("fee", mJobList.get(position).getFee());
            mIntent.putExtra("deskripsi", mJobList.get(position).getDeskripsi());
            mIntent.putExtra("idJob", mJobList.get(position).getIdJob());
            v.getContext().startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount () {
        return mJobList.size();
    }

    public void replaceData(List<JobModel> jobModel) {
        this.mJobList = jobModel;
        notifyDataSetChanged();
    }
}
