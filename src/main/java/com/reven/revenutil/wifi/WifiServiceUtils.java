package com.reven.revenutil.wifi;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;

import com.reven.revenutil.R;

public class WifiServiceUtils
{
    public static void doForground(Context context)
    {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                String id = "channel_2";
                String name = "wifiService";
                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                channel.setVibrationPattern(new long[]{0, 0, 0, 0});
                channel.enableVibration(true);
                manager.createNotificationChannel(channel);

                notification = new Notification.Builder(context, id)
                        .setContentTitle("WIFI连接中")
                        .setSmallIcon(R.drawable.app_icon)
                        .setPriority(Notification.PRIORITY_MAX)
                        .build();
            }
            else
            {
                notification = new Notification.Builder(context)
                        .setContentTitle("WIFI连接中")
                        .setSmallIcon(R.drawable.app_icon, 0)
                        .setPriority(Notification.PRIORITY_MAX)
                        .build();
                notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
            }
        }
        else
        {
            notification = new Notification();
        }

        ((Service)context).startForeground(1, notification);
    }
}
