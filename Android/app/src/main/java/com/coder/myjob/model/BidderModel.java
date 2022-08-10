package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class BidderModel {
   @SerializedName("id")
   private final String idBidder;

   @SerializedName("user_id")
   private final String idUser;

   @SerializedName("nama")
   private final String namaUser;

   @SerializedName("email")
   private final String emailUser;

   public BidderModel(String idBidder, String idUser, String namaUser, String emailUser) {
      this.idBidder = idBidder;
      this.idUser = idUser;
      this.namaUser = namaUser;
      this.emailUser = emailUser;
   }

   public String getIdBidder() {
      return idBidder;
   }

   public String getIdUser() {
      return idUser;
   }

   public String getNamaUser() {
      return namaUser;
   }

   public String getEmailUser() {
      return emailUser;
   }
}
