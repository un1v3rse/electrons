package com.bywrights.electrons;

import android.app.Application;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.bywrights.electrons.service.Voice;

/**
 * Created by chris on 13-07-25.
 */

/*

    Program Structure (these concepts correspond to classes and packages)

    Controller

        This is where any central initialization that the app needs on the start of any Activity will
        happen.

        For this project, we don't need any of that.

    Activity

        All user interface presentation is wrapped in a Activity, of course.  In most instances,
        little code should be in the activity, aside from deciding what fragments to present.

        This project being small, and Android Studio apparently not friendly with ActionBarSherlock,
        all of the UI is in a single activity.  Can't fix everything.

    Fragment

        The UI components of the app.  Cast as fragments so they can be positioned differently for
        different screen sizes, such as Master->Detail on a tablet.

        There are no fragments in this project.

    Model

        This is where the business logic of the application resides, and persistence is managed.

        The model can be instantiated multiple times (as would be needed for a background service),
        with some caveats, such as only one instance should be allowed to write to any one Sqlite
        database (yes it is possible to have many do so, but I find the complexity impractical).

        For this app, only a simple entry in SharedPreferences is required.

    Receiver

        This is where our external event receivers live.

        We only have one receiver, listening for the power connected event.  Happily, since Android
        will wake us on this event, we avoided needing a background service to handle the event.

    Service

        Services are a little more generic than the Android Service, in that they don't actually
        have to run as a service.  What I mean by Service is a compartmentalized piece of functionality
        that manages its own state.

        We have a single Voice service for this app.


 */

public class Controller extends Application {

    public static final String
        AUTHORITY = "com.bywrights.electrons",
        TAG = AUTHORITY + ".Controller";

    private static Controller
        INSTANCE = null;
    private Voice
        voice_;

    public static Controller sharedInstance() {
        if (INSTANCE == null)
            throw new IllegalStateException("Application not created yet!");
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
                Log.e( TAG, thread.getName(), ex );
            }
        } );

        INSTANCE = this;
        voice_ = new Voice();

        voice_.onCreate( getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Toast.makeText(getBaseContext(), R.string.speech_setup_failed_message, 2).show();
                }
            }
        });
    }

    public Voice voice() { return voice_; }


}
