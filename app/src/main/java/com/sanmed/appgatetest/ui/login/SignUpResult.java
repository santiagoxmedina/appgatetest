package com.sanmed.appgatetest.ui.login;

import androidx.annotation.Nullable;

public class SignUpResult extends Result<SignUpUserView>{
    SignUpResult(@Nullable Integer error) {
        super(error);
    }

    SignUpResult(@Nullable SignUpUserView success) {
        super(success);
    }
}
