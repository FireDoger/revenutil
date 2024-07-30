package com.reven.revenutil.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请工具类
 *
 * @author Lee
 */
public class PermissionsUtils
{

    private int mRequestCode = 100;
    private PermissionsResult mPermissionsResult;
    private static PermissionsUtils permissionsUtils;

    public static PermissionsUtils getInstance() {
        if (permissionsUtils == null) {
            synchronized (PermissionsUtils.class) {
                if (permissionsUtils == null) {
                    permissionsUtils = new PermissionsUtils();
                }
            }
        }
        return permissionsUtils;
    }

    public void checkPermissions(Activity activity, String[] permissions, PermissionsResult result) {
        mPermissionsResult = result;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            result.passPermission();
            return;
        }

        List<String> mPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);
            }
        }

        if (mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissions, mRequestCode);
        } else {
            result.passPermission();
        }
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        boolean hasPermissionDenied = false;
        boolean notRemindAgain = false;

        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                    if (flag) {
                        hasPermissionDenied = true;
                    } else {
                        notRemindAgain = true;
                    }
                }
            }
            if (hasPermissionDenied) {
                mPermissionsResult.continuePermission();
            } else if (notRemindAgain) {
                mPermissionsResult.refusePermission();
            } else {
                mPermissionsResult.passPermission();
            }
        }
    }

    public interface PermissionsResult {

        /**
         * 权限全部通过
         */
        void passPermission();

        /**
         * 权限部分通过
         */
        void continuePermission();

        /**
         * 权限拒绝且不再提醒
         */
        void refusePermission();
    }

    /**
     * 判断GPS是否开启
     */
    public static boolean isGpsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 强制打开GPS
     */
    public static void openGps(Context context) {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(settingsIntent);
    }
}
