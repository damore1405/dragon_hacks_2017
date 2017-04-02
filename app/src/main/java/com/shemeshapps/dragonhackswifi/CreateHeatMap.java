package com.shemeshapps.dragonhackswifi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CreateHeatMap extends AppCompatActivity {

    TextView path;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_heat_map);
        Button upload = (Button)findViewById(R.id.upload_button);
        getSupportActionBar().setTitle("Create New Heatmap");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureHelper.getPicture(CreateHeatMap.this);
            }
        });
        path = (TextView)findViewById(R.id.upload_path);

        Button next = (Button)findViewById(R.id.next_button);
        final EditText name = (EditText)findViewById(R.id.heatmapName);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateHeatMap.this, HeatMapFloor.class);
                i.putExtra("filename",filename);
                i.putExtra("heatmapName",name.getText().toString());
                startActivityForResult(i,1);
            }
        });
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
                    filename = Long.toString(System.currentTimeMillis());
                    PictureHelper.loadBitmapFromIntent(data,filename,this);
                    path.setText("Uploaded!");
                }
            }
            if(requestCode ==1)
            {
                finish();
            }
        }


    }
}
