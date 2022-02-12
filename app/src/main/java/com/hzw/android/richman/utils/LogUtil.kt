package com.hzw.android.richman.utils

import android.util.Log

/**
 * class LogUtil
 *
 * @author CrazyDragon
 * description 日志工具
 * note
 * create date 2022/2/9
 */
object LogUtil {

    private const val TAG = "日志"

    fun print(msg: String) {
        Log.e(TAG, msg)
    }

    fun print(msg: Int) {
        Log.e(TAG, msg.toString())
    }
}