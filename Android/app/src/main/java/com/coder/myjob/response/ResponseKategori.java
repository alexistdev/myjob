package com.coder.myjob.response;

import com.coder.myjob.model.KategoriModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKategori {
    @SerializedName("result")
    final private List<KategoriModel> itemKategori;

    public ResponseKategori(List<KategoriModel> itemKategori) {
        this.itemKategori = itemKategori;
    }

    public List<KategoriModel> getItemKategori() {
        return itemKategori;
    }
}
