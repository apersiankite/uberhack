package com.example.prateek.uberhack.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.prateek.uberhack.R;
import com.example.prateek.uberhack.util.AppUtil;
import com.example.prateek.uberhack.util.ConstantUtils;

/**
 * Created by Prateek on 12/01/16.
 */
public class GPSTrackerActivity extends AppCompatActivity implements LocationListener{

    private LocationManager mLocationManager;
    private View progressView;
    private String uberProductType = "Any";
    private final String phoneNumber = "+14134183825";
//    private final String phoneNumber = "+918096781948";     //testing


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        if(AppUtil.getNullCheck(getIntent().getStringExtra(ConstantUtils.UBER_PRODUCT))) {
            uberProductType = getIntent().getStringExtra(ConstantUtils.UBER_PRODUCT);
        }
        progressView = findViewById(R.id.layout_progress);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Disabled");
            builder.setMessage("Kindly enable GPS through settings");
            builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(gpsOptionsIntent);
                }
            });
            builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();
        }else{
            progressView.setVisibility(View.VISIBLE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, this);
            Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation != null){
                checkValidLocation(lastKnownLocation);
            }
        }
    }

    private void checkValidLocation(Location location){
        float locationAccuracy = location.getAccuracy();
        if(locationAccuracy > 30.0F){
            Toast.makeText(GPSTrackerActivity.this,"Location received is not accurate enough. Kindly change your location",Toast.LENGTH_LONG).show();
            return;
        }
        sendSMS(location.getLatitude(),location.getLongitude());
        progressView.setVisibility(View.GONE);
    }

    private void sendSMS(double lat, double lng){
        Uri uri = Uri.parse("smsto:"+phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Type:book,Lat:"+lat+",Lng:"+lng+",Product:"+uberProductType);
        startActivity(it);
        mLocationManager.removeUpdates(this);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("prateek", "location received : "+location.getAccuracy()+" ,provider: "+location.getProvider());
        checkValidLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

