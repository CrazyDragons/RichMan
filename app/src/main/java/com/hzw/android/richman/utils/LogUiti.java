package com.hzw.android.richman.utils;

import android.util.Log;

/**
 * class LogUiti
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class LogUiti {

    final static String TAG = "日志";

    public static void print(String msg) {
        Log.e(TAG, msg);
    }

    public static void print(int msg) {
        Log.e(TAG, String.valueOf(msg));
    }
} 