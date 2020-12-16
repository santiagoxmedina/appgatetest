package com.sanmed.appgatetest.ui.attempts;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sanmed.appgatetest.R;
import com.sanmed.appgatetest.databinding.FragmentSignInAttemptsBinding;
import com.sanmed.appgatetest.ui.animation.SlideUpItemAnimator;

import java.util.List;

public class SignInAttemptsFragment extends Fragment {

    private SignInAttemptsViewModel mViewModel;
    private FragmentSignInAttemptsBinding mBinding;
    private SignInAttemptsAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in_attempts,container,false);
        initViewModel();
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setItemAnimator(new SlideUpItemAnimator());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewComponents();
        initSubscribers();
        mViewModel.init();
    }

    private void initViewComponents() {
        mAdapter = new SignInAttemptsAdapter(new SignInAttemptsDiff());
        mBinding.attemptsRecyclerView.setAdapter(mAdapter);
    }

    private void initSubscribers() {
        mViewModel.getSignInAttempts().observe(getViewLifecycleOwner(),this::onSignInAttemptsChange);
    }

    private void onSignInAttemptsChange(SignInAttemptsResult signInAttemptsResult) {
        if(signInAttemptsResult!=null){
            if (signInAttemptsResult.getError() != null) {
                showMessage(signInAttemptsResult.getError());
            }
            if (signInAttemptsResult.getSuccess() != null) {
                updateUiWithSignInAttempts(signInAttemptsResult.getSuccess());
            }
        }
    }

    private void updateUiWithSignInAttempts(List<SignInAttemptsView> success) {
        mAdapter.submitList(success);
    }

    private void showMessage(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void initViewModel() {
        SignInAttemptsViewModelFactory factory = new SignInAttemptsViewModelFactory(requireActivity().getApplication(),getUserId());
        mViewModel = new ViewModelProvider(this,factory).get(SignInAttemptsViewModel.class);
    }

    private String getUserId() {
        return SignInAttemptsFragmentArgs.fromBundle(requireArguments()).getUserId();
    }

}