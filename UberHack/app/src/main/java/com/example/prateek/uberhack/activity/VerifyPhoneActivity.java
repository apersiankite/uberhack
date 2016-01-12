package com.example.prateek.uberhack.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.prateek.uberhack.R;
import com.example.prateek.uberhack.network.RequestManager;
import com.example.prateek.uberhack.util.ConstantUtils;
import com.example.prateek.uberhack.util.SharedPrefUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Prateek on 12/01/16.
 */
public class VerifyPhoneActivity extends AppCompatActivity {

    private EditText mEditPhone;
    private Button buttonSubmit;
    private View progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        mEditPhone = (EditText) findViewById(R.id.edit_phone);
        progressLayout = findViewById(R.id.layout_progress);
        progressLayout.setVisibility(View.GONE);
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mEditPhone.getText().toString();
                if (number == null || number.length() != 10) {
                    Toast.makeText(VerifyPhoneActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                startRequestToSendNumber(number);
            }
        });
    }

    private void startRequestToSendNumber(final String number) {
        StringRequest request = new StringRequest(Request.Method.POST, ConstantUtils.URL.VERIFY_PHONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(VerifyPhoneActivity.this,"Number Successfully saved",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Log.d("prateek", "error : " + error);
                Toast.makeText(VerifyPhoneActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", number);
                params.put("access_token", SharedPrefUtil.getInstance(VerifyPhoneActivity.this).getString(ConstantUtils.ACCESS_TOKEN));
                return params;
            }
        };
        RequestManager.getInstance(VerifyPhoneActivity.this).addRequest(request, "number");
        progressLayout.setVisibility(View.VISIBLE);
    }
}
