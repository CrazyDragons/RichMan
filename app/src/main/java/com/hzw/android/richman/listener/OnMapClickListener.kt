package com.hzw.android.richman.listener

/**
 * class OnMapClickListener
 * @author CrazyDragon
 * description 地图点击监听
 * note
 * create date 2022/2/12
 */
interface OnMapClickListener {
    /**
     * 点击地图
     * @param index 点击位置
     */
    fun onMapClick(index: Int)
}