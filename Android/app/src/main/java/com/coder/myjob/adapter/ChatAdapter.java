package com.coder.myjob.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.coder.myjob.Detailchat;
import com.coder.myjob.R;
import com.coder.myjob.model.ChatModel;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyChatHolder> {
    List<ChatModel> mChatList;

    public ChatAdapter(List<ChatModel> mChatList) {
        this.mChatList = mChatList;
    }

    @NonNull
    @Override
    public MyChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_chat, parent, false);
        ChatAdapter.MyChatHolder holder;
        holder = new MyChatHolder(mView);
        return holder;
    }

    public static class MyChatHolder extends RecyclerView.ViewHolder {
        private final TextView mFreelancer,mTanggal,mJudul;

        MyChatHolder(@NonNull View itemView) {
            super(itemView);
            mFreelancer = itemView.findViewById(R.id.txt_namafreelancer);
            mTanggal = itemView.findViewById(R.id.txt_tanggal);
            mJudul = itemView.findViewById(R.id.txt_judul);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull MyChatHolder holder,final int position){
        holder.mFreelancer.setText(mChatList.get(position).getNamaFreelancer());
        holder.mJudul.setText(mChatList.get(position).getJudulChat());
        holder.mTanggal.setText(mChatList.get(position).getTanggal());
        holder.itemView.setOnClickListener(v -> {

            Intent mIntent = new Intent(v.getContext(), Detailchat.class);
            mIntent.putExtra("idjob", mChatList.get(position).getJobId());
            v.getContext().startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount () {
        return mChatList.size();
    }

    public void replaceData(List<ChatModel> chatModel) {
        this.mChatList = chatModel;
        notifyDataSetChanged();
    }
}
