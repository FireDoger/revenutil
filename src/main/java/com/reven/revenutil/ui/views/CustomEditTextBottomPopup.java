package com.reven.revenutil.ui.views;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.core.BottomPopupView;
import com.reven.revenutil.R;
import com.util.reven.utils.ToastyUtils;
import com.util.reven.wifi.WifiSocketService;

/**
 * Created by Reven
 * on 2022/2/26.
 */
public class CustomEditTextBottomPopup extends BottomPopupView
{
    private WifiSocketService mWifiSocketService;

    @Override
    protected void onKeyboardHeightChange(int height)
    {
        super.onKeyboardHeightChange(height);
    }

    public CustomEditTextBottomPopup(@NonNull Context context)
    {
        super(context);
    }

    @Override
    protected int getImplLayoutId()
    {
        return R.layout.custom_edittext_bottom_popup;
    }

    @Override
    protected void onCreate()
    {
        super.onCreate();

        findViewById(R.id.confirm_socket_info).setOnClickListener(v -> {
            if (mWifiSocketService != null)
            {
                String ip = ((EditText) findViewById(R.id.et_ip)).getText().toString();
                String port = ((EditText) findViewById(R.id.et_port)).getText().toString();

                if (ip.equals("") || port.equals(""))
                {
                    ToastyUtils.toastWarn("缺少信息",true);
                    return;
                }

                mWifiSocketService.connect(ip, port);
                dismiss();
            }
        });

        setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK)
            {
                ToastUtils.showShort("自定义弹窗设置了KeyListener");
                return true;
            }
            return true;
        });
    }

    @Override
    protected void onShow()
    {
        super.onShow();
    }

    @Override
    protected void onDismiss()
    {
        super.onDismiss();
    }

    public void setWifiService(WifiSocketService wifiSocket)
    {
        mWifiSocketService = wifiSocket;
    }

    //        @Override
    //        protected int getMaxHeight()
    //        {
    //            return (int) (XPopupUtils.getScreenHeight(MainActivity.this) * 0.75);
    //        }
}

