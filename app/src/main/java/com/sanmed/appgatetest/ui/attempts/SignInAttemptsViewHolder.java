package com.sanmed.appgatetest.ui.attempts;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanmed.appgatetest.databinding.ViewSignInAttemptsBinding;

public class SignInAttemptsViewHolder extends RecyclerView.ViewHolder {
    private final ViewSignInAttemptsBinding mBinding;
    public SignInAttemptsViewHolder(@NonNull View itemView, ViewSignInAttemptsBinding binding) {
        super(itemView);
        mBinding = binding;
    }

    public void bind(SignInAttemptsView item) {
        mBinding.setSignInAttempts(item);
        mBinding.executePendingBindings();
    }
}
