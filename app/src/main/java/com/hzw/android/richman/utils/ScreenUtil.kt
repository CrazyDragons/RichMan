package com.hzw.android.richman.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.Display

/**
 * class ScreenUtils
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/8
 */
class ScreenUtil private constructor() {

    companion object {

        var screenWidth = 0
        var screenHeight = 0

        /**
         * dp单位转成px
         *
         * @param context context
         * @param dp      dp值
         * @return px值
         */
        fun dp2px(context: Context, dp: Int): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(context: Activity): Int {
            val metrics = DisplayMetrics()
            val display: Display = context.windowManager.defaultDisplay
            display.getRealMetrics(metrics)
            screenWidth = metrics.widthPixels
            return screenWidth
        }

        /**
         * 获取屏幕高度
         */
        fun getScreenHeight(context: Activity): Int {
            val metrics = DisplayMetrics()
            val display: Display = context.windowManager.defaultDisplay
            display.getRealMetrics(metrics)
            screenHeight = metrics.heightPixels
            return screenHeight
        }
    }

    init {
        throw AssertionError()
    }
}