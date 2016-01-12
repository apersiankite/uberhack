package com.example.prateek.uberhack.application;

import android.app.Application;

import com.example.prateek.uberhack.network.RequestManager;

/**
 * Created by Prateek on 12/01/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance(getApplicationContext());
    }
}
