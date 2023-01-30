package com.example.instore2.models.publicmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EdgesToSideCarModel implements Serializable {

    @SerializedName("edges")
    private List<EdgeModel> edges;

    public List<EdgeModel> getEdges() {
        return edges;
    }

    public void setEdges(List<EdgeModel> edges) {
        this.edges = edges;
    }
}
