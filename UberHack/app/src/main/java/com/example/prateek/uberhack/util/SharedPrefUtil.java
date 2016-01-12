package com.example.prateek.uberhack.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Prateek on 12/01/16.
 */
public class SharedPrefUtil {

    private static SharedPrefUtil mInstance;
    private static final String PREF_NAME = "";
    private Context mContext;
    private SharedPreferences mSharedPref;

    public static synchronized SharedPrefUtil getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefUtil(context);
        }
        return mInstance;
    }

    private SharedPrefUtil(Context context){
        mContext = context;
        mSharedPref = context.getSharedPreferences(PREF_NAME,0);
    }

    public String getString(String key){
        return mSharedPref.getString(key,null);
    }

    public void saveString(String key, String value){
        mSharedPref.edit().putString(key,value).apply();
    }
}
