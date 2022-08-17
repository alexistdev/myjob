package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class ChatModel {
   @SerializedName("id")
   private final String idChat;

   @SerializedName("job_id")
   private final String jobId;

   @SerializedName("freelancer")
   private final String namaFreelancer;

   @SerializedName("judul")
   private final String judulChat;

   @SerializedName("tanggal")
   private final String tanggal;

   public ChatModel(String idChat, String jobId, String namaFreelancer, String judulChat, String tanggal) {
      this.idChat = idChat;
      this.jobId = jobId;
      this.namaFreelancer = namaFreelancer;
      this.judulChat = judulChat;
      this.tanggal = tanggal;
   }

   public String getIdChat() {
      return idChat;
   }

   public String getJobId() {
      return jobId;
   }

   public String getNamaFreelancer() {
      return namaFreelancer;
   }

   public String getJudulChat() {
      return judulChat;
   }

   public String getTanggal() {
      return tanggal;
   }
}
