package com.hzw.android.richman.listener

/**
 * class OnWalkListener
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
interface OnWalkListener {
    fun onWalkStart(count: Int, isFinish:Boolean)
    fun onWalkFinish(isNewGame:Boolean)
}