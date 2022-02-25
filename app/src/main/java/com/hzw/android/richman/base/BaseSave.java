package com.hzw.android.richman.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;

import com.hzw.android.richman.MyApplication;

/**
 * @author hezhongwei
 */
public abstract class BaseSave {

    @NonNull
    protected SharedPreferences preferences;
    protected Editor editor;

    public BaseSave() {
        this.preferences = MyApplication.getContext().getSharedPreferences("RichMan", Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    protected boolean putStringData(@NonNull String key, @NonNull String value) {
        this.editor.putString(key, value);
        return editor.commit();
    }

    protected String getStringData(String key) {
        return preferences.getString(key, "");
    }

    public boolean putBooleanData(String key, boolean isTrue) {
        this.editor.putBoolean(key, isTrue);
        return editor.commit();
    }


    protected boolean getBooleanData(String key) {
        return preferences.getBoolean(key, true);
    }
}