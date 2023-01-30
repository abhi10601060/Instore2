package com.example.instore2.models.publicmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EdgeModel implements Serializable {

    @SerializedName("node")
    private NodeModel node;

}
