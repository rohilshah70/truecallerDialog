package com.example.floatingdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager teleMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener psl = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.i("CallReceiverBroadcast", "Incoming call caught");
                        Intent i = new Intent(context, IncomingCallService.class);
                        context.startService(i);
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.i("CallReceiverBroadcast", "CALL_STATE_IDLE");
                        IncomingCallService.clearView(context);
                        // Call Disconnected
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.i("CallReceiverBroadcast", "CALL_STATE_OFFHOOK");
                        IncomingCallService.clearView(context);
                        // Call Answer Mode
                        break;
                }
            }
        };
        teleMgr.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
        teleMgr.listen(psl, PhoneStateListener.LISTEN_NONE);
    }

}
