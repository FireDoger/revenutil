package com.reven.revenutil.utils;

import android.graphics.Color;
import android.util.Log;


/**
 * Created by Reven
 * on 2022/4/20.
 */
public class ColorUtils
{
    /**
     * rgb to int
     *
     * @param red
     * @param green
     * @param blue
     * @return
     */
    public static int rgbToInt(int red, int green, int blue)
    {
        return Color.rgb(red, green, blue);
    }

    /**
     * argb to int 含透明度
     *
     * @param alpha
     * @param red
     * @param green
     * @param blue
     * @return
     */
    public static int argbToInt(int alpha, int red, int green, int blue)
    {
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * intcolor转rgb
     * @param color
     */
    public static void intTorgb(int color)
    {
        int alpha = color >>> 24;
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        Log.d("miss",alpha + "_" + red + "_" + green + "_" + blue);
    }

    public static int sixteenToColorInt(String colorId)
    {
        return Color.parseColor(colorId);
    }
}
