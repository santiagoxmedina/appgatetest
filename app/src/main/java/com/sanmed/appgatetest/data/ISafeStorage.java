package com.sanmed.appgatetest.data;

import com.sanmed.appgatetest.data.model.SignInAttempt;

import java.util.List;

public interface ISafeStorage {
    boolean loadUser(String username, String password);

    boolean saveUser(String username, String password);

    List<SignInAttempt> loadSignInAttempts(String userId);

    boolean saveSignInAttempt(String userId, String date, boolean success);

    boolean isNewUser(String username);
}
