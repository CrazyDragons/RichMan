package com.hzw.android.richman.utils;

import android.widget.Toast;

import com.hzw.android.richman.MyApplication;

/**
 * class ToastUitl
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/12
 */
public class ToastUitl {
    public static void show(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
} 