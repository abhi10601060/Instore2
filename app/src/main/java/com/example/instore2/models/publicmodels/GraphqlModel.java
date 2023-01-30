package com.example.instore2.models.publicmodels;

import com.example.instore2.models.UserModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GraphqlModel implements Serializable {

    @SerializedName("shortcode_media")
    private ShortCodeMediaModel shortcode_media;

    @SerializedName("user")
    private UserModel user;


    public ShortCodeMediaModel getShortcode_media() {
        return shortcode_media;
    }

    public void setShortcode_media(ShortCodeMediaModel shortcode_media) {
        this.shortcode_media = shortcode_media;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
