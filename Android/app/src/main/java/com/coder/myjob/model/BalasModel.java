package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class BalasModel {
   @SerializedName("id")
   private String idBalas;

   @SerializedName("pesan")
   private final String pesan;

   @SerializedName("nama_pengguna")
   private final String namaPengguna;

   public BalasModel(String idBalas, String pesan, String namaPengguna) {
      this.idBalas = idBalas;
      this.pesan = pesan;
      this.namaPengguna = namaPengguna;

   }

   public String getIdBalas() {
      return idBalas;
   }

   public void setIdBalas(String idBalas) {
      this.idBalas = idBalas;
   }

   public String getPesan() {
      return pesan;
   }

   public String getNamaPengguna() {
      return namaPengguna;
   }


}
