package com.shemeshapps.dragonhackswifi;

import android.animation.ArgbEvaluator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LiveScan extends AppCompatActivity {

    WifiManager mWifiManager;
    BroadcastReceiver mWifiReceiver;
    View scanbg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_scan);
        scanbg = (View)findViewById(R.id.background_scan);
        final TextView rssi = (TextView)findViewById(R.id.rssi_text);
        getSupportActionBar().setTitle("Live Scanner");

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                rssi.setText("RSSI = " + mWifiManager.getConnectionInfo().getRssi());
                float val = (mWifiManager.getConnectionInfo().getRssi() + 100);
                val = Math.max(val,10);
                val = Math.min(val,50);
                val = (val-10)/40.0f;
                update(val);
            }
        };
        IntentFilter mIntentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mIntentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(mWifiReceiver, mIntentFilter);
        mWifiManager.startScan();

    }

    private void update(float val) {

        final int colorStart = Color.parseColor("#ff0000");
        final int colorEnd = Color.parseColor("#00ff00");

        scanbg.setBackgroundColor(interpolateColor(colorStart, colorEnd, val)); // assuming SeekBar max is 100
    }

    private float interpolate(final float a, final float b,
                              final float proportion) {
        return (a + ((b - a) * proportion));
    }

    private int interpolateColor(final int a, final int b,
                                 final float proportion) {
        final float[] hsva = new float[3];
        final float[] hsvb = new float[3];
        Color.colorToHSV(a, hsva);
        Color.colorToHSV(b, hsvb);
        for (int i = 0; i < 3; i++) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }
        return Color.HSVToColor(hsvb);
    }



    public void onPause() {
        //unregisterReceiver(mWifiReceiver);
        super.onPause();
    }

    public void onResume() {
        //registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
}
