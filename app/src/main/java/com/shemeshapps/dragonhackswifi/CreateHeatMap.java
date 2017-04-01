package com.shemeshapps.dragonhackswifi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateHeatMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_heat_map);
        PictureHelper.getPicture(CreateHeatMap.this, "floorplan_photo");
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == PictureHelper.PICTURE_CHOSEN)
            {
                if(data!=null)
                {
                    PictureHelper.loadBitmapFromIntent(data,"floorplan_photo",this);
                    Intent i = new Intent(this, HeatMapFloor.class);
                    i.putExtra("filename","floorplan_photo");
                    i.putExtra("source","chosen");
                    startActivity(i);
                }
            }
        }


    }
}
