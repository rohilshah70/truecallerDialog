package com.example.floatingdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener customPhoneListener = new MyPhoneStateListener(context);

        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        int state = 0;
        if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            state = TelephonyManager.CALL_STATE_IDLE;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            state = TelephonyManager.CALL_STATE_RINGING;
        }
        customPhoneListener.onCallStateChanged(context, state);
    }

}
