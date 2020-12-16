package com.sanmed.appgatetest.data;

import android.content.Context;

import com.sanmed.appgatetest.data.model.SignInAttempt;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.data.model.SignUpUser;

import java.io.IOException;
import java.util.List;

public class LoginDataSource implements ILoginDataSource {

    private final ISafeStorage mSafeStorage;
    private final IApiClient mApiClient;

    public LoginDataSource(Context context) {
        mSafeStorage = new SafeStorage(context);
        mApiClient = new ApiClient();
    }

    public Result<SignInUser> signIn(String username, String password) {

        try {
            if (mSafeStorage.loadUser(username, password)) {
                return new Result.Success<>(new SignInUser("", username));
            } else {
                return new Result.Error(new Exception("Error Sign In"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error Sign in", e));
        }
    }

    @Override
    public Result<SignUpUser> signUp(String username, String password) {
        try {
            if (mSafeStorage.isNewUser(username) && mSafeStorage.saveUser(username, password)) {
                return new Result.Success<>(new SignUpUser(username, password));
            } else {
                return new Result.Error(new Exception("Error Sign up"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error Sign up", e));
        }
    }

    @Override
    public Result<List<SignInAttempt>> getSignInAttempts(String userId) {
        try {
            List<SignInAttempt> signInAttempts = mSafeStorage.loadSignInAttempts(userId);
            if (signInAttempts!=null) {
                return new Result.Success<>(signInAttempts);
            } else {
                return new Result.Error(new Exception("Error Sign in Attempts"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error Sign in Attempts", e));
        }
    }

    @Override
    public void saveSignInAttempt(String userId,String date, boolean success) {
        mSafeStorage.saveSignInAttempt(userId,date,success);
    }

    @Override
    public Result<String> loadDate(double deviceLatitude,double deviceLongitude) {
        try {

            return mApiClient.loadDate(deviceLatitude,deviceLongitude);
        } catch (IOException e) {
            return new Result.Error(new IOException("Error load date", e));
        }
    }

    public void logout() {
    }
}