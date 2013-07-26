package com.bywrights.electrons.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.bywrights.electrons.R;

/**
 * Created by chris on 13-07-25.
 */
public final class Prefs {

    private static final String
        PREFS_NAME = "com.bywrights.electrons.model.Prefs",
        TEXT = "TEXT";

    public static String text( Context context ) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString( TEXT, context.getString( R.string.default_text ) );
    }

    public static void saveText( Context context, String text ) {
        SharedPreferences.Editor editor = context.getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE ).edit();
        editor.putString( TEXT, text );
        editor.commit();
    }
}
