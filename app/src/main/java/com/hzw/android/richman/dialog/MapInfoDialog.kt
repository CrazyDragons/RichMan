package com.hzw.android.richman.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog

/**
 * class InfoDialog
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class MapInfoDialog(context: Context, view: View?) : BaseDialog(context) {
    init {
        setContentView(view!!)
        setWindowAnimations(R.style.scale_alpha_animation)
        setGravity(Gravity.CENTER)
    }
}