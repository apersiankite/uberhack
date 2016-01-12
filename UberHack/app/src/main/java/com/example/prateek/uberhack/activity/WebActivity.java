package com.example.prateek.uberhack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.prateek.uberhack.R;
import com.example.prateek.uberhack.fragment.WebFragment;
import com.example.prateek.uberhack.listener.OnLoginSuccessListener;
import com.example.prateek.uberhack.util.AppUtil;
import com.example.prateek.uberhack.util.ConstantUtils;
import com.example.prateek.uberhack.util.IntentUtil;
import com.example.prateek.uberhack.util.SharedPrefUtil;

/**
 * Created by Prateek on 12/01/16.
 */
public class WebActivity extends AppCompatActivity implements OnLoginSuccessListener {

    private static final int REQ_CODE_VERIFY_PHONE = 1100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebFragment webFragment = new WebFragment();
        webFragment.setLoginSuccessListener(this);
        Bundle b = new Bundle();
        String ur = "https://login.uber.com/oauth/v2/authorize?response_type=code&client_id=" + ConstantUtils.App.CLIENT_ID + "&redirect_uri=https://frozen-bayou-9116.herokuapp.com/uber/callback";
        b.putString(IntentUtil.UBER_LOGIN_URL, ur);
        webFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, webFragment).commit();
    }

    @Override
    public void onLogin(String accessToken) {
        Log.d("prateek", "onLogin webActivity : " + accessToken);
        /*if (AppUtil.getNullCheck(accessToken)) {
            SharedPrefUtil.getInstance(this).saveString(ConstantUtils.ACCESS_TOKEN, accessToken);
            startActivityForResult(new Intent(WebActivity.this, VerifyPhoneActivity.class), REQ_CODE_VERIFY_PHONE);
        }*/

        if(AppUtil.getNullCheck(accessToken)){
            SharedPrefUtil.getInstance(this).saveString(ConstantUtils.ACCESS_TOKEN, accessToken);
            setResult(RESULT_OK);
        }else{
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_VERIFY_PHONE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
