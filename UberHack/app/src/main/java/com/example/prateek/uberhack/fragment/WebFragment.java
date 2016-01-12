package com.example.prateek.uberhack.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.prateek.uberhack.R;
import com.example.prateek.uberhack.listener.OnLoginSuccessListener;
import com.example.prateek.uberhack.network.JavaScriptInterface;
import com.example.prateek.uberhack.util.IntentUtil;

/**
 * Created by Prateek on 12/01/16.
 */
public class WebFragment extends Fragment {

    private View rootLayout;
    private WebView webView = null;
    private View progressView;
    private OnLoginSuccessListener loginSuccessListener;

    public void setLoginSuccessListener(OnLoginSuccessListener loginSuccessListener) {
        this.loginSuccessListener = loginSuccessListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = inflater.inflate(R.layout.fragment_web, container, false);
        progressView = rootLayout.findViewById(R.id.layout_progress);

        webView = (WebView) rootLayout.findViewById(R.id.webView);
        webView.clearHistory();
        webView.addJavascriptInterface(new JavaScriptInterface(loginSuccessListener),"JSInterface");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("prateek","URL is : "+url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("prateek", "onPageFinished");
                progressView.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("prateek","onPageStarted");
                progressView.setVisibility(View.VISIBLE);
            }
        };
        webView.setWebViewClient(webViewClient);
        webView.requestFocus(View.FOCUS_DOWN);
        String uberLoginURL = getArguments().getString(IntentUtil.UBER_LOGIN_URL);
        webView.loadUrl(uberLoginURL);
        return rootLayout;
    }

}
