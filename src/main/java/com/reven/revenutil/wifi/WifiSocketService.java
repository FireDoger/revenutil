package com.reven.revenutil.wifi;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.reven.revenutil.utils.CommonUtil;
import com.reven.revenutil.utils.ThreadUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Reven
 * on 2021/10/26.
 */
public class WifiSocketService extends Service
{
    private WifiSocketCallBack mWifiSocketCallBack;
    private WifiSocketBinder mWifiSocketBinder = new WifiSocketBinder();
    private DataOutputStream outputStream = null;
    private DataInputStream inputStream = null;
    private Socket socket = null;
    private boolean socketStatus = false;

    @Override
    public IBinder onBind(Intent intent)
    {
        WifiServiceUtils.doForground(this);
        return mWifiSocketBinder;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        stopForeground(true);
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    public class WifiSocketBinder extends Binder
    {
        public WifiSocketService getService()
        {
            return WifiSocketService.this;
        }
    }

    public WifiSocketCallBack getWifiSocketCallBack()
    {
        return mWifiSocketCallBack;
    }

    public void setWifiSocketCallBack(WifiSocketCallBack wifiSocketCallBack)
    {
        mWifiSocketCallBack = wifiSocketCallBack;
    }

    public WifiSocketBinder getWifiSocketBinder()
    {
        return mWifiSocketBinder;
    }

    public void setWifiSocketBinder(WifiSocketBinder wifiSocketBinder)
    {
        mWifiSocketBinder = wifiSocketBinder;
    }

    byte[] msgId = new byte[4];


    public void connect(String ip, String port)
    {
        LogUtils.d("connect ip：" + ip);
        if (ip == null)
        {
            return;
        }

        ThreadUtil.sNewCachedThreadPool.execute(() -> {
            try
            {
                socket = new Socket(ip, Integer.parseInt(port));
                LogUtils.d("socket connect success");
                socket.setSoTimeout(1000 * 10);
                socket.setKeepAlive(true);
                socketStatus = true;
                outputStream = new DataOutputStream(socket.getOutputStream());
                inputStream = new DataInputStream(socket.getInputStream());

                mWifiSocketCallBack.onReceiveData("连接成功");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                close();
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException interruptedException)
                {
                    interruptedException.printStackTrace();
                }
            }

            while (socketStatus)
            {
                try
                {
                    readData(1, inputStream);//数据头55
                    readData(1, inputStream);//类型01
                    readData(1, inputStream);//类型参数00
                    readData(1, inputStream);//保留位00
                    // 获取头信息：后续数据长度
                    byte[] length = readData(2, inputStream);
                    // 获取头信息：后续数据长度（包含时间戳等信息的长度）
                    StringBuffer bb = new StringBuffer();
                    for (byte b : length)
                    {
                        bb.append(CommonUtil.byteToHex(b));
                    }

                    int dataLength = Integer.parseInt(bb.toString(), 16);
                    int frameNum = dataLength / 80;//每个数据帧（不带校验位）长度80字节，字符长度160
                    for (int i = 0; i < frameNum; i++)
                    {
                        mWifiSocketCallBack.onReceiveData(readData(80, inputStream));
                    }
                    readData(1, inputStream);//读取校验位
                }
                catch (IOException e)
                {
                    close();
                    if (e.getMessage().contains("-1"))
                    {
                        try
                        {
                            Thread.sleep(20);
                        }
                        catch (InterruptedException ex)
                        {
                            ex.printStackTrace();
                        }
                        connect(ip, port);
                    }
                    else
                    {
                        mWifiSocketCallBack.onReceiveData("连接失败");
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    public void send(String data)
    {
        if (data == null)
        {
            return;
        }

        if (data.length() != 24)
        {
            LogUtils.d("数据错误：" + data);
            return;
        }
        LogUtils.d("send wifi data：" + data);
        String id = data.substring(0, 8);
        String realData = data.substring(8);

        ThreadUtil.sNewCachedThreadPool.execute(() -> {
            if (socketStatus)
            {
                //0008 90 24 00 00 00 00 00 0A
                //起始标志1、包类型1、类型参数1、保留1、数据长度2、数据区1、校验码1
                //                byte[] a = new byte[]{0x55,    0x00,  0x00,   0x00,     20,     0x66,   0x00};

                //第7位，校验码1
                byte[] check = new byte[1];
                // 第1位，起始标志1
                byte[] head = new byte[1];
                head[0] = CommonUtil.hexToByte("55");

                //第2位，包类型1
                byte[] type = new byte[1];
                type[0] = CommonUtil.hexToByte("00");

                check[0] = (byte) (head[0] ^ type[0]);

                //第3位，类型参数1
                byte[] typeParam = new byte[1];
                typeParam[0] = CommonUtil.hexToByte("00");
                check[0] = (byte) (check[0] ^ typeParam[0]);

                //第4位，保留1
                byte[] keep = new byte[1];
                keep[0] = CommonUtil.hexToByte("00");
                check[0] = (byte) (check[0] ^ keep[0]);

                //第5位，数据长度2
                byte[] length = new byte[2];
                length[0] = CommonUtil.hexToByte("00");
                length[1] = CommonUtil.hexToByte("18");
                check[0] = CommonUtil.getBCC(check, length);

                //第6位，数据区1 data.getbytes，数据位之前还有时间戳等信息，所以，前面加上那些信息需要
                //时间戳。。
                byte[] stampByte = new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};
                check[0] = CommonUtil.getBCC(check, stampByte);

                //ID
                byte[] canId = new byte[]{CommonUtil.hexToByte(id.substring(0, 2)),
                        CommonUtil.hexToByte(id.substring(2, 4)),
                        CommonUtil.hexToByte(id.substring(4, 6)),
                        CommonUtil.hexToByte(id.substring(6))};
                check[0] = CommonUtil.getBCC(check, canId);

                byte[] info = new byte[2];
                info[0] = CommonUtil.hexToByte("00");
                info[1] = CommonUtil.hexToByte("00");
                check[0] = CommonUtil.getBCC(check, info);

                byte[] road = new byte[1];
                road[0] = CommonUtil.hexToByte("00");
                check[0] = CommonUtil.getBCC(check, road);

                byte[] dataLen = new byte[1];
                dataLen[0] = CommonUtil.hexToByte("8");
                check[0] = CommonUtil.getBCC(check, dataLen);

                //数据
                byte[] dataByte = new byte[]{
                        CommonUtil.hexToByte(realData.substring(0, 2)),
                        CommonUtil.hexToByte(realData.substring(2, 4)),
                        CommonUtil.hexToByte(realData.substring(4, 6)),
                        CommonUtil.hexToByte(realData.substring(6, 8)),
                        CommonUtil.hexToByte(realData.substring(8, 10)),
                        CommonUtil.hexToByte(realData.substring(10, 12)),
                        CommonUtil.hexToByte(realData.substring(12, 14)),
                        CommonUtil.hexToByte(realData.substring(14))};
                check[0] = CommonUtil.getBCC(check, dataByte);

                String hex = Integer.toHexString(check[0] & 0xFF);
                if (hex.length() == 1)
                {
                    hex = '0' + hex;
                }

                String ret = hex.toUpperCase();
                check[0] = CommonUtil.hexToByte(ret);
                /*src参数：源数组
                srcPos参数：从src数组的第几个元素开始赋值
                dest参数：目标数组
                destPos参数：指定从dest数组的第几个元素开始。
                length参数：指定从src数组拿几个元素来赋值到dest数组*/

                try
                {
                    outputStream.write(head);
                    outputStream.write(type);
                    outputStream.write(typeParam);
                    outputStream.write(keep);
                    outputStream.write(length);
                    outputStream.write(stampByte);
                    outputStream.write(canId);
                    outputStream.write(info);
                    outputStream.write(road);
                    outputStream.write(dataLen);
                    outputStream.write(dataByte);
                    outputStream.write(check);
                }
                catch (IOException e)
                {
                    LogUtils.d("send data IOException：" + data);
                    e.printStackTrace();
                    close();
                    mWifiSocketCallBack.onReceiveData("连接失败");
                }
            }
            else
            {
                mWifiSocketCallBack.onReceiveData("连接失败");
                LogUtils.d("socketStatus false,cant send data：" + data);
            }
        });
    }

    /**
     * 接收数据
     *
     * @param length 读取的长度
     * @return 数据
     */
    private byte[] readData(int length, DataInputStream inputStream) throws IOException
    {
        int readBytes = 0;
        byte[] data = new byte[length];

        while (readBytes < length)
        {
            int read = inputStream.read(data, readBytes, length - readBytes);
            //判断是不是读到了数据流的末尾 ，防止出现死循环。
            if (read == -1)
            {
                socketStatus = false;
                throw new IOException("mInputStream read -1");
            }

            readBytes += read;
        }

        return data;
    }

    public void close()
    {
        socketStatus = false;
        try
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
            if (outputStream != null)
            {
                outputStream.close();
            }
            if (socket != null)
            {
                socket.close();
            }
            socketStatus = false;
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }
}
