package com.example.instore2.models.publicmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShortCodeMediaModel implements Serializable {

    @SerializedName("__typename")
    private String typename;

    @SerializedName("id")
    private String id;

    @SerializedName("display_url")
    private String display_url;

    @SerializedName("owner")
    private String owner;

    @SerializedName("video_url")
    private String video_url;

    @SerializedName("edge_sidecar_to_children")
    private EdgesToSideCarModel edge_sidecar_to_children;
}
