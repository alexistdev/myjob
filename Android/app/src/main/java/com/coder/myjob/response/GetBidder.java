package com.coder.myjob.response;

import com.coder.myjob.model.BidderModel;
import com.coder.myjob.model.JobModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBidder {
   @SerializedName("result")
   List<BidderModel> listBidder;

   @SerializedName("message")
   final private String message;

   @SerializedName("status")
   final private Boolean status;

   public GetBidder(List<BidderModel> listBidder, String message, Boolean status) {
      this.listBidder = listBidder;
      this.message = message;
      this.status = status;
   }

   public List<BidderModel> getListBidder() {
      return listBidder;
   }

   public String getMessage() {
      return message;
   }

   public Boolean getStatus() {
      return status;
   }
}
