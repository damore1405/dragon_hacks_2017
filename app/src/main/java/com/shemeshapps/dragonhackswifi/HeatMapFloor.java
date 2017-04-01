package com.shemeshapps.dragonhackswifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.radius;
import static android.R.attr.x;
import static android.R.attr.y;

public class HeatMapFloor extends AppCompatActivity {


    WifiManager mWifiManager;
    BroadcastReceiver mWifiReceiver;
    List<ScanResult> wifiList;
    Map<Point,Integer> heatPoints = new HashMap<>();
    int currentRssi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map_floor);
        final DrawImageView image = (DrawImageView) findViewById(R.id.floorplan_image);
        final Button scan = (Button)findViewById(R.id.scan_now);
        image.setImageBitmap(PictureHelper.loadAndRotateBitmap(this,"floorplan_photo"));
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                image.drawPoint = new Point(Math.round(event.getX()),Math.round(event.getY()));
                image.invalidate();
                scan.setEnabled(true);
                return false;
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                currentRssi = mWifiManager.getConnectionInfo().getRssi();
            }
        };
        IntentFilter mIntentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mIntentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(mWifiReceiver, mIntentFilter);
        mWifiManager.startScan();
    }


    public void onPause() {
        unregisterReceiver(mWifiReceiver);
        super.onPause();
    }

    public void onResume() {
        registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

}
