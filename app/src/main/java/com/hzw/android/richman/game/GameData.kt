package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean

/**
 * class Date
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
class GameData private constructor() {

    var optionPlayerIndex = 0
    var playerData = mutableListOf<PlayerBean>()
    var mapData = mutableListOf<BaseMapBean>()
    var logData = mutableListOf<String>()

    companion object {
        val INSTANCE: GameData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameData()
        }
    }

    fun load() {
        playerData = JSON.parseArray(GameSave.INSTANCE.loadPlayer(), PlayerBean::class.java)
        parseMap()
    }

    private fun parseMap() {
        val jsonArray = JSON.parseArray(GameSave.INSTANCE.loadMap())
        for (i in 0 until jsonArray.size) {
            val jsonObject = jsonArray.getJSONObject(i)
            when {
                BaseMapBean.MapType.valueOf(jsonObject.getString("type")) == BaseMapBean.MapType.CITY -> {
                    mapData.add(CityBean(jsonObject))
                }
                BaseMapBean.MapType.valueOf(jsonObject.getString("type")) == BaseMapBean.MapType.AREA -> {
                    mapData.add(AreaBean(jsonObject))
                }
                else -> {
                    mapData.add(JSON.parseObject(jsonObject.toJSONString(), BaseMapBean::class.java))
                }
            }
        }
    }

    fun currentPlayer():PlayerBean {
        return playerData[optionPlayerIndex]
    }


}