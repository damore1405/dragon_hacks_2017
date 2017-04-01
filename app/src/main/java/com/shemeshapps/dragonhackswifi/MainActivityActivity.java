package com.shemeshapps.dragonhackswifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        final TextView textview = (TextView)findViewById(R.id.refreshtext);
        Button update = (Button)findViewById(R.id.update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mWifiManager.
                mWifiManager.startScan();
            }
        });
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!mWifiManager.isWifiEnabled()) {
            // If wifi disabled then enable it
            Toast.makeText(this, "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();
            mWifiManager.setWifiEnabled(true);
        }
        mWifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int numberOfLevels=5;

                WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
                int level=WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
                int rssi = mWifiManager.getConnectionInfo().getRssi();

                //Log.i("WIFI","rssi =" +rssi);
                textview.setText("Level=" + rssi);

            }
        };
        IntentFilter mIntentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mIntentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(mWifiReceiver, mIntentFilter);
        mWifiManager.startScan();

    }



    WifiManager mWifiManager;
    BroadcastReceiver mWifiReceiver;
    List<ScanResult> wifiList;


    public void onPause() {
        unregisterReceiver(mWifiReceiver);
        super.onPause();
    }

    public void onResume() {
        registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }



}
