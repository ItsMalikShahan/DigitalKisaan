package com.sortscript.digitalkasaan.datamodel.model.fan;

public class UserModel {
    String userName;
    String email;

    public UserModel() {
    }

    public UserModel(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
