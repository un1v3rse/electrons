package com.bywrights.electrons.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bywrights.electrons.Controller;
import com.bywrights.electrons.R;
import com.bywrights.electrons.model.Prefs;

public class FullscreenActivity extends Activity {
    private static final String
        TAG = "FullscreenActivity";

    private EditText
        speech_edit_;
    private Button
        speak_button_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);


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
        super.onDestroy();
    }

    private String getText() {
        CharSequence seq = speech_edit_.getText();
        return seq == null ? "" : seq.toString().trim();
    }

    private void speak() {
        String text = getText();
        if (text.length() == 0) {
            Toast.makeText(this,R.string.no_text_message,2).show();
        } else if (Controller.sharedInstance().voice().isSpeaking()) {
            Toast.makeText(this,R.string.already_talking_message,2).show();
        } else {
            Prefs.saveText( this, text );
            Controller.sharedInstance().voice().speak(text, null);
        }
    }
}
