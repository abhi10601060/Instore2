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

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
