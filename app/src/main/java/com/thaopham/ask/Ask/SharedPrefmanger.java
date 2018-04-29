package com.thaopham.ask.Ask;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefmanger {
    private static final String SHARED_PREF_NAME ="simpliedshared";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_ID = "keyid";

    private static SharedPrefmanger mInstance;
    private static Context mCtx;

    private SharedPrefmanger(Context context){
        mCtx = context;
    }

    public  static synchronized SharedPrefmanger getmInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefmanger(context);
        }
        return mInstance;
    }
    //phuong thuc nay danh cho nguoi dung login
    //luu tru du lieu trong shared preferences
    public void userLogin(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }
    //phuong thuc nay kiem tra nguoi dung da dang nhap hay chua
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null)!= null;
    }

    //phuong thuc cho dang ky tai khoan
    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
    }
}
