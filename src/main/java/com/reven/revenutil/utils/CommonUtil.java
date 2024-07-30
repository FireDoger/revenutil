package com.reven.revenutil.utils;

import android.widget.CheckBox;

import com.reven.revenutil.ui.views.ControlCheckbox;

import java.util.List;
import java.util.Locale;

/**
 * Created by Reven
 * on 2022/6/22.
 */
public class CommonUtil
{
    //高位在前，低位在后
    public static byte[] int2bytes(int num)
    {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);//说明一
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) ((num >>> 0) & 0xff);
        return result;
    }

    //高位在前，低位在后
    public static int bytes2int(byte[] bytes)
    {
        int result = 0;
        if (bytes.length == 4)
        {
            int a = (bytes[0] & 0xff) << 24;//说明二
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

    /**
     * @return返回微秒
     */
    public static Long getmicTime()
    {
        Long cutime = System.currentTimeMillis() * 1000; // 微秒
        Long nanoTime = System.nanoTime(); // 纳秒
        return cutime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }

    /**
     * 获取长度为8的时间戳字节数组
     *
     * @param time
     * @return
     */
    public static byte[] long2Byte(long time)
    {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++)
        {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((time >> offset) & 0xff);
        }
        return buffer;
    }

    public static long byte2Long(byte[] buffer)
    {
        long value = 0;

        for (int i = 0; i < 8; i++)
        {
            value <<= 8;
            value |= buffer[i] & 0xff;
        }

        return value;
    }

    /**
     * BCC校验(异或校验)
     *
     * @param data
     * @return
     */
    public static byte getBCC(byte[] BCC, byte[] data)
    {
        for (int i = 0; i < data.length; i++)
        {
            BCC[0] = (byte) (BCC[0] ^ data[i]);
        }
        return BCC[0];
    }

    /**
     * byte转十六进制字符
     *
     * @param b byte
     * @return 十六进制字符
     */
    public static String byteToHex(byte b)
    {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1)
        {
            hex = '0' + hex;
        }
        return hex.toUpperCase(Locale.getDefault());
    }

    /**
     * Hex转Byte字节
     * @param hex 十六进制字符串
     * @return 字节
     */
    public static byte hexToByte(String hex)
    {
        return (byte) Integer.parseInt(hex,16);
    }

    public static String getBCC(byte[] data) {
        String ret = "";
        byte BCC[] = new byte[1];
        for (int i = 0; i < data.length; i++) {
            BCC[0] = (byte) (BCC[0] ^ data[i]);
        }
        String hex = Integer.toHexString(BCC[0] & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex.toUpperCase();
        return ret;
    }

    /**
     * 清除其他选中状态
     *
     * @param id
     */
    public static void clearOtherStatus(int id, List<ControlCheckbox> list)
    {
        for (CheckBox view :
                list)
        {
            if (view.getId() == id)
            {
                view.setChecked(true);
            }
            else
            {
                view.setChecked(false);
            }
        }
    }
}
