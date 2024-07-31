package com.reven.revenutil.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadUtil
{
    public static Executor sNewCachedThreadPool = Executors.newCachedThreadPool();
    public static ScheduledThreadPoolExecutor sScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    private static Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(final Runnable runnable)
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            runnable.run();
        }
        else
        {
            HANDLER.post(runnable);
        }
    }
}