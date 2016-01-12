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
import com.example.prateek.uberhack.util.IntentUtil;

/**
 * Created by Prateek on 12/01/16.
 */
public class WebFragment extends Fragment {

    private View rootLayout;
    private WebView webView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = inflater.inflate(R.layout.fragment_web,container,false);
        webView = (WebView) rootLayout.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("prateek","URL is : "+url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        };
        webView.setWebViewClient(webViewClient);
        webView.requestFocus(View.FOCUS_DOWN);
        String uberLoginURL = getArguments().getString(IntentUtil.UBER_LOGIN_URL);
        webView.loadUrl(uberLoginURL);
        return rootLayout;
    }

}
