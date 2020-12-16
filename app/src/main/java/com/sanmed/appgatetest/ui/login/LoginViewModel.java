package com.sanmed.appgatetest.ui.login;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.util.Patterns;

import com.sanmed.appgatetest.data.ILoginRepository;
import com.sanmed.appgatetest.data.Result;
import com.sanmed.appgatetest.data.model.SignInAttempt;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.R;
import com.sanmed.appgatetest.data.model.SignUpUser;
import com.sanmed.appgatetest.ui.layouts.ITwoInputsTwoButtons;
import com.sanmed.appgatetest.ui.layouts.TwoInputsTwoButtons;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<SignInResult> mSignInResult;
    private final MutableLiveData<SignUpResult> mSignUpResult;
    private final ILoginRepository loginRepository;
    private final ITwoInputsTwoButtons mTwoInputsTwoButtons;

    LoginViewModel(Application application,ILoginRepository loginRepository) {
        super(application);
        this.loginRepository = loginRepository;
        mSignInResult = new MutableLiveData<>();
        mSignUpResult = new MutableLiveData<>();
        mTwoInputsTwoButtons = new TwoInputsTwoButtons(
                getApplication().getString(R.string.prompt_email)
                ,getApplication().getString(R.string.prompt_password)
                ,getApplication().getString(R.string.action_sign_in_short)
                ,getApplication().getString(R.string.action_sign_up_short)
                ,this::onSignIn
                ,this::onSingUp
        );
    }

    public void init(){
        mTwoInputsTwoButtons.init();
    }

    private void onSingUp() {
        signUp(mTwoInputsTwoButtons.getInputOneText().getValue(), mTwoInputsTwoButtons.getInputTwoText().getValue());
    }

    public void signUp(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<SignUpUser> result = loginRepository.signUp(username, password);

        if (result instanceof Result.Success) {
            SignUpUser data = ((Result.Success<SignUpUser>) result).getData();
            mSignUpResult.setValue(new SignUpResult(new SignUpUserView(data.getUser())));
        } else {
            mSignUpResult.setValue(new SignUpResult(R.string.login_failed));
        }
    }

    LiveData<SignInResult> getSignInResult() {
        return mSignInResult;
    }

    LiveData<SignUpResult> getSignUpResult() {
        return mSignUpResult;
    }

    public void signIn(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<SignInUser> result = loginRepository.signIn(username, password);

        if (result instanceof Result.Success) {
            SignInUser data = ((Result.Success<SignInUser>) result).getData();
            mSignInResult.setValue(new SignInResult(new SignInUserView(data.getDisplayName())));
            onSignInAttempt(username,true);
        } else {
            mSignInResult.setValue(new SignInResult(R.string.login_failed));
            onSignInAttempt(username,false);
        }
    }

    private void onSignInAttempt(String userId,boolean success ) {
        Result<String> result = loginRepository.loadDate();
        if (result instanceof Result.Success) {
            String date = ((Result.Success<String>) result).getData();
            loginRepository.saveSignInAttempt(userId,date, success);
        }
    }


    public ITwoInputsTwoButtons getTwoInputsOneButton(){
        return mTwoInputsTwoButtons;
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }


    public LiveData<String> getUserText() {
        return mTwoInputsTwoButtons.getInputOneText();
    }

    public LiveData<String> getPasswordText() {
        return mTwoInputsTwoButtons.getInputTwoText();
    }

    public void onUserTextChanged(String user) {
        if (!isUserNameValid(user)) {
            mTwoInputsTwoButtons.setFirstCondition(false);
            mTwoInputsTwoButtons.setInputOneErrorText(getApplication().getString(R.string.invalid_username));
        }else{
            mTwoInputsTwoButtons.setFirstCondition(true);
            mTwoInputsTwoButtons.setInputOneErrorText(null);
        }
    }

    public void onPasswordTextChanged(String password) {
        if (!isPasswordValid(password)) {
            mTwoInputsTwoButtons.setInputTwoErrorText(getApplication().getString(R.string.invalid_password));
            mTwoInputsTwoButtons.setSecondCondition(false);
        }else{
            mTwoInputsTwoButtons.setInputTwoErrorText(null);
            mTwoInputsTwoButtons.setSecondCondition(true);
        }
    }

    public void onSignIn() {
        signIn(mTwoInputsTwoButtons.getInputOneText().getValue(), mTwoInputsTwoButtons.getInputTwoText().getValue());
    }

    public void onSignInResultCompleted() {
        mSignInResult.setValue(null);
    }

    public void onSignUpResultCompleted() {
        mSignUpResult.setValue(null);
    }
}