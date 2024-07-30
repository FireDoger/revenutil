package com.reven.revenutil.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Reven
 * on 2021/9/28.
 */
public class DrawGifView extends GifImageView
{
    public DrawGifView(Context context)
    {
        super(context);
    }

    public DrawGifView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DrawGifView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public DrawGifView(Context context, AttributeSet attrs, int defStyle, int defStyleRes)
    {
        super(context, attrs, defStyle, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(Color.LTGRAY);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true);
//        paint.setStrokeWidth(1);
//        Path path = new Path();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//
//        float x = (getWidth() - getHeight() / 2) / 4;
//        float y = getHeight() / 8;
//
//        RectF oval = new RectF(x, y,
//                               getWidth() - x, getHeight() - y);
//
//        canvas.drawArc(oval,-85,85,false,paint);
//        canvas.drawArc(oval,-85,15,false,paint);
//        canvas.drawArc(oval,-35,30,false,paint);
    }
}
