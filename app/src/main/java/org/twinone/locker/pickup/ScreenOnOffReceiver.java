package org.twinone.locker.pickup;

/**
 * Created by julie on 7/27/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    private boolean screenOff;
    private final String TAG = "======";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
//            Log.d(TAG,"Screen is off");

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
//            Log.d(TAG,"Screen is on");
        }

        // Send Current screen ON/OFF value to service
        Intent i = new Intent(context, ScreenOnOffService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }
}