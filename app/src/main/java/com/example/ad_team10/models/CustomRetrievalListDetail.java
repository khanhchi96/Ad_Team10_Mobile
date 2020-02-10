package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomRetrievalListDetail {
    @SerializedName("DepartmentID")
    private int departmentID;
    @SerializedName("DepartmentName")
    private String departmentName;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("QuantityOffered")
    private int quantityOffered;

    public CustomRetrievalListDetail(){}
    public CustomRetrievalListDetail(int departmentID, String departmentName, int quantity, int quantityOffered) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.quantity = quantity;
        this.quantityOffered = quantityOffered;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
}
