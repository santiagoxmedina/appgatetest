package com.sanmed.appgatetest.ui.attempts;

import androidx.annotation.Nullable;

import com.sanmed.appgatetest.ui.login.Result;
import com.sanmed.appgatetest.ui.login.SignUpUserView;

import java.util.List;

public class SignInAttemptsResult  extends Result<List<SignInAttemptsView>> {

    SignInAttemptsResult(@Nullable Integer error) {
        super(error);
    }

    SignInAttemptsResult(@Nullable List<SignInAttemptsView> success) {
        super(success);
    }
}
