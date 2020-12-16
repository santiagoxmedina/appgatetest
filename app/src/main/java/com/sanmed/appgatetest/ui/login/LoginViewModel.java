package com.sanmed.appgatetest.ui.login;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Patterns;

import com.google.android.gms.maps.model.LatLng;
import com.sanmed.appgatetest.data.ILocationRepository;
import com.sanmed.appgatetest.data.ILoginRepository;
import com.sanmed.appgatetest.data.Result;
import com.sanmed.appgatetest.data.model.SignInUser;
import com.sanmed.appgatetest.R;
import com.sanmed.appgatetest.data.model.SignUpUser;
import com.sanmed.appgatetest.ui.IAction;
import com.sanmed.appgatetest.ui.layouts.ITwoInputsTwoButtons;
import com.sanmed.appgatetest.ui.layouts.TwoInputsTwoButtons;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<SignInResult> mSignInResult;
    private final MutableLiveData<SignUpResult> mSignUpResult;
    private final MutableLiveData<String[]> mPermissionError;
    private final MutableLiveData<String> mMessage;
    private final ILoginRepository mLoginRepository;
    private final ILocationRepository mLocationRepository;
    private final ITwoInputsTwoButtons mTwoInputsTwoButtons;
    public static final int LOCATION_CODE = 1;
    public IAction pendingLocationAction;
    private  Thread mThread;

    LoginViewModel(Application application, ILoginRepository loginRepository, ILocationRepository locationRepository) {
        super(application);
        mLoginRepository = loginRepository;
        mLocationRepository = locationRepository;
        mSignInResult = new MutableLiveData<>();
        mSignUpResult = new MutableLiveData<>();
        mPermissionError = new MutableLiveData<>();
        mMessage = new MutableLiveData<>();
        mTwoInputsTwoButtons = new TwoInputsTwoButtons(
                getApplication().getString(R.string.prompt_email)
                ,getApplication().getString(R.string.prompt_password)
                ,getApplication().getString(R.string.action_sign_in_short)
                ,getApplication().getString(R.string.action_sign_up_short)
                ,this::onSignIn
                ,this::onSingUp
        );
        pendingLocationAction = null;
        mThread = null;
    }

    public void init(){
        mTwoInputsTwoButtons.init();
    }

    private void onSingUp() {
        signUp(mTwoInputsTwoButtons.getInputOneText().getValue(), mTwoInputsTwoButtons.getInputTwoText().getValue());
    }

    public MutableLiveData<String> getMessage() {
        return mMessage;
    }

    public void onMessageCompleted(){
        mMessage.setValue(null);
    }

    public void signUp(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<SignUpUser> result = mLoginRepository.signUp(username, password);

        if (result instanceof Result.Success) {
            SignUpUser data = ((Result.Success<SignUpUser>) result).getData();
            mSignUpResult.setValue(new SignUpResult(new SignUpUserView(data.getUser())));
        } else {
            mSignUpResult.setValue(new SignUpResult(R.string.Sign_up_failed));
        }
    }

    LiveData<SignInResult> getSignInResult() {
        return mSignInResult;
    }

    LiveData<SignUpResult> getSignUpResult() {
        return mSignUpResult;
    }

    public void signIn(String username, String password) {
        Result<SignInUser> result = mLoginRepository.signIn(username, password);

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
        pendingLocationAction = ()->mLocationRepository.getUserLocation((c)->onGetUserLocation(c,userId,success));
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mPermissionError.setValue(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION});
            return;
        }
        runPendingLocationAction();
    }

    private  void runPendingLocationAction(){
        if(pendingLocationAction!=null){
            pendingLocationAction.onAction();
        }
    }

    public LiveData<String[]> getPermissionError() {
        return mPermissionError;
    }

    public void onPermissionErrorCompleted() {
        mPermissionError.setValue(null);
    }

    private void onGetUserLocation(Result<LatLng> locationResult, String userId, boolean success ) {
        if (locationResult instanceof Result.Success) {
            LatLng userLatLng = ((Result.Success<LatLng>) locationResult).getData();
            interruptCurrentThread();
            mThread = new Thread(()->onLoadDate(userLatLng,userId,success));
            mThread.start();

        }else{
            mMessage.setValue(getApplication().getString(R.string.update_location_failed));
        }
    }

    private void onLoadDate(LatLng userLatLng,String userId,boolean success) {
        Result<String> result = mLoginRepository.loadDate(userLatLng.latitude,userLatLng.longitude);
        if (result instanceof Result.Success) {
            String date = ((Result.Success<String>) result).getData();
            mLoginRepository.saveSignInAttempt(userId,date, success);
        }else{
            mMessage.setValue(getApplication().getString(R.string.load_date_fail));
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


    public void onPermissionResult(int requestCode, int[] grantResults) {
        if(requestCode == LoginViewModel.LOCATION_CODE){
            boolean allPermissionGranted = true;
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    allPermissionGranted = false;
                    break;
                }
            }
            if(allPermissionGranted){
                runPendingLocationAction();
            }else{
                mMessage.setValue(getApplication().getString(R.string.location_permission_failed));
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        interruptCurrentThread();
    }

    private void interruptCurrentThread(){
        if(mThread!=null){
            if(mThread.isAlive()){
                mThread.interrupt();
            }
            mThread = null;
        }
    }
}