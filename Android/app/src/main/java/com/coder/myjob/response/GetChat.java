package com.coder.myjob.response;

import com.coder.myjob.model.BalasModel;
import com.coder.myjob.model.ChatModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetChat {
    @SerializedName("result")
    List<ChatModel> daftarChat;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetChat(List<ChatModel> daftarChat, String message, Boolean status) {
        this.daftarChat = daftarChat;
        this.message = message;
        this.status = status;
    }

    public List<ChatModel> getDaftarChat() {
        return daftarChat;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
