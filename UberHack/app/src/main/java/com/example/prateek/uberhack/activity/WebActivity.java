package com.example.prateek.uberhack.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.prateek.uberhack.R;
import com.example.prateek.uberhack.fragment.WebFragment;
import com.example.prateek.uberhack.util.ConstantUtils;
import com.example.prateek.uberhack.util.IntentUtil;

/**
 * Created by Prateek on 12/01/16.
 */
public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebFragment webFragment = new WebFragment();
        Bundle b = new Bundle();
        String ur = "https://login.uber.com/oauth/v2/authorize?response_type=code&client_id="+ ConstantUtils.App.CLIENT_ID+"&redirect_uri=localhost:80";
        b.putString(IntentUtil.UBER_LOGIN_URL, ur);
        webFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, webFragment).commit();
    }
}
