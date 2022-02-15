package com.hzw.android.richman.dialog

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.listener.OnClickTipsListener
import kotlinx.android.synthetic.main.dialog_tips.*

/**
 * class TipsDialog
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/14
 */
class TipsDialog(
    context: Context,
    message: String,
    yes: String?,
    no: String?,
    onClickTipsListener: OnClickTipsListener?
) : BaseDialog(context) {

    constructor(context: Context, message: String) : this(context, message, null, null, null)
    constructor(context: Context, message: String, onClickTipsListener: OnClickTipsListener?) : this(context, message, null, null, onClickTipsListener)


    init {
        setContentView(R.layout.dialog_tips)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)

        mTvMessage.text = message
        mTvYes.text = yes
        mTvNo.text = no

        mTvNo.visibility = if (TextUtils.isEmpty(no)) View.GONE else View.VISIBLE

        if (TextUtils.isEmpty(yes) && TextUtils.isEmpty(no)) {
            mTvYes.text = "我知道了"
        }


        mTvYes.setOnClickListener {
            onClickTipsListener?.onClickYes()
            dismiss()
        }
        mTvNo.setOnClickListener {
            onClickTipsListener?.onClickNo()
            dismiss()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (!GameData.INSTANCE.currentPlayer().isPlayer) {
                mTvYes.performClick()
            }
        }, 1000)
    }
}