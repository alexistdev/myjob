package com.coder.myjob.model;

import com.google.gson.annotations.SerializedName;

public class AkunModel {
    @SerializedName("name")
    private final String namaPengguna;

    @SerializedName("email")
    private final String emailPengguna;

    @SerializedName("phone")
    private final String phonePengguna;

    public AkunModel(String namaPengguna, String emailPengguna, String phonePengguna) {
        this.namaPengguna = namaPengguna;
        this.emailPengguna = emailPengguna;
        this.phonePengguna = phonePengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public String getEmailPengguna() {
        return emailPengguna;
    }

    public String getPhonePengguna() {
        return phonePengguna;
    }
}
