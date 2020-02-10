package com.example.ad_team10.viewModels;

import com.google.gson.annotations.SerializedName;

public class LoginView {
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;
    @SerializedName("UserType")
    private String userType;

    public LoginView(String userName, String password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
