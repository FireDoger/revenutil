package com.reven.revenutil.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.reven.revenutil.R;

/**
 * 可控制drawable图像大小的textview
 * <p>
 * by 秘少帅 at 2017-01-03
 */
@SuppressLint("AppCompatCustomView")
public class ControlRadioButton extends RadioButton {
    /** 需要从xml中读取的各个方向图片的宽和高 */

    /**
     * 左方图片的高
     */
    private int mLeftHeight = -1;

    /**
     * 左方图片的宽
     */
    private int mLeftWidth = -1;

    /**
     * 右方图片的高
     */
    private int mRightHeight = -1;

    /**
     * 右方图片的宽
     */
    private int mRightWidth = -1;

    /**
     * 上方图片的高
     */
    private int mTopHeight = -1;

    /**
     * 上方图片的宽
     */
    private int mTopWidth = -1;

    /**
     * 下方图片的高
     */
    private int mBottomHeight = -1;

    /**
     * 下方图片的宽
     */
    private int mBottomWidth = -1;

    public ControlRadioButton(Context context) {
        super(context);
    }

    public ControlRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        // super一定要在我们的代码之前配置文件  
        init(context, attrs, 0);
    }

    public ControlRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // super一定要在我们的代码之前配置文件  
        init(context, attrs, defStyle);
    }

    /**
     * 初始化读取参数
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    @SuppressLint("Recycle")
    private void init(Context context, AttributeSet attrs, int defStyle) {
        // TypeArray中含有我们需要使用的参数  
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlRadioButton, defStyle, 0);

        if (typedArray != null) {
            // 获得参数个数  
            int count = typedArray.getIndexCount();

            int index = 0;

            // 遍历参数。先将index从TypedArray中读出来，  
            // 得到的这个index对应于attrs.xml中设置的参数名称在R中编译得到的数  
            // 这里会得到各个方向的宽和高  
            for (int i = 0; i < count; i++) {
                index = typedArray.getIndex(i);

                if (index == R.styleable.ControlRadioButton_radiobutton_bottom_height) {
                    mBottomHeight = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_bottom_width) {
                    mBottomWidth = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_left_height) {
                    mLeftHeight = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_left_width) {
                    mLeftWidth = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_right_height) {
                    mRightHeight = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_right_width) {
                    mRightWidth = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_top_height) {
                    mTopHeight = typedArray.getDimensionPixelSize(index, -1);

                } else if (index == R.styleable.ControlRadioButton_radiobutton_top_width) {
                    mTopWidth = typedArray.getDimensionPixelSize(index, -1);

                } else {
                }
            }

            // 获取各个方向的图片，按照：左-上-右-下 的顺序存于数组中  
            Drawable[] drawables = getCompoundDrawables();

            int dir = 0;

            // 0-left; 1-top; 2-right; 3-bottom;  
            for (Drawable drawable : drawables) {
                // 设定图片大小  
                setImageSize(drawable, dir++);
            }

            // 将图片放回到TextView中  
            setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    /**
     * 设定图片的大小
     *
     * @param d   drawable
     * @param dir 方向
     */
    private void setImageSize(Drawable d, int dir) {
        if (d == null) {
            return;
        }

        int height = -1;
        int width = -1;

        // 根据方向给宽和高赋值  
        switch (dir) {
            case 0:// 左
                height = mLeftHeight;
                width = mLeftWidth;
                break;
            case 1:// 上
                height = mTopHeight;
                width = mTopWidth;
                break;
            case 2:// 右
                height = mRightHeight;
                width = mRightWidth;
                break;
            case 3:// 下
                height = mBottomHeight;
                width = mBottomWidth;
                break;
            default:
                break;
        }

        // 如果有某个方向的宽或者高没有设定值，则不去设定图片大小  
        if (width != -1 && height != -1) {
            d.setBounds(0, 0, width, height);
        }
    }
}
