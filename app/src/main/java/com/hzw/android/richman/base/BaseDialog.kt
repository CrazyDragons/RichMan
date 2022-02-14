package com.hzw.android.richman.base

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.Window
import com.hzw.android.richman.R
import com.hzw.android.richman.utils.ScreenUtil.screenHeight
import com.hzw.android.richman.utils.ScreenUtil.screenWidth

/**
 * class BaseDialog
 *
 * @author CrazyDragon
 * description dialog基类
 * note
 * create date 2022/2/13
 */
open class BaseDialog(context: Context) : Dialog(context, R.style.dialog_style) {

    private fun initBase(context: Context) {
        window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        setGravity(Gravity.CENTER)
    }


    fun setGravity(gravity: Int) {
        this.window!!.setGravity(gravity)
    }

    fun setWindowAnimations(animations: Int) {
        this.window!!.setWindowAnimations(animations)
    }

    fun setHeight(height: Int) {
        val lp = window!!.attributes
        lp.height = height
        window!!.attributes = lp
    }

    fun setWidth(width: Int) {
        val lp = window!!.attributes
        lp.width = width
        window!!.attributes = lp
    }

    fun setFullWidth(x: Double) {
        setWidth((screenWidth / x).toInt())
    }

    fun setFullHeight(x: Double) {
        setHeight((screenHeight / x).toInt())
    }

    fun setFull(w: Double, h: Double) {
        setFullWidth(w)
        setFullHeight(h)
    }

    fun setFull() {
        setFullWidth(1.0)
        setFullHeight(1.0)
    }

    init {
        initBase(context)
    }
}