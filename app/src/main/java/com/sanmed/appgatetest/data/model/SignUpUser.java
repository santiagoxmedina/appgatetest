package com.sanmed.appgatetest.data.model;

public class SignUpUser {

    private String user;
    private String password;

    public SignUpUser(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
