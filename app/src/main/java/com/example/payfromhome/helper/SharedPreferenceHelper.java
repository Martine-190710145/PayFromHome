package com.example.payfromhome.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.payfromhome.model.User;

public class SharedPreferenceHelper {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public static final String PREFERENCE_NAME = "pay_from_home_pref";
    public static final String IS_LOGIN = "is_login";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String BALANCE = "balance";

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setRegister(String name, String username, String password) {
        editor.putString(NAME, name);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putInt(BALANCE, 1000000);
        editor.commit();
    }

    public void setIsLogin(boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public User getUser() {
        return new User(
                sharedPreferences.getString(NAME, ""),
                sharedPreferences.getString(USERNAME, ""),
                sharedPreferences.getString(PASSWORD, ""),
                sharedPreferences.getInt(BALANCE, 0)
        );
    }

    public int getBalance() {
        return sharedPreferences.getInt(BALANCE, 0);
    }

    public void setBalance(int balance) {
        editor.putInt(BALANCE, balance);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
