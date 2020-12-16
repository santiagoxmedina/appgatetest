package com.sanmed.appgatetest.data;

import com.sanmed.appgatetest.data.model.SignInAttempt;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.data.model.SignUpUser;

import java.util.List;


public class LoginRepository implements ILoginRepository {

    private static volatile LoginRepository instance;

    private final ILoginDataSource dataSource;

    private LoginRepository(ILoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(ILoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public Result<SignInUser> signIn(String username, String password) {
        return dataSource.signIn(username, password);
    }

    @Override
    public Result<SignUpUser> signUp(String username, String password) {
        return dataSource.signUp(username, password);
    }

    @Override
    public Result<List<SignInAttempt>> getSignInAttempts(String userId) {
        return dataSource.getSignInAttempts(userId);
    }

    @Override
    public void saveSignInAttempt(String userId,String date, boolean success) {
        dataSource.saveSignInAttempt(userId,date,success);
    }

    @Override
    public Result<String> loadDate(double deviceLatitude,double deviceLongitude) {
        return dataSource.loadDate(deviceLatitude,deviceLongitude);
    }
}