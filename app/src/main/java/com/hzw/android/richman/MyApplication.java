package com.hzw.android.richman;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * class Application
 *
 * @author CrazyDragon
 * description 主程序
 * note
 * create date 2022/2/10
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
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