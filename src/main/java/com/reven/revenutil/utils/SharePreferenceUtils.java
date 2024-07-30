package com.reven.revenutil.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.LogUtils;
import com.util.reven.global.UtilApplication;


public class SharePreferenceUtils
{
    private static SharePreferenceUtils sSharePreferenceUtils = null;
    private final String PREFERENCE_NAME = "NoboAuto";
    /**
     * 连接过得MAC地址
     */
    private final String MDONETOBED = "pre_do_to_bed";
    private final String PRE_TURN_SEAT_NUM = "pre_turn_seat";

    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;

    /**
     * 构造函数
     *
     * @param context 当前应用的Context
     */
    private SharePreferenceUtils(Context context)
    {
        mSharedPreferences = UtilApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取SharePreferenceUtils类唯一实例
     *
     * @param context 当前应用的Context
     * @return SharePreferenceUtils类唯一实例
     */
    public static SharePreferenceUtils getInstance(Context context)
    {
        if (sSharePreferenceUtils == null)
        {
            synchronized (SharePreferenceUtils.class)
            {
                if (sSharePreferenceUtils == null)
                {
                    sSharePreferenceUtils = new SharePreferenceUtils(context);
                }
            }
        }

        return sSharePreferenceUtils;
    }

    /**
     * 获取上次是否做了成床
     *
     * @return mac
     */
    public boolean getDoneToBed()
    {
        return mSharedPreferences.getBoolean(MDONETOBED, false);
    }

    public void setDoneToBed(boolean tobed)
    {
        LogUtils.d("保存做过成床");
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MDONETOBED, tobed);
        mEditor.commit();
    }
    /**
     * 获取上次旋转座椅
     *
     * @return
     */
    public int getPreTurnSeat()
    {
        return mSharedPreferences.getInt(PRE_TURN_SEAT_NUM, 0);
    }

    public void setPreSeatNum(int preSeatNum)
    {
        LogUtils.d("保存做过成床");
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(PRE_TURN_SEAT_NUM, preSeatNum);
        mEditor.commit();
    }
}