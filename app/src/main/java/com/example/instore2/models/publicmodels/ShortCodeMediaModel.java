package com.example.instore2.models.publicmodels;

import com.example.instore2.models.UserModel;
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
    private UserModel owner;

    @SerializedName("video_url")
    private String video_url;

    @SerializedName("edge_sidecar_to_children")
    private EdgesToSideCarModel edge_sidecar_to_children;

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

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public EdgesToSideCarModel getEdge_sidecar_to_children() {
        return edge_sidecar_to_children;
    }

    public void setEdge_sidecar_to_children(EdgesToSideCarModel edge_sidecar_to_children) {
        this.edge_sidecar_to_children = edge_sidecar_to_children;
    }
}
