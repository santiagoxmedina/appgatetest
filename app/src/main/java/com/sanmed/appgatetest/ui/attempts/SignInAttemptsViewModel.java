package com.sanmed.appgatetest.ui.attempts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sanmed.appgatetest.R;
import com.sanmed.appgatetest.data.ILoginRepository;
import com.sanmed.appgatetest.data.Result;
import com.sanmed.appgatetest.data.model.SignInAttempt;

import java.util.List;


public class SignInAttemptsViewModel extends AndroidViewModel {

    private final MutableLiveData<SignInAttemptsResult> listSignInAttemptsViewMutableLiveData;
    private boolean initialized;
    private ILoginRepository mLoginRepository;
    private final String mUserId;
    public SignInAttemptsViewModel(@NonNull Application application, ILoginRepository loginRepository,String userId) {
        super(application);
        listSignInAttemptsViewMutableLiveData = new MutableLiveData<>();
        initialized = false;
        mLoginRepository = loginRepository;
        mUserId = userId;
    }

    public void init(){
        if(!initialized){
            initialized =true;
            updateSignInAttempts();
        }
    }

    private void updateSignInAttempts() {
        Result<List<SignInAttempt>> result = mLoginRepository.getSignInAttempts(mUserId);
        if (result instanceof Result.Success) {
            List<SignInAttempt> data = ((Result.Success<List<SignInAttempt>>) result).getData();
            listSignInAttemptsViewMutableLiveData.setValue(new SignInAttemptsResult(SignInAttemptHelper.from(data)));
        } else {
            listSignInAttemptsViewMutableLiveData.setValue(new SignInAttemptsResult(R.string.loading_data_fail));
        }

    }

    public void onUpdateList(){
        updateSignInAttempts();
    }

    public LiveData<SignInAttemptsResult> getSignInAttempts() {
        return listSignInAttemptsViewMutableLiveData;
    }
}