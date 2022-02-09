package com.hzw.android.richman;

import android.app.Application;
import android.content.Context;

/**
 * class Application
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}