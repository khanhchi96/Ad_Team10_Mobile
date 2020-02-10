package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomRequisition {
    @SerializedName("RequisitionID")
    private int requisitionID;
    @SerializedName("RequisitionDate")
    private String requisitionDate;
    @SerializedName("EmployeeName")
    private String employeeName;
    @SerializedName("CustomItems")
    private CustomItem[] customItems;

    public CustomRequisition(){}
    public CustomRequisition(int requisitionID, String requisitionDate, String employeeName, CustomItem[] customItems) {
        this.requisitionID = requisitionID;
        this.requisitionDate = requisitionDate;
        this.employeeName = employeeName;
        this.customItems = customItems;
    }

    public int getRequisitionID() {
        return requisitionID;
    }

    public void setRequisitionID(int requisitionID) {
        this.requisitionID = requisitionID;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public CustomItem[] getCustomItems() {
        return customItems;
    }

    public void setCustomItems(CustomItem[] customItems) {
        this.customItems = customItems;
    }
}
