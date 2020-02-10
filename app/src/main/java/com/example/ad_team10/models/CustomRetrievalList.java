package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomRetrievalList {
    @SerializedName("ItemID")
    public int itemID;
    @SerializedName("RetrievalListDetails")
    public CustomRetrievalListDetail[] retrievalListDetails;

    public CustomRetrievalList(){}
    public CustomRetrievalList(int itemID, CustomRetrievalListDetail[] retrievalListDetails) {
        this.itemID = itemID;
        this.retrievalListDetails = retrievalListDetails;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public CustomRetrievalListDetail[] getRetrievalListDetails() {
        return retrievalListDetails;
    }

    public void setRetrievalListDetails(CustomRetrievalListDetail[] retrievalListDetails) {
        this.retrievalListDetails = retrievalListDetails;
    }
}
