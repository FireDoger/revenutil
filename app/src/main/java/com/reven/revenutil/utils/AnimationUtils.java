package com.reven.revenutil.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.transition.Slide;
import android.view.View;
import android.view.animation.AccelerateInterpolator;


/**
 * Created by Reven
 * on 2021/10/9.
 */
public class AnimationUtils
{
    /**
     * 透明度动画
     *
     * @param view
     */
    public static void doAlphaAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 平移动画
     *
     * @param view
     */
    public static void doTranslationAnimation(View view)
    {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 0, 200, 0, -200, 0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", 0, 200, 0, -200, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).before(animatorY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * 缩放动画
     *
     * @param view
     */
    public static void doScaleAnimation(View view)
    {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1F, 0.5F, 1F, 1.5F, 1F);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1F, 0.5F, 1F, 1.5F, 1F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    /**
     * 旋转动画
     *
     * @param view
     */
    public static void doRotationAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 360, 0);
        animator.setDuration(2000);
        //加速查值器，参数越大，速度越来越快
        animator.setInterpolator(new AccelerateInterpolator(2));
        //        //减速差值起，和上面相反
        //        animator.setInterpolator(new DecelerateInterpolator(10));
        //        //先加速后减速插值器
        //        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        //        //张力值，默认为2，T越大，初始的偏移越大，而且速度越快
        //        animator.setInterpolator(new AnticipateInterpolator(3));
        //        //张力值tension，默认为2，张力越大，起始时和结束时的偏移越大
        //        animator.setInterpolator(new AnticipateOvershootInterpolator(6));
        //        //弹跳插值器
        //        animator.setInterpolator(new BounceInterpolator());
        //        //周期插值器
        //        animator.setInterpolator(new CycleInterpolator(2));
        //        //线性差值器,匀速
        //        animator.setInterpolator(new LinearInterpolator());
        //        //张力插值器，扩散反弹一下
        //        animator.setInterpolator(new OvershootInterpolator(2));
        animator.start();
    }


    /**
     * 值动画，适合构建复杂的动画
     *
     * @param view
     */
    public static void doValueAnimator(View view)
    {
        //值的变化，与控件无关
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0, 1);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            view.setScaleX(value);
            view.setScaleY(value);
            view.setAlpha(value);
            view.setRotation(360 * (1 - value));
        });
        animator.setDuration(1000).start();
    }


    /**
     * 估值器（实现重力下落的效果）
     *
     * @param view
     */
    public static void doTypeEvaluator(final View view)
    {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(100);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        animator.setObjectValues(new PointF(location[0], location[1]));
        final PointF pointF = new PointF();
        animator.setEvaluator((fraction, startValue, endValue) -> {
            //fraction是运动中的匀速变化的值
            //根据重力计算实际的运动y=vt=0.5*g*t*t
            //g越大效果越明显
            pointF.x = 100 * (fraction * 5);
            pointF.y = 0.5f * 300f * (fraction * 5) * (fraction * 5);
            return pointF;
        });
        animator.addUpdateListener(animation -> {
            PointF p = (PointF) animation.getAnimatedValue();
            view.setX(p.x);
            view.setY(p.y);
        });
        animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                //还原到原始的位置
                view.setTranslationX(0);
                view.setTranslationY(0);
            }
        });
        animator.start();
    }


    /**
     * 组合动画
     *
     * @param view
     */
    public static void doComposeAnimation1(View view)
    {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0, 1);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0, 360, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha).with(scaleX).with(scaleY).with(rotation);
        animatorSet.setDuration(2000).start();
    }


    /**
     * 组合动画
     *
     * @param view
     */
    public static void doComposeAnimation2(View view)
    {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1, 0, 1);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0, 1);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0, 1);
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", 0, 360, 0);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX, scaleY, rotation);
        animator.setDuration(2000).start();
    }

    public static void setEnterAnimation(Activity context)
    {
        Slide slide = new Slide();
        slide.setDuration(1000);

        context.getWindow().setEnterTransition(slide);
        context.getWindow().setReturnTransition(slide);
    }
    public static void setExitAnimation(Activity context)
    {
        Slide slide = new Slide();
        slide.setDuration(1000);

        context.getWindow().setExitTransition(slide);
    }
}
