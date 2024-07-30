package com.reven.revenutil.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.util.reven.global.UtilApplication;

/**
 * Created by Reven
 * on 2022/3/1.
 */
public class WifiUtils
{
    /**
     * 检查wifi是否可用
     */
    public static boolean isWifiOpen()
    {
        WifiManager wifiManager = (WifiManager) UtilApplication.getContext()
                .getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        return null != wifiManager && wifiManager.isWifiEnabled();
    }

    public static boolean isWifiConnected()
    {
        if (isWifiOpen())
        {
            ConnectivityManager manager = (ConnectivityManager) UtilApplication.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null && wifiInfo.isConnected())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 模拟点击某个指定坐标作用在View上
     *
     * @param view
     * @param x
     * @param y
     */
    public void clickView(View view, float x, float y)
    {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(
                downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1;
        final MotionEvent upEvent = MotionEvent.obtain(
                downTime, downTime, MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }
}
