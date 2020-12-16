package com.sanmed.appgatetest.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import com.sanmed.appgatetest.data.model.SignInAttempt;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class LocalSecurity {
    private final String key = "AppGate";
    SharedPreferences sharedPreferences;
    DataSerializer mDataSerializer;

    public LocalSecurity(Context context) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        mDataSerializer = new DataSerializer();
    }

    public boolean saveUser(String username, String password) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username, password);
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e("LocalSecurity", "saveUser", ex);
            return false;
        }
    }

    public boolean loadUser(String username, String password) {
        String pass = sharedPreferences.getString(username, null);
        return password.equals(pass);
    }

    public List<SignInAttempt> loadSignInAttempts(String userId) {
        String key = getKey(userId);
        String jsonData = sharedPreferences.getString(key,"");
        SignInAttempt[] data = mDataSerializer.getObject(jsonData, SignInAttempt[].class);
        if(data != null){
            return Arrays.asList(data);

        }else{
            return new ArrayList<>();
        }
    }

    public boolean saveSignInAttempt(String userId,String date,boolean success) {
        try {
            String key = getKey(userId);
            List<SignInAttempt> newData = new ArrayList<>();
            newData.add(new SignInAttempt(date,success));
            newData.addAll(loadSignInAttempts(userId));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, mDataSerializer.getData(newData));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e("LocalSecurity", "saveSignInAttempt", ex);
            return false;
        }
    }

    @NotNull
    private String getKey(String userId) {
        return userId+"attempts";
    }
}
