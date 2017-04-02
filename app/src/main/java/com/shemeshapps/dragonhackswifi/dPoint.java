package com.shemeshapps.dragonhackswifi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by tomer on 4/2/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class dPoint implements Serializable{
    public dPoint(int x, int y)
    {
        this.x =x;
        this.y = y;
    }
    public int x;
    public int y;
}
