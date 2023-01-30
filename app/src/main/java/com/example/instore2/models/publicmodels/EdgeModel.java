package com.example.instore2.models.publicmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EdgeModel implements Serializable {

    @SerializedName("node")
    private NodeModel node;

    public NodeModel getNode() {
        return node;
    }

    public void setNode(NodeModel node) {
        this.node = node;
    }
}
