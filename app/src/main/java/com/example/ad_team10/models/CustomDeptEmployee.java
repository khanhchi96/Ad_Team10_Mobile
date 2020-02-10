package com.example.ad_team10.models;

import com.google.gson.annotations.SerializedName;

public class CustomDeptEmployee {
    @SerializedName("DepartmentID")
    private int DepartmentID;
    @SerializedName("DeptEmployeeID")
    private int DeptEmployeeID;
    @SerializedName("DeptEmployeeName")
    private String DeptEmployeeName;
    @SerializedName("Designation")
    private String Designation;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Phone")
    private String Phone;

    public CustomDeptEmployee(int departmentID, int deptEmployeeID, String deptEmployeeName, String designation, String email, String phone) {
        DepartmentID = departmentID;
        DeptEmployeeID = deptEmployeeID;
        DeptEmployeeName = deptEmployeeName;
        Designation = designation;
        Email = email;
        Phone = phone;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    public int getDeptEmployeeID() {
        return DeptEmployeeID;
    }

    public void setDeptEmployeeID(int deptEmployeeID) {
        DeptEmployeeID = deptEmployeeID;
    }

    public String getDeptEmployeeName() {
        return DeptEmployeeName;
    }

    public void setDeptEmployeeName(String deptEmployeeName) {
        DeptEmployeeName = deptEmployeeName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
