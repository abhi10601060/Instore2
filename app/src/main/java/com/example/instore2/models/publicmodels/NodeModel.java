package com.example.instore2.models.publicmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NodeModel implements Serializable {

    @SerializedName("__typename")
    private String typename;

    @SerializedName("id")
    private String id;

    @SerializedName("display_url")
    private String display_url;

    @SerializedName("video_url")
    private String video_url;
}
