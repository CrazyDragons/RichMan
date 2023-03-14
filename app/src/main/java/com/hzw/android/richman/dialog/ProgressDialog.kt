package com.hzw.android.richman.dialog

import android.content.Context
import android.view.Gravity
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog

/**
 * Created by AdminFun on 2016-05-18
 * 进度框(自定义使用的时候可以设置标题)
 * 也可以动态更新标题
 */
class ProgressDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_progress)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)
    }
}