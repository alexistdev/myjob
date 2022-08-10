package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class JobModel {
    @SerializedName("id")
    private final String idJob;

    @SerializedName("status_bid")
    private final String statusBid;

    @SerializedName("name")
    private final String judulJob;

    @SerializedName("deskripsi")
    private final String deskripsi;

    @SerializedName("fee")
    private final String fee;

    @SerializedName("deadline")
    private final String deadline;

    @SerializedName("bidder")
    private final String bidder;

    @SerializedName("status")
    private final String status;

    public JobModel(String idJob, String statusBid, String judulJob, String deskripsi, String fee, String deadline, String bidder, String status) {
        this.idJob = idJob;
        this.statusBid = statusBid;
        this.judulJob = judulJob;
        this.deskripsi = deskripsi;
        this.fee = fee;
        this.deadline = deadline;
        this.bidder = bidder;
        this.status = status;
    }

    public String getIdJob() {
        return idJob;
    }

    public String getStatusBid() {
        return statusBid;
    }

    public String getJudulJob() {
        return judulJob;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFee() {
        return fee;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getBidder() {
        return bidder;
    }

    public String getStatus() {
        return status;
    }
}
