package com.hzw.android.richman.listener

/**
 * class OnWalkListener
 * @author CrazyDragon
 * description 投掷监听
 * note
 * create date 2022/2/10
 */
interface OnWalkListener {
    /**
     * 投掷开始
     * @param count 投掷数
     * @param isFinish 是否投掷完成
     */
    fun onWalkStart(count: Int, isFinish:Boolean)

    /**
     * 走动完成
     * @param isNewGame 是否是新游戏
     */
    fun onWalkFinish(isNewGame:Boolean)
}