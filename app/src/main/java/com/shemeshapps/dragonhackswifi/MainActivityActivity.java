package com.shemeshapps.dragonhackswifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        getSupportActionBar().setTitle("My Heatmaps");

        Button newheatmap = (Button)findViewById(R.id.newheatmap);
        newheatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityActivity.this,CreateHeatMap.class));
            }
        });

        Button livescan = (Button)findViewById(R.id.livescan_button);
        livescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityActivity.this,LiveScan.class));
            }
        });

        final ArrayList<MapData> past = PrefrenceHelper.getMaps(this);
        listAdapter adapter = new listAdapter(this,past);
        ListView list = (ListView)findViewById(R.id.heatmaplist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivityActivity.this,HeatMapFloor.class);
                i.putExtra("data",past.get(position));
                startActivity(i);
            }
        });
    }


}
