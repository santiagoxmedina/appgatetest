package com.sanmed.appgatetest.ui.attempts;

import com.sanmed.appgatetest.data.model.SignInAttempt;

import java.util.ArrayList;
import java.util.List;

public class SignInAttemptHelper {
    public static List<SignInAttemptsView> from(List<SignInAttempt> data) {
        List<SignInAttemptsView> result = new ArrayList<>();
        if(data!=null){
            for (int i = 0; i < data.size(); i++) {
                SignInAttempt signInAttempt = data.get(i);
                result.add(from(signInAttempt));
            }
        }
        return result;
    }

    private static SignInAttemptsView from(SignInAttempt signInAttemptsView) {
        SignInAttemptsView result = new SignInAttemptsView();
        if(signInAttemptsView!=null){
            result.date = signInAttemptsView.date;
            result.success = signInAttemptsView.success;
        }
        return result;
    }
}
