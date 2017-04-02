package com.shemeshapps.dragonhackswifi;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Map;

import ca.hss.heatmaplib.HeatMap;

public class RouterFinder extends AppCompatActivity {

    boolean done = false;
    DrawImageView tapper;
    MapData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_finder);
        getSupportActionBar().setTitle("Router Locator");

        ImageView image = (ImageView) findViewById(R.id.floorplan_image2);
        data = (MapData)getIntent().getSerializableExtra("data");
        image.setImageBitmap(PictureHelper.loadAndRotateBitmap(this,data.filename));

        tapper = (DrawImageView)findViewById(R.id.add_frequent);
        tapper.setcolor(getResources().getColor(android.R.color.holo_blue_light));

        tapper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!done)
                {
                    tapper.pastPoints.add(new dPoint(Math.round(event.getX()),Math.round(event.getY())));
                    tapper.invalidate();
                }

                return false;
            }
        });

        final HeatMap heatMap = (HeatMap) findViewById(R.id.heatmap2);
        heatMap.setMinimum(10.0);
        heatMap.setMaximum(50.0);
        heatMap.setRadius(750);

        for(Map.Entry<dPoint,Integer> heat:data.heatPoints.entrySet())
        {
            HeatMap.DataPoint point = new HeatMap.DataPoint(heat.getKey().x/(float)data.imagex, heat.getKey().y/(float)data.imagey, (heat.getValue() +  100.0));
            heatMap.addData(point);
        }

        Button calc = (Button)findViewById(R.id.finish_button);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouterLoc();
                tapper.invalidate();
                done = true;
            }
        });

    }

    public void getRouterLoc()
    {
        float xtot = 0;
        float ytot = 0;
        float xw = 0;
        for(Map.Entry<dPoint,Integer> heat:data.heatPoints.entrySet())
        {
            float val = (heat.getValue()+ 100);
            val = Math.max(val,10);
            val = Math.min(val,50);
            val = (val-10)/40.0f;
            xtot += heat.getKey().x * (1.25-val);
            ytot += heat.getKey().y * (1.25-val);
            xw += (1.25-val);
        }

        for(dPoint p:tapper.pastPoints)
        {
            xtot += p.x;
            ytot += p.y;
            xw += 1;
        }
        dPoint avg = new dPoint(Math.round(xtot/xw),Math.round(ytot/xw));
        tapper.routerLoc = avg;
    }
}
