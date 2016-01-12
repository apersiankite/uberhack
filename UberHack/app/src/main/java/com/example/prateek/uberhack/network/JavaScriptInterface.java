package com.example.prateek.uberhack.network;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.prateek.uberhack.listener.OnLoginSuccessListener;

/**
 * Created by Prateek on 12/01/16.
 */
public class JavaScriptInterface {

    OnLoginSuccessListener listener;

    public JavaScriptInterface(OnLoginSuccessListener listener){
        this.listener = listener;
    }

    @JavascriptInterface
    public void login(String accessToken) {
        Log.d("prateek","login function called with AccessToken : "+accessToken);
        if (listener != null) {
            listener.onLogin(accessToken);
        }
    }
}
