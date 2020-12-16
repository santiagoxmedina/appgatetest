package com.sanmed.appgatetest.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class SignInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    SignInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}