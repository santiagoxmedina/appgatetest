package com.sanmed.appgatetest.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class SignInResult extends Result<SignInUserView> {
    SignInResult(@Nullable Integer error) {
        super(error);
    }

    SignInResult(@Nullable SignInUserView success) {
        super(success);
    }
}