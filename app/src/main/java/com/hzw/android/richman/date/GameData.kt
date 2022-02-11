package com.hzw.android.richman.date

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.save.Save

/**
 * class Date
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
class GameData private constructor() {

    var playerData = mutableListOf<PlayerBean>()

    companion object {
        val INSTANCE: GameData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameData()
        }
    }

    fun load() {
        playerData = JSON.parseArray(Save.INSTANCE.loadPlayer(), PlayerBean::class.java)
    }


}