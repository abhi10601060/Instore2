package com.example.instore2.models.publicmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MainModel implements Serializable {

    @SerializedName("graphql")
    private GraphqlModel graphql;

    public GraphqlModel getGraphql() {
        return graphql;
    }

    public void setGraphql(GraphqlModel graphql) {
        this.graphql = graphql;
    }
}
