package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomPurchaseOrder {
    @SerializedName("OrderID")
    private int orderID;
    @SerializedName("SupplierName")
    private String supplierName;
    @SerializedName("Status")
    private String status;
    @SerializedName("OrderDate")
    private String orderDate;
    @SerializedName("CustomItems")
    private CustomItem[] customItems;

    public CustomPurchaseOrder(){}
    public CustomPurchaseOrder(int orderID, String supplierName, String status, String orderDate, CustomItem[] customItems) {
        this.orderID = orderID;
        this.supplierName = supplierName;
        this.status = status;
        this.orderDate = orderDate;
        this.customItems = customItems;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public CustomItem[] getCustomItems() {
        return customItems;
    }

    public void setCustomItems(CustomItem[] customItems) {
        this.customItems = customItems;
    }
}
