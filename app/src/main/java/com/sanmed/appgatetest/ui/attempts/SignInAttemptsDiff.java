package com.sanmed.appgatetest.ui.attempts;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class SignInAttemptsDiff extends DiffUtil.ItemCallback<SignInAttemptsView> {
    @Override
    public boolean areItemsTheSame(@NonNull SignInAttemptsView oldItem, @NonNull SignInAttemptsView newItem) {
        return oldItem.date.equals(newItem.date);
    }

    @Override
    public boolean areContentsTheSame(@NonNull SignInAttemptsView oldItem, @NonNull SignInAttemptsView newItem) {
        return oldItem.success == newItem.success;
    }
}
