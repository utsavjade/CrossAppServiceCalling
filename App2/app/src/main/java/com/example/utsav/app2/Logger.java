package com.example.utsav.app2;

import android.util.Log;

/**
 * Created by utsav on 12/6/15.
 */
public final class Logger {
    static boolean isLogEnabled = true;
    static String TAG = "TAG";

    public static void d(String msg) {
        if (isLogEnabled)
            Log.d(TAG, msg);
    }
}
