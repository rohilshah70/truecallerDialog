package com.example.floatingdialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class MyPhoneStateListener extends PhoneStateListener {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private LinearLayout mLinearLayout;
    private TextView mTextView;

    public MyPhoneStateListener(Context context) {
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                200,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        LayoutInflater li = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        mLinearLayout = (LinearLayout) li.inflate(R.layout.overlay, null);
    }

    public void onCallStateChanged(Context context, int state){
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:

                mWindowManager.addView(mLinearLayout, mParams);
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
            case TelephonyManager.CALL_STATE_IDLE:
                    if(mLinearLayout != null) {
//                        WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
                        mWindowManager.removeViewImmediate(mLinearLayout);
                }
                break;
        }
    }
}