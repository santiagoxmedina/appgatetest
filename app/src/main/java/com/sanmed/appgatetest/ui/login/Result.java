package com.sanmed.appgatetest.ui.login;

import androidx.annotation.Nullable;

public class Result<T> {
    @Nullable
    private T success;
    @Nullable
    private Integer error;

    public Result(@Nullable Integer error) {
        this.error = error;
    }

    public  Result(@Nullable T success) {
        this.success = success;
    }

    @Nullable
    public T getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
