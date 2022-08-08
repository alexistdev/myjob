package com.coder.myjob.response;

import com.coder.myjob.model.JobModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetJob {
   @SerializedName("result")
   List<JobModel> listJob;

   @SerializedName("message")
   final private String message;

   @SerializedName("status")
   final private Boolean status;

   public GetJob(List<JobModel> listJob, String message, Boolean status) {
      this.listJob = listJob;
      this.message = message;
      this.status = status;
   }

   public List<JobModel> getListJob() {
      return listJob;
   }

   public String getMessage() {
      return message;
   }

   public Boolean getStatus() {
      return status;
   }
}
