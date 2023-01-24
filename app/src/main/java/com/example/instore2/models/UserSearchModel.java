package com.example.instore2.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserSearchModel implements Serializable {
    @SerializedName("graphql")
    private Graphql graphql;

    public Graphql getGraphql() {
        return graphql;
    }

    public void setGraphql(Graphql graphql) {
        this.graphql = graphql;
    }
}
