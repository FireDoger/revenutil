package com.reven.revenutil.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FileUtil
{
    private static final String TAG = FileUtil.class.getSimpleName();
    public static final Executor EXECUTOR = Executors.newCachedThreadPool();

    public static void mkdirs(String filePath)
    {
        boolean mk = new File(filePath).mkdirs();
        Log.d(TAG, "mkdirs: " + mk);
    }

    /**
     * 检测文件是否存在
     *
     * @param filePath 要检测的文件路径
     * @return true, 文件存在;否则返回false
     */
    public static boolean exists(String filePath)
    {
        File file = new File(filePath);

        return file.exists();
    }

    /**
     * 创建文件夹(包含父文件夹)
     *
     * @param dirPath 要创建的文件夹路径
     */
    public static void mkDirs(String dirPath)
    {
        File file = new File(dirPath);

        if (!file.exists())
        {
            file.mkdirs();
        }
    }

    /**
     * 删除已存在的文件
     *
     * @param filePath 要删除的文件路径
     */
    public static void delExists(String filePath)
    {
        File file = new File(filePath);

        if (file.isFile() && file.exists())
        {
            file.delete();
        }
    }

    public static String getSDPath(Context context)
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist)
        {
            if (Build.VERSION.SDK_INT >= 29)
            {
                //Android10之后
                sdDir = context.getExternalFilesDir(null);
            }
            else
            {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
        }
        else
        {
            sdDir = Environment.getRootDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

}
