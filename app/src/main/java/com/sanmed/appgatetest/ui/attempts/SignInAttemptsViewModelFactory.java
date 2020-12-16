package com.sanmed.appgatetest.ui.attempts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sanmed.appgatetest.data.ILoginDataSource;
import com.sanmed.appgatetest.data.ILoginRepository;
import com.sanmed.appgatetest.data.LoginDataSource;
import com.sanmed.appgatetest.data.LoginRepository;
import com.sanmed.appgatetest.ui.login.LoginViewModel;

public class SignInAttemptsViewModelFactory implements ViewModelProvider.Factory {

    private final Application mApplication;
    private final String mUserId;
    private final ILoginRepository mLoginRepository;
    private final ILoginDataSource mLoginDataSource;

    public SignInAttemptsViewModelFactory(Application application,String userId){
        mApplication = application;
        mLoginDataSource = new LoginDataSource(application);
        mLoginRepository = LoginRepository.getInstance(mLoginDataSource);
        mUserId = userId;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignInAttemptsViewModel.class)) {
            return (T) new SignInAttemptsViewModel(mApplication, mLoginRepository,mUserId);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
