package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CollectionPoint {
    @SerializedName("CollectionPointID")
    private int collectionPointID;
    @SerializedName("CollectionPointName")
    private String collectionPointName;

    public CollectionPoint(int collectionPointID, String collectionPointName) {
        this.collectionPointID = collectionPointID;
        this.collectionPointName = collectionPointName;
    }

    public int getCollectionPointID() {
        return collectionPointID;
    }

    public void setCollectionPointID(int collectionPointID) {
        this.collectionPointID = collectionPointID;
    }

    public String getCollectionPointName() {
        return collectionPointName;
    }

    public void setCollectionPointName(String collectionPointName) {
        this.collectionPointName = collectionPointName;
    }
}
