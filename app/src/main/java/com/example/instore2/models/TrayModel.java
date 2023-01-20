package com.example.instore2.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrayModel implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("items")
    private List<ItemModel> items;
    @SerializedName("media_count")
    private int mediacount;
    @SerializedName("user")
    private UserModel user;

    @SerializedName("num_results")
    private int num_results;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public int getMediacount() {
        return this.mediacount;
    }

    public void setMediacount(int i) {
        this.mediacount = i;
    }

    public List<ItemModel> getItems() {
        return this.items;
    }

    public void setItems(List<ItemModel> list) {
        this.items = list;
    }

    public int getNum_results() {
        return num_results;
    }

    public void setNum_results(int num_results) {
        this.num_results = num_results;
    }
}