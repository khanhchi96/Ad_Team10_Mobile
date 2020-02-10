package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomDisbursementList {
    @SerializedName("DepartmentID")
    private int departmentID;
    @SerializedName("DepartmentName")
    private String departmentName;
    @SerializedName("CustomItems")
    private CustomItem[] customItems;

    public CustomDisbursementList(){}
    public CustomDisbursementList(int departmentID, String departmentName, CustomItem[] customItems) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.customItems = customItems;
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

    public CustomItem[] getCustomItems() {
        return customItems;
    }

    public void setCustomItems(CustomItem[] customItems) {
        this.customItems = customItems;
    }
}
