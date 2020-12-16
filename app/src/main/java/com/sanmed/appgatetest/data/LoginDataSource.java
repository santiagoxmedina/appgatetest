package com.sanmed.appgatetest.data;

import android.content.Context;

import com.sanmed.appgatetest.data.model.SignInAttempt;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.data.model.SignUpUser;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements ILoginDataSource {

    private final LocalSecurity mLocalSecurity;

    public LoginDataSource(Context context) {
        mLocalSecurity = new LocalSecurity(context);
    }

    public Result<SignInUser> signIn(String username, String password) {

        try {
            if (mLocalSecurity.loadUser(username, password)) {
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
            if (mLocalSecurity.saveUser(username, password)) {
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
            List<SignInAttempt> signInAttempts = mLocalSecurity.loadSignInAttempts(userId);
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
        mLocalSecurity.saveSignInAttempt(userId,date,success);
    }

    @Override
    public Result<String> loadDate() {
        return new Result.Success<>(Calendar.getInstance().getTime().toString());
    }

    public void logout() {
    }
}