package com.reven.revenutil.wifi;

/**
 * Created by Reven
 * on 2021/10/26.
 */
public interface WifiSocketCallBack
{
    void onReceiveData(String receivedData);

    void onReceiveData(byte[] receivedData);
}
