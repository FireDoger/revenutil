package com.reven.revenutil.utils;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.util.reven.global.UtilApplication;

import es.dmoral.toasty.Toasty;

public class ToastyUtils
{
    private static Toast sInstance;

    /***
     *
     * 弹出成功消息*   
     * @param text 需要显示的消息   
     * @param isShowIcon 是否需要显示图标 默认显示
     *
     */
    public static void toastSuccess(@NonNull String text, @NonNull Boolean isShowIcon)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.success(UtilApplication.getContext(), text, Toast.LENGTH_SHORT, isShowIcon);
        sInstance.show();
    }

    /***弹出错误消息*   
     * *@param text 需要显示的消息   
     * *@param isShowIcon 是否需要显示图标 默认显示*
     * */
    public static void toastError(@NonNull String text, @NonNull Boolean isShowIcon)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.error(UtilApplication.getContext(), text, Toast.LENGTH_SHORT, isShowIcon);
        sInstance.show();
    }

    /***弹出info消息*   
     * @param text 需要显示的消息*
     * @param isShowIcon 是否需要显示图标 默认显示*
     */

    public static void toastInfo(@NonNull String text, @NonNull Boolean isShowIcon)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.info(UtilApplication.getContext(), text, Toast.LENGTH_SHORT, isShowIcon);
        sInstance.show();
    }

    /***弹出警告消息*   
     *@param text 需要显示的消息   
     *@param isShowIcon 是否需要显示图标 默认显示*
     */

    public static void toastWarn(@NonNull String text, @NonNull Boolean isShowIcon)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.warning(UtilApplication.getContext(), text, Toast.LENGTH_SHORT, isShowIcon);
        sInstance.show();
    }

    /***
     * 弹出一般的消息
     * @param text
     */
    public static void toastNormal(@NonNull String text)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.normal(UtilApplication.getContext(), text);
        sInstance.show();
    }

    /***
     * 弹出一般的带icon的消息
     * @param text
     * @param icon  图片id
     */
    public static void toastNormalWithIcon(@NonNull String text, @NonNull int icon)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.normal(UtilApplication.getContext(), text, ContextCompat.getDrawable(UtilApplication
                                                                                                        .getContext(), icon));
        sInstance.show();
    }

    /***
     *
     * @param text 文本
     * @param icon 图标id
     * @param isShowIcon 是否显示图标
     * @param textColor 文本的颜色
     * @param color 设置Toast的颜色
     * @param isShowBackColor 是否应用设置的颜色，如果为false代表不应用，那么依旧显示的是Normal时的颜色
     */
    public static void toastCustorm(@NonNull String text, @NonNull int icon, @NonNull Boolean isShowIcon, @NonNull int textColor, @NonNull int color, @NonNull Boolean isShowBackColor)
    {
        if (sInstance != null)
        {
            sInstance.cancel();
        }
        sInstance = Toasty.custom(UtilApplication.getContext(), text, ContextCompat.getDrawable(UtilApplication.getContext(), icon), textColor, color, Toast.LENGTH_SHORT, isShowIcon,
                                  isShowBackColor);
        sInstance.show();
    }
}
