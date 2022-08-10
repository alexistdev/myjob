package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class KategoriModel {
      @SerializedName("id")
      final private String idKategori;

   @SerializedName("name")
   final private String namaKategori;

   @SerializedName("tag")
   final private String taggar;

   public KategoriModel(String idKategori, String namaKategori, String taggar) {
      this.idKategori = idKategori;
      this.namaKategori = namaKategori;
      this.taggar = taggar;
   }

   public String getIdKategori() {
      return idKategori;
   }

   public String getNamaKategori() {
      return namaKategori;
   }

   public String getTaggar() {
      return taggar;
   }
}
