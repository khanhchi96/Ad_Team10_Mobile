package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomDepartment {
    @SerializedName("DepartmentID")
    private int departmentID;
    @SerializedName("DepartmentName")
    private String departmentName;
    @SerializedName("CollectionPoint")
    private CollectionPoint collectionPoint;
    @SerializedName("Representative")
    private CustomDeptEmployee representative;


    public CustomDepartment(){}

    public CustomDepartment(int departmentID, String departmentName, CollectionPoint collectionPoint, CustomDeptEmployee representative) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.collectionPoint = collectionPoint;
        this.representative = representative;
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

    public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public CustomDeptEmployee getRepresentative() {
        return representative;
    }

    public void setRepresentative(CustomDeptEmployee representative) {
        this.representative = representative;
    }
}
