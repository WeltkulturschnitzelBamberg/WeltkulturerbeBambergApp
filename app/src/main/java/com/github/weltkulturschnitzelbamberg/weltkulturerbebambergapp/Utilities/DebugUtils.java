package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * This class eases debugging
 *
 * @author OrangeUtan
 * @version 1.0
 * @since 2015-06-05
 */
public final class DebugUtils {

    public static final boolean debug = true;
    private static final String tag = "DEBUG";

    public static void toast(Context context, String message) {
        if (debug) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            log(message);
        }
    }

    public static void log(String message) {
        if (debug) Log.d(tag, message);
    }
}