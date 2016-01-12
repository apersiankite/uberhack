package com.example.prateek.uberhack.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.prateek.uberhack.R;
import com.example.prateek.uberhack.util.AppUtil;
import com.example.prateek.uberhack.util.ConstantUtils;
import com.example.prateek.uberhack.util.SharedPrefUtil;

/**
 * Created by Prateek on 12/01/16.
 */
public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_CODE_LOGIN = 101;
    private Button buttonBook;
    private View uberProductLayout;
    private Spinner uberProductSpinner;
    private String uberProduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        uberProductLayout = findViewById(R.id.uber_product_layout);
        uberProductSpinner = (Spinner) findViewById(R.id.uber_product_spinner);
        uberProductSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uberProduct = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonBook = (Button) findViewById(R.id.button_book);
        buttonBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_book) {
            if (((Button) v).getText().toString().equalsIgnoreCase("login")) {
                startActivityForResult(new Intent(LauncherActivity.this, WebActivity.class), REQ_CODE_LOGIN);
            } else {
                Intent in = new Intent(LauncherActivity.this, GPSTrackerActivity.class);
                in.putExtra(ConstantUtils.UBER_PRODUCT,uberProduct);
                startActivity(in);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForAccessToken();
    }

    private void checkForAccessToken(){
        if (!AppUtil.getNullCheck(SharedPrefUtil.getInstance(LauncherActivity.this).getString(ConstantUtils.ACCESS_TOKEN))) {
            buttonBook.setText("Login");
            uberProductLayout.setVisibility(View.GONE);
        } else {
            buttonBook.setText("Book Uber");
            uberProductLayout.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK){
            checkForAccessToken();
        }
    }
}
