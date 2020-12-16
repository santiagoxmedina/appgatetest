package com.sanmed.appgatetest.data;

import com.sanmed.appgatetest.data.model.SignInAttempt;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.data.model.SignUpUser;

import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository implements ILoginRepository {

    private static volatile LoginRepository instance;

    private ILoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private SignInUser userSignIn = null;
    private SignUpUser userSignUp = null;

    // private constructor : singleton access
    private LoginRepository(ILoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(ILoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return userSignIn != null;
    }

    public void logout() {
        userSignIn = null;
        dataSource.logout();
    }

    private void setLoggedInUser(SignInUser user) {
        this.userSignIn = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private void setLoggedInUser(SignUpUser user) {
        this.userSignUp = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<SignInUser> signIn(String username, String password) {
        // handle login
        Result<SignInUser> result = dataSource.signIn(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<SignInUser>) result).getData());
        }
        return result;
    }

    @Override
    public Result<SignUpUser> signUp(String username, String password) {
        Result<SignUpUser> result = dataSource.signUp(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<SignUpUser>) result).getData());
        }
        return result;
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
    public Result<String> loadDate() {
        return dataSource.loadDate();
    }
}