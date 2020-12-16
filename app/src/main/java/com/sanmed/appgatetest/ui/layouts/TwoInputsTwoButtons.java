package com.sanmed.appgatetest.ui.layouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.sanmed.appgatetest.ui.IAction;

public class TwoInputsTwoButtons implements ITwoInputsTwoButtons {

    private final MutableLiveData<String> inputOneHintMutableLiveData;
    private final MutableLiveData<String> inputTwoHintMutableLiveData;
    private final MutableLiveData<String> inputOneTextMutableLiveData;
    private final MutableLiveData<String> inputTwoTextMutableLiveData;
    private final MutableLiveData<String> buttonOneTextMutableLiveData;
    private final MutableLiveData<String> buttonTwoTextMutableLiveData;
    private final MutableLiveData<String> inputOneErrorTextMutableLiveData;
    private final MutableLiveData<String> inputTwoErrorTextMutableLiveData;
    private final MediatorLiveData<Boolean> buttonOneEnableMediatorLiveData;
    private final MediatorLiveData<Boolean> buttonTwoEnableMediatorLiveData;
    private final MutableLiveData<Boolean> firstConditionMutableLiveData;
    private final MutableLiveData<Boolean> secondConditionMutableLiveData;
    private final IAction mButtonOneAction;
    private final IAction mButtonTwoAction;
    private boolean initialized;
    public TwoInputsTwoButtons(String inputOneHintText, String inputTwoHintTwo, String buttonOneText, String buttonTwoText, IAction buttonOneAction, IAction buttonTwoAction){
        inputOneHintMutableLiveData = new MutableLiveData<>(inputOneHintText);
        inputTwoHintMutableLiveData = new MutableLiveData<>(inputTwoHintTwo);
        inputOneTextMutableLiveData = new MutableLiveData<>("");
        inputTwoTextMutableLiveData = new MutableLiveData<>("");
        buttonOneTextMutableLiveData = new MutableLiveData<>(buttonOneText);
        buttonTwoTextMutableLiveData = new MutableLiveData<>(buttonTwoText);
        inputOneErrorTextMutableLiveData = new MutableLiveData<>();
        inputTwoErrorTextMutableLiveData = new MutableLiveData<>();
        buttonOneEnableMediatorLiveData = new MediatorLiveData<>();
        buttonTwoEnableMediatorLiveData = new MediatorLiveData<>();
        firstConditionMutableLiveData = new MutableLiveData<>(false);
        secondConditionMutableLiveData = new MutableLiveData<>(false);
        mButtonOneAction = buttonOneAction;
        mButtonTwoAction = buttonTwoAction;
        initialized = false;
    }

    public void init(){
        if(!initialized){
            initialized = true;
            buttonOneEnableMediatorLiveData.addSource(firstConditionMutableLiveData,(x)-> buttonOneEnableMediatorLiveData.setValue(firstConditionMutableLiveData.getValue() && secondConditionMutableLiveData.getValue()));
            buttonOneEnableMediatorLiveData.addSource(secondConditionMutableLiveData,(x)-> buttonOneEnableMediatorLiveData.setValue(firstConditionMutableLiveData.getValue() && secondConditionMutableLiveData.getValue()));
            buttonTwoEnableMediatorLiveData.addSource(firstConditionMutableLiveData,(x)-> buttonTwoEnableMediatorLiveData.setValue(firstConditionMutableLiveData.getValue() && secondConditionMutableLiveData.getValue()));
            buttonTwoEnableMediatorLiveData.addSource(secondConditionMutableLiveData,(x)-> buttonTwoEnableMediatorLiveData.setValue(firstConditionMutableLiveData.getValue() && secondConditionMutableLiveData.getValue()));
        }
    }

    @Override
    public LiveData<String> getInputOneHint() {
        return inputOneHintMutableLiveData;
    }

    @Override
    public LiveData<String> getInputTwoHint() {
        return inputTwoHintMutableLiveData;
    }

    @Override
    public LiveData<String> getInputOneErrorText() {
        return inputOneErrorTextMutableLiveData;
    }

    @Override
    public LiveData<String> getInputTwoErrorText() {
        return inputTwoErrorTextMutableLiveData;
    }

    @Override
    public MutableLiveData<String> getInputOneText() {
        return inputOneTextMutableLiveData;
    }

    @Override
    public MutableLiveData<String> getInputTwoText() {
        return inputTwoTextMutableLiveData;
    }

    @Override
    public LiveData<String> getButtonOneText() {
        return buttonOneTextMutableLiveData;
    }

    @Override
    public LiveData<String> getButtonTwoText() {
        return buttonTwoTextMutableLiveData;
    }

    @Override
    public LiveData<Boolean> getButtonOneEnable() {
        return buttonOneEnableMediatorLiveData;
    }

    @Override
    public LiveData<Boolean> getButtonTwoEnable() {
        return buttonTwoEnableMediatorLiveData;
    }

    @Override
    public void onOneButtonClick() {
        if(mButtonOneAction!=null){
            mButtonOneAction.onAction();
        }
    }

    @Override
    public void onTwoButtonClick() {
        if(mButtonTwoAction!=null){
            mButtonTwoAction.onAction();
        }
    }

    @Override
    public void setInputTwoErrorText(String errorText) {
        inputTwoErrorTextMutableLiveData.setValue(errorText);
    }

    @Override
    public void setInputOneErrorText(String errorText) {
        inputOneErrorTextMutableLiveData.setValue(errorText);
    }

    @Override
    public void setFirstCondition(boolean enabled) {
        firstConditionMutableLiveData.setValue(enabled);
    }

    @Override
    public void setSecondCondition(boolean enabled) {
        secondConditionMutableLiveData.setValue(enabled);
    }
}
