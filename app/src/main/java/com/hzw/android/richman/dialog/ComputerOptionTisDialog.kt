package com.hzw.android.richman.dialog

import android.content.Context
import android.view.Gravity
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog

/**
 * class ComputerOptionTisDialog
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/15
 */
class ComputerOptionTisDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_computer_option_tips)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)
    }
}