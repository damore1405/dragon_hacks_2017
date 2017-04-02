package com.shemeshapps.dragonhackswifi;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ca.hss.heatmaplib.HeatMap;


public class HeatMapFloor extends AppCompatActivity {


    WifiManager mWifiManager;
    boolean finished = false;
    BroadcastReceiver mWifiReceiver;
    Map<dPoint,Integer> heatPoints = new HashMap<>();
    int currentRssi = 0;
    DrawImageView image;
    int imagex;
    int imagey;
    String filename;
    String heatname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map_floor);
        image = (DrawImageView) findViewById(R.id.floorplan_image);
        final Button scan = (Button)findViewById(R.id.scan_now);

        MapData d = (MapData)getIntent().getSerializableExtra("data");

        if(d!=null)
        {
           filename = d.filename;
           heatname = d.name;
            imagex = d.imagex;
            imagey = d.imagey;
            heatPoints = d.heatPoints;
        }
        else
        {
            filename = getIntent().getStringExtra("filename");
            heatname = getIntent().getStringExtra("heatmapName");
        }

        getSupportActionBar().setTitle(heatname);



        image.setImageBitmap(PictureHelper.loadAndRotateBitmap(this,filename));
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!finished)
                {
                    image.drawPoint = new dPoint(Math.round(event.getX()),Math.round(event.getY()));
                    image.invalidate();
                    scan.setEnabled(true);
                }
                return false;
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog =
                        ProgressDialog.show(HeatMapFloor.this, "Scanning...", "Task in progress...");
                Handler ha= new Handler();
                ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        heatPoints.put(image.drawPoint,currentRssi);
                        image.pastPoints.add(image.drawPoint);
                        image.drawPoint = null;
                        scan.setEnabled(false);
                        image.invalidate();
                    }
                }, 5000);

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

        final Button createButton = (Button)findViewById(R.id.finish_button);
        final HeatMap heatMap = (HeatMap) findViewById(R.id.heatmap);
        heatMap.setMinimum(10.0);
        heatMap.setMaximum(50.0);
        heatMap.setRadius(750);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!finished)
                {
                    setResult(RESULT_OK);
                    image.drawPoint = null;
                    finished = true;
                    image.pastPoints.clear();
                    image.invalidate();
                    if(imagex == 0)
                    {
                         imagex = image.getWidth();
                        imagey = image.getHeight();
                    }
                    for(Map.Entry<dPoint,Integer> heat:heatPoints.entrySet())
                    {
                        HeatMap.DataPoint point = new HeatMap.DataPoint(heat.getKey().x/(float)imagex, heat.getKey().y/(float)imagey, (heat.getValue() +  100.0));
                        heatMap.addData(point);
                    }


                    heatMap.forceRefresh();
                    MapData m = new MapData();
                    m.filename = filename;
                    m.name = heatname;
                    m.heatPoints.putAll(heatPoints);
                    m.imagex = imagex;
                    m.imagey = imagey;
                    PrefrenceHelper.putHeatMap(m,HeatMapFloor.this);
                    createButton.setText("Find Optimal Router Placement");
                }
                else
                {
                    Intent i = new Intent(HeatMapFloor.this,RouterFinder.class);
                    MapData m = new MapData();
                    m.filename = filename;
                    m.name = heatname;
                    m.heatPoints.putAll(heatPoints);
                    m.imagex = imagex;
                    m.imagey = imagey;
                    i.putExtra("data",m);
                    startActivity(i);
                }

            }
        });


        if(d!=null)
        {
            createButton.callOnClick();
        }
        else
        {
            Random rand = new Random();
            for (int i = 0; i < 20; i++) {
                heatPoints.put(new dPoint(Math.round(rand.nextFloat()*720), Math.round(rand.nextFloat()*720)), (int)Math.round(rand.nextDouble() * -100.0));
            }
        }

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
