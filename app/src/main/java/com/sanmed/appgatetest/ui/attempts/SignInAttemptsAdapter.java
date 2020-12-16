package com.sanmed.appgatetest.ui.attempts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.sanmed.appgatetest.R;
import com.sanmed.appgatetest.databinding.ViewSignInAttemptsBinding;

public class SignInAttemptsAdapter extends ListAdapter<SignInAttemptsView,SignInAttemptsViewHolder> {
    protected SignInAttemptsAdapter(@NonNull DiffUtil.ItemCallback<SignInAttemptsView> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public SignInAttemptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewSignInAttemptsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_sign_in_attempts,parent,false);
        return new SignInAttemptsViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SignInAttemptsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
