package com.reven.revenutil.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.reven.revenutil.R;

import java.io.IOException;
import java.lang.reflect.Method;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

@SuppressWarnings({"unchecked"})
public class ViewUtils
{
    /**
     * ViewHolder简洁写法,避免适配器中重复定义ViewHolder,减少代码量 用法:
     *
     * <pre>
     * if (convertView == null)
     * {
     * 	convertView = View.inflate(context, R.layout.ad_demo, null);
     * }
     * TextView tv_demo = ViewHolderUtils.get(convertView, R.id.tv_demo);
     * ImageView iv_demo = ViewHolderUtils.get(convertView, R.id.iv_demo);
     * </pre>
     */
    public static <T extends View> T hold(View view, int id)
    {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);

        if (childView == null)
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }

        return (T) childView;
    }

    /**
     * 替代findviewById方法
     */
    public static <T extends View> T find(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    /**
     * 是否是平板
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
               >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * @param context
     * @param type
     * @param title          标题
     * @param content        内容
     * @param confirmText    确认文字
     * @param cancleText     取消文字
     * @param confirListener 确认事件
     * @param cancelListener 取消事件
     */
    public static void showNoticDialog(Context context, int type, String title, String content, String confirmText, String cancleText, SweetAlertDialog.OnSweetClickListener confirListener, SweetAlertDialog.OnSweetClickListener cancelListener)
    {
        new SweetAlertDialog(context, type)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirmText)
                .setConfirmClickListener(confirListener)
                .showCancelButton(true)
                .setCancelText(cancleText)
                .setCancelClickListener(cancelListener).show();
    }

    /**
     * 实现图片闪烁效果
     */
    public static void setFlickerAnimation(ImageView iv_chat_head)
    {
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(200); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(1); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); //
        iv_chat_head.setAnimation(animation);
        iv_chat_head.startAnimation(animation);
    }

    /**
     * 显示动画
     *
     * @param view
     */
    public static void fadeIn(final View view)
    {
        if (view.getVisibility() == View.VISIBLE)
        {
            return;
        }
        Animation animation = new AlphaAnimation(0F, 1F);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                view.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏动画
     *
     * @param view
     */
    public static void fadeOut(final View view)
    {
        if (view.getVisibility() != View.VISIBLE)
        {
            return;
        }

        // Since the button is still clickable before fade-out animation
        // ends, we disable the button first to block click.
        view.setEnabled(false);
        Animation animation = new AlphaAnimation(1F, 0F);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                view.setEnabled(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }

    /**
     * 设置左右边距
     *
     * @param v
     * @param leftDimen
     * @param rightDimen
     */
    public static void setMarginLR(View v, int leftDimen, int rightDimen)
    {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(v.getContext()
                                                                                       .getResources()
                                                                                       .getDimensionPixelOffset(R.dimen.y112), v
                                                                                       .getContext()
                                                                                       .getResources()
                                                                                       .getDimensionPixelOffset(R.dimen.y111));
        if (leftDimen == 0)
        {
            layoutParams.setMarginStart(0);
        }
        else
        {
            layoutParams.setMarginStart(v.getContext()
                                                .getResources()
                                                .getDimensionPixelOffset(leftDimen));
        }

        if (rightDimen == 0)
        {
            layoutParams.setMarginEnd(0);
        }
        else
        {
            layoutParams.setMarginEnd(v.getContext()
                                              .getResources()
                                              .getDimensionPixelOffset(rightDimen));
        }

        v.setLayoutParams(layoutParams);
    }

    public static GifDrawable setGifDrawable(Context context, int drawableId, GifImageView gifImageView)
    {
        //这里控制播放的对象实际是gifDrawable
        GifDrawable gifDrawable = null;
        try
        {
            gifDrawable = new GifDrawable(context.getResources(), drawableId);
            gifDrawable.setLoopCount(5);
            gifImageView.setImageDrawable(gifDrawable);//这里是实际决定资源的地方，优先级高于xml文件的资源定义
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return gifDrawable;
    }

    public void gifPlay(boolean stop, int gifId, GifDrawable mGifBackDrawable, GifImageView mGifBackControl, Context context)
    {
        if (stop)
        {
            mGifBackDrawable.stop();
            mGifBackDrawable.seekTo(0);
            return;
        }

        if (gifId != 0)
        {
            mGifBackDrawable = ViewUtils.setGifDrawable(context, gifId, mGifBackControl);
            mGifBackDrawable.start();
        }
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context)
    {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0)
        {
            hasNavigationBar = rs.getBoolean(id);
        }
        try
        {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride))
            {
                hasNavigationBar = false;
            }
            else if ("0".equals(navBarOverride))
            {
                hasNavigationBar = true;
            }
        }
        catch (Exception e)
        {
        }
        return hasNavigationBar;
    }

    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public static int getVirtualBarHeigh(Context context)
    {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try
        {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return vh;
    }
}
