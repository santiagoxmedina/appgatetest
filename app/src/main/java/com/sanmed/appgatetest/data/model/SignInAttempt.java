package com.sanmed.appgatetest.data.model;

public class SignInAttempt {
    public String date;
    public boolean success;

    public SignInAttempt(String date, boolean success) {
        this.date = date;
        this.success = success;
    }
}
