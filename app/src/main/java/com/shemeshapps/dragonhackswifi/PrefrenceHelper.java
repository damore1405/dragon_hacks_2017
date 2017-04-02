package com.shemeshapps.dragonhackswifi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomer on 4/2/17.
 */

public class PrefrenceHelper {

    public static void putHeatMap(MapData m, Context c)
    {
        SharedPreferences  mPrefs = c.getSharedPreferences("map",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        try {

            prefsEditor.putString(m.filename, toString(m));
            prefsEditor.commit();
        } catch (Exception e) {
            System.out.println(e);
        }

        // deserialize the object
    }


    public static ArrayList<MapData> getMaps(Context c)
    {
        SharedPreferences  mPrefs = c.getSharedPreferences("map",Context.MODE_PRIVATE);

        Map<String,String> keys = (Map<String,String>)mPrefs.getAll();
        ArrayList<MapData> maps = new ArrayList<>();

        for(Map.Entry<String,String> entry : keys.entrySet()){
            try {

                MapData obj = (MapData) fromString(entry.getValue());
                maps.add(obj);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return maps;
    }

    private static Object fromString( String s ) throws IOException,
            ClassNotFoundException {
        byte [] data = Base64.decode( s ,Base64.NO_WRAP);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.encodeToString(baos.toByteArray(),Base64.NO_WRAP);
    }
}
