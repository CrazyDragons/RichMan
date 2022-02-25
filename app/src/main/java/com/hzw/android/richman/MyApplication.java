package com.hzw.android.richman;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)     // （可选）是否显示线程信息。 默认值为true
//                .methodCount(1)            //（可选）要显示的方法行数。 默认2
//                .methodOffset(0)           //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                //.logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("日志") //（可选）TAG内容. 默认是 PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static Context getContext() {
        return context;
    }
}