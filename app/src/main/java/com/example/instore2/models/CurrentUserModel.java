package com.example.instore2.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentUserModel implements Serializable {

    @SerializedName("user")
    private UserModel user;
    @SerializedName("status")
    private String status;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
