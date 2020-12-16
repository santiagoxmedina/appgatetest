package com.sanmed.appgatetest.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sanmed.appgatetest.data.model.SignInAttempt;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafeStorage implements ISafeStorage {
    private final String key = "AppGate";
    SharedPreferences sharedPreferences;
    IDataSerializer mDataSerializer;

    public SafeStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        mDataSerializer = new DataSerializer();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean saveUser(String username, String password) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String usermd5=md5(username);
            editor.putString(usermd5, md5(password));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e("SafeStorage", "saveUser", ex);
            return false;
        }
    }

    public boolean loadUser(String username, String password) {
        String pass = sharedPreferences.getString(md5(username), null);
        return md5(password).equals(pass);
    }

    public List<SignInAttempt> loadSignInAttempts(String userId) {
        String key = getAttemptsKey(userId);
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
            String key = getAttemptsKey(userId);
            List<SignInAttempt> newData = new ArrayList<>();
            newData.add(new SignInAttempt(date,success));
            newData.addAll(loadSignInAttempts(userId));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, mDataSerializer.getData(newData));
            editor.apply();
            return true;
        } catch (Exception ex) {
            Log.e("SafeStorage", "saveSignInAttempt", ex);
            return false;
        }
    }

    @Override
    public boolean isNewUser(String username) {
        return  sharedPreferences.getString(md5(username),"").equals("");
    }

    @NotNull
    private String getAttemptsKey(String userId) {
        return md5(userId+"attempts");
    }


}
