package com.sanmed.appgatetest.ui.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.sanmed.appgatetest.R;
import com.sanmed.appgatetest.databinding.ViewTwoInputsTwoButtonsBinding;

public class LoginFragment extends Fragment {

    private ViewTwoInputsTwoButtonsBinding mDataBinding;
    private LoginViewModel mLoginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.view_two_inputs_two_buttons, container, false);
        mDataBinding.setLifecycleOwner(getViewLifecycleOwner());
        initViewModel();
        mDataBinding.setViewModel(mLoginViewModel.getTwoInputsOneButton());
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewComponents();
        mLoginViewModel.init();
    }

    private void initViewComponents() {

        mLoginViewModel.getSignInResult().observe(getViewLifecycleOwner(), this::onSignInResult);
        mLoginViewModel.getSignUpResult().observe(getViewLifecycleOwner(), this::onSignUpResult);
        mLoginViewModel.getMessage().observe(getViewLifecycleOwner(), this::onMessageChanged);
        mLoginViewModel.getUserText().observe(getViewLifecycleOwner(), mLoginViewModel::onUserTextChanged);
        mLoginViewModel.getPasswordText().observe(getViewLifecycleOwner(), mLoginViewModel::onPasswordTextChanged);
        mLoginViewModel.getPermissionError().observe(getViewLifecycleOwner(), this::onPermissionError);
        mDataBinding.editTextPassword2.setOnEditorActionListener(this::onEditText);
    }

    private boolean onEditText(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mLoginViewModel.onSignIn();
        }
        return false;
    }

    private void onMessageChanged(String message) {
        if(message !=null && message.trim().isEmpty()){
            showMessage(message);
            mLoginViewModel.onMessageCompleted();
        }
    }

    public void onPermissionError(String[] permissions) {
        if (permissions != null) {
            requestPermissions(permissions,LoginViewModel.LOCATION_CODE);
            mLoginViewModel.onPermissionErrorCompleted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLoginViewModel.onPermissionResult(requestCode,grantResults);
    }

    private void initViewModel() {
        LoginViewModelFactory factory = new LoginViewModelFactory(requireActivity().getApplication());
        mLoginViewModel = new ViewModelProvider(this, factory)
                .get(LoginViewModel.class);
    }

    private void onSignInResult(SignInResult signInResult) {
        if (signInResult == null) {
            return;
        }
        mDataBinding.loadingProgressBar.setVisibility(View.GONE);
        if (signInResult.getError() != null) {
            showMessage(signInResult.getError());
        }
        if (signInResult.getSuccess() != null) {
            updateUiWithUserSignIn(signInResult.getSuccess());
        }
        mLoginViewModel.onSignInResultCompleted();
    }

    private void onSignUpResult(SignUpResult signUpResult) {
        if (signUpResult == null) {
            return;
        }
        mDataBinding.loadingProgressBar.setVisibility(View.GONE);
        if (signUpResult.getError() != null) {
            showMessage(signUpResult.getError());
        }
        if (signUpResult.getSuccess() != null) {
            updateUiWithUserSignUp(signUpResult.getSuccess());
        }
        mLoginViewModel.onSignUpResultCompleted();
    }


    private void updateUiWithUserSignIn(SignInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
        goToSignInAttempts(model.getDisplayName());
    }

    private void goToSignInAttempts(String displayName) {
        Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToSignInAttemptsFragment(displayName));
    }

    private void updateUiWithUserSignUp(SignUpUserView model) {
        String welcome = getString(R.string.registered) + model.getDisplayName();
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showMessage(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    private void showMessage(String errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
