package com.bywrights.electrons.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bywrights.electrons.Controller;
import com.bywrights.electrons.R;
import com.bywrights.electrons.model.Prefs;

/**
 * Created by chris on 13-07-25.
 */
public class PowerConnectedReceiver extends BroadcastReceiver {

    private static final String
        TAG = "PowerConnectedReceiver";

    public void onReceive(final Context context, Intent intent) {

        String action = intent.getAction();
        if (Intent.ACTION_POWER_CONNECTED.equals(action)) {

            String text = Prefs.text(context);
            if (text == null || text.length() == 0) {
                text = context.getString(R.string.no_text_message);
            }
            Controller.sharedInstance().voice().speak( text, null );
        }
    }

}
