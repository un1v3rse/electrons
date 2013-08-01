package com.bywrights.electrons.service;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

/**
 * Created by chris on 13-07-25.
 */
public class Voice {

    public static interface SpeakListener {
        public void onStart(String s);
        public void onDone(String s);
        public void onError(String s);
    }


    private static final String
        TAG = "Voice";

    private TextToSpeech
         tts_;

    public Voice() {

    }

    public void onCreate( Context context, final TextToSpeech.OnInitListener listener ) {
        tts_ = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {

                int lang_status = tts_.setLanguage(Locale.US);
                if (lang_status == TextToSpeech.LANG_MISSING_DATA
                        || lang_status == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "This Language is not supported");
                }

            } else {
                Log.e(TAG, "Initialization Failed!");
            }

            if (listener != null) {
                listener.onInit( status );
            }
            }
        });
    }

    public void onDestroy() {
        if (tts_ != null) {
            tts_.stop();
            tts_.shutdown();
            tts_ = null;
        }
    }


    public boolean isSpeaking() { return tts_.isSpeaking(); }

    public void speak( String text, final SpeakListener listener ) {

        if (listener != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                tts_.setOnUtteranceProgressListener( new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        listener.onStart(s);
                    }

                    @Override
                    public void onDone(String s) {
                        listener.onDone(s);
                    }

                    @Override
                    public void onError(String s) {
                        listener.onError(s);
                    }
                });
            } else {
                tts_.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                    @Override
                    public void onUtteranceCompleted(java.lang.String s) {
                        listener.onDone(s);
                    }
                });
            }
        }

        tts_.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }



}
