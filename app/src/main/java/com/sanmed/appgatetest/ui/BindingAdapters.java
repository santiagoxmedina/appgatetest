package com.sanmed.appgatetest.ui;

import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.sanmed.appgatetest.R;

public class BindingAdapters {

    @BindingAdapter("setError")
    public static void setError(EditText view, String errorText) {
            view.setError(errorText);
    }

    @BindingAdapter("setSuccess")
    public static void setSuccess(TextView view, boolean success) {
        if(success){
            view.setText(view.getResources().getString(R.string.success));
            view.setTextColor(view.getResources().getColor(android.R.color.holo_green_dark));
        }else{
            view.setText(view.getResources().getString(R.string.fail));
            view.setTextColor(view.getResources().getColor(android.R.color.holo_red_dark));
        }
    }

}
