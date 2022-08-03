package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("id_user")
    private final String idUser;
    @SerializedName("token")
    private final String token;
    @SerializedName("role")
    private final String role;

    public LoginModel(String idUser, String token, String role) {
        this.idUser = idUser;
        this.token = token;
        this.role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
