package com.hzw.android.richman.listener

import com.hzw.android.richman.bean.PlayerBean

/**
 * class OnInputListener
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/15
 */
interface OnInputListener {
    fun onInput(msg: String, buff: PlayerBean.BUFF)
}