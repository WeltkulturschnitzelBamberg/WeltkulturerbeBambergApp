package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.BuildConfig;

/**
 * This class eases debugging
 *
 * @author OrangeUtan
 * @version 1.0
 * @since 2015-06-05
 */
public final class DebugUtils {

    private static final String tag = "DEBUG";

    public static void toast(Context context, String message) {
        if (isDebugMode()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            log(">>" + message + "<<");
        }
    }

    public static void log(String message) {
        if (isDebugMode()) Log.d(tag, message);
    }

    public static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }
}