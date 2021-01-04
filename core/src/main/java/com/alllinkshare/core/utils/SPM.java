package com.alllinkshare.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.alllinkshare.core.ALS;
import static android.content.Context.MODE_PRIVATE;

public class SPM {
    private static final String TAG = "Utils/SPM";

    private static final String PREFS_NAME = "ALS";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String EXPIRES_IN = "expires_in";
    public static final String TOKEN_TYPE = "token_type";
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_STATUS = "user_status";

    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;
    private static SPM instance = null;

    public static synchronized SPM getInstance(){
        if(instance == null){
            instance = new SPM();
        }
        return instance;
    }

    private SPM(){
        sharedPreferences = ALS.getAppContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Log.d(TAG, "New instance created...");
    }

    public void save(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void save(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }


    public String get(String key, String defValule) {
        return sharedPreferences.getString(key, defValule);
    }

    public int get(String key, int defValule) {
        return sharedPreferences.getInt(key, defValule);
    }

    public boolean get(String key, boolean defValule) {
        return sharedPreferences.getBoolean(key, defValule);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    public void removeCredentials() {
        editor.remove(ACCESS_TOKEN);
        editor.remove(REFRESH_TOKEN);
        editor.remove(TOKEN_TYPE);
        editor.remove(EXPIRES_IN);
        editor.remove(USER_ID);
        editor.remove(USER_EMAIL);
        editor.remove(USER_STATUS);
        editor.apply();
    }
}