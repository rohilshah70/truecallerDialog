package com.example.floatingdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class MyPhoneStateListener extends PhoneStateListener {


    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private LinearLayout mLinearLayout;

    public MyPhoneStateListener(Context context) {
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                200,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

//        LayoutInflater li = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//        mLinearLayout = (LinearLayout) li.inflate(R.layout.overlay, null);

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                100));
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setBackgroundColor(Color.RED);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(48,36);
        lp.gravity= Gravity.RIGHT | Gravity.TOP;
        imageView.setLayoutParams(lp);
        imageView.setImageDrawable(context.getDrawable(R.drawable.close_red));

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setText("Incoming Call");
        textView.setTextSize(28);

        mLinearLayout.addView(imageView);
        mLinearLayout.addView(textView);
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