package com.sanmed.appgatetest.data;

import com.sanmed.appgatetest.data.model.SignInAttempt;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.data.model.SignUpUser;

import java.util.List;

public interface ILoginRepository {
    Result<SignInUser> signIn(String username, String password);
    Result<SignUpUser> signUp(String username, String password);

    Result<List<SignInAttempt>> getSignInAttempts(String userId);
    void saveSignInAttempt(String userId,String date,boolean success);

    Result<String> loadDate(double deviceLatitude,double deviceLongitude);
}
