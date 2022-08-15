package com.coder.myjob.response;

import com.coder.myjob.model.BalasModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBalas {
    @SerializedName("result")
    List<BalasModel> daftarBalas;

    @SerializedName("chatID")
    private String chatId;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetBalas(List<BalasModel> daftarBalas, String chatId, String message, Boolean status) {
        this.daftarBalas = daftarBalas;
        this.chatId = chatId;
        this.message = message;
        this.status = status;
    }

    public List<BalasModel> getDaftarBalas() {
        return daftarBalas;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
