package com.sanmed.appgatetest.ui.login;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.sanmed.appgatetest.data.ILoginDataSource;
import com.sanmed.appgatetest.data.ILoginRepository;
import com.sanmed.appgatetest.data.LoginDataSource;
import com.sanmed.appgatetest.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Application mApplication;
    private final ILoginRepository mLoginRepository;
    private final ILoginDataSource mLoginDataSource;

    public LoginViewModelFactory(Application application){
        mApplication = application;
        mLoginDataSource = new LoginDataSource(application);
        mLoginRepository = LoginRepository.getInstance(mLoginDataSource);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mLoginRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}