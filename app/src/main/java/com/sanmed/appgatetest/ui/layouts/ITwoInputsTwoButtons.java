package com.sanmed.appgatetest.ui.layouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface ITwoInputsTwoButtons {
     LiveData<String> getInputOneHint();
     LiveData<String> getInputTwoHint();
     LiveData<String> getInputOneErrorText();
     LiveData<String> getInputTwoErrorText();
     MutableLiveData<String> getInputOneText();
    MutableLiveData<String> getInputTwoText();
    LiveData<String> getButtonOneText();
    LiveData<String> getButtonTwoText();
    LiveData<Boolean> getButtonOneEnable();
    LiveData<Boolean> getButtonTwoEnable();
    void onOneButtonClick();
    void onTwoButtonClick();
    void setInputTwoErrorText(String errorText);

    void setInputOneErrorText(String errorText);

    void setFirstCondition(boolean enabled);

    void setSecondCondition(boolean enabled);

    void init();
}
