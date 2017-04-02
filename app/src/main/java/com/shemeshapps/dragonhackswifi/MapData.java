package com.shemeshapps.dragonhackswifi;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomer on 4/2/17.
 */


public class MapData implements Serializable{
    public String name;
    public String filename;
    public Map<dPoint,Integer> heatPoints = new HashMap<>();
    public int imagex;
    public int imagey;

}
