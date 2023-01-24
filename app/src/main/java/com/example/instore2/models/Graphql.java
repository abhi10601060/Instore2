package com.example.instore2.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Graphql implements Serializable {
    @SerializedName("user")
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
