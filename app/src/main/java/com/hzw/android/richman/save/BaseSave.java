package com.hzw.android.richman.save;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;

import com.hzw.android.richman.MyApplication;

/**
 * 轻量级存储工具
 * <p>
 * Created by AdminFun on 2013-10
 *
 * @version 1.0
 */
public abstract class BaseSave {

    @NonNull
    protected SharedPreferences preferences;
    protected Editor editor;

    public BaseSave() {
        this.preferences = MyApplication.getContext().getSharedPreferences("RichMan", Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    //// int
    protected boolean putIntData(String key, int value) {
        this.editor.putInt(key, value);
        return editor.commit();
    }

    protected int getIntData(String key) {
        return preferences.getInt(key, 0);
    }

    protected int getIntData(String key, int default_) {
        return preferences.getInt(key, default_);
    }

    //// String
    protected boolean putStringData(@NonNull String key, @NonNull String value) {
        this.editor.putString(key, value);
        return editor.commit();
    }

    protected String getStringData(String key) {
        return preferences.getString(key, "");
    }

    protected String getStringData(String key, String default_) {
        return preferences.getString(key, default_);
    }

    //// long
    protected boolean putLongData(String key, long value) {
        this.editor.putLong(key, value);
        return editor.commit();
    }

    protected long getLongData(String key) {
        return preferences.getLong(key, -1);
    }

    //// boolean
    protected boolean putBoolearnData(String key, boolean istrue) {
        this.editor.putBoolean(key, istrue);
        return editor.commit();
    }

    protected boolean getBooleanData(String key) {
        return preferences.getBoolean(key, false);
    }

    protected boolean getBooleanData(String key, boolean value) {
        return preferences.getBoolean(key, value);
    }

    protected boolean putBoolearnAuto(String key) {
        return putBoolearnData(key, !this.getBooleanData(key));
    }
}