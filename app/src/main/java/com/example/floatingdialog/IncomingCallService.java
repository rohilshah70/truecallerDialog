package com.example.floatingdialog;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class IncomingCallService extends Service {

    static View view = null;
    static WindowManager wm;
    static LayoutInflater inflater;
    static WindowManager.LayoutParams params;
    private static TextView txtIncomingnumber;
    private static ImageView closeImage;
    static boolean processingAction = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onCreate();
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                350,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.RIGHT | Gravity.TOP;

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.overlay,null);

        wm.addView(view, params);
        txtIncomingnumber = view.findViewById(R.id.txtIncomingnumber);
        txtIncomingnumber.setText("You have Incoming Call " + CallReceiver.IncomingNumber);
        txtIncomingnumber.setTextSize(26);

        closeImage = view.findViewById(R.id.closeButton);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearView(getApplicationContext());
            }
        });
        return START_STICKY;
    }

    public static void clearView(Context context) {
        wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        if(view != null) {
            if(view.isEnabled()){
                wm.removeView(view);
                view = null;
            }
        }
    }
}
