package com.bywrights.electrons.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.bywrights.electrons.R;
import com.bywrights.electrons.model.Prefs;
import com.bywrights.electrons.service.Voice;

/**
 * Created by chris on 13-07-25.
 */
public class PowerConnectedReceiver extends BroadcastReceiver {

    private static final String
        TAG = "PowerConnectedReceiver";

    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
            final Voice voice = new Voice();
            voice.onCreate(context, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                        String text = Prefs.text(context).trim();
                        if (text.length() == 0) {
                            text = context.getString(R.string.no_text_message);
                        }
                        voice.speak(text, new Voice.SpeakListener() {
                            @Override
                            public void onStart(String s) {
                                Log.d( TAG, s );
                            }

                            @Override
                            public void onDone(String s) {
                                Log.d( TAG, s );
                                voice.onDestroy();
                            }

                            @Override
                            public void onError(String s) {
                                Log.e( TAG, s );
                                voice.onDestroy();
                            }
                        });
                    }
                }
            });
        }
    }

}
