package com.bywrights.electrons.activity;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bywrights.electrons.model.Prefs;
import com.bywrights.electrons.R;
import com.bywrights.electrons.service.Voice;

public class FullscreenActivity extends Activity {
    private static final String
        TAG = "FullscreenActivity";

    private Voice
        voice_ = new Voice();
    private EditText
        speech_edit_;
    private Button
        speak_button_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        voice_.onCreate( this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    speech_edit_.setEnabled(true);
                    speak_button_.setEnabled(true);
                    speak();
                } else {
                    Toast.makeText(FullscreenActivity.this, R.string.speech_setup_failed_message, 2).show();
                }
            }
        });

        speech_edit_ = (EditText) findViewById(R.id.speech_edit);
        speech_edit_.setText( Prefs.text(this) );

        speak_button_ = (Button) findViewById(R.id.speak_button);
        speak_button_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speak();
            }

        });
    }

    @Override
    public void onPause() {
        Prefs.saveText( this, getText() );
        super.onPause();
    }

    @Override
    public void onDestroy() {
        voice_.onDestroy();
        super.onDestroy();
    }

    private String getText() {
        try {
            return speech_edit_.getText().toString();
        } catch (Throwable t) {
            Log.e(TAG, "getText", t);
        }
        return "";
    }

    private void speak() {
        String text = getText().trim();
        if (text.length() == 0) {
            Toast.makeText(this,R.string.no_text_message,2).show();
        } else if (voice_.isSpeaking()) {
            Toast.makeText(this,R.string.already_talking_message,2).show();
        } else {
            Prefs.saveText( this, text );
            voice_.speak(text, null);
        }
    }
}
