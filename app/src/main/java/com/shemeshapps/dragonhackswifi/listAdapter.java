package com.shemeshapps.dragonhackswifi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomer on 4/2/17.
 */

public class listAdapter   extends ArrayAdapter<MapData> {

    Context c;
    List<MapData> data = new ArrayList<>();
    public listAdapter(Context c, ArrayList<MapData> entries)
    {
        super(c, R.layout.maprow,entries);
        this.c = c;
        data.addAll(entries);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.maprow, null);
        }
        TextView name = (TextView)convertView.findViewById(R.id.row_name);
        ImageView pic = (ImageView)convertView.findViewById(R.id.row_pic);
        name.setText(data.get(position).name);
        pic.setImageBitmap(PictureHelper.loadAndRotateBitmap(c,data.get(position).filename));

        return convertView;
    }
}
