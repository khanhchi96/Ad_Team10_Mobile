package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomItem {
    @SerializedName("ItemID")
    private int itemID;
    @SerializedName("Description")
    private String description;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("QuantityOffered")
    private int quantityOffered;
    @SerializedName("QuantityReceived")
    private int quantityReceived;

    public CustomItem(){}

    public CustomItem(int itemID, String description, int quantity, int quantityOffered, int quantityReceived) {
        this.itemID = itemID;
        this.description = description;
        this.quantity = quantity;
        this.quantityOffered = quantityOffered;
        this.quantityReceived = quantityReceived;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityOffered() {
        return quantityOffered;
    }

    public void setQuantityOffered(int quantityOffered) {
        this.quantityOffered = quantityOffered;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }
}
