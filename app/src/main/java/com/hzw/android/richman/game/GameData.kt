package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.base.BaseMapBean
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

    var playerData = mutableListOf<PlayerBean>()
    var mapData = mutableListOf<BaseMapBean>()

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
            if (BaseMapBean.MapType.valueOf(jsonObject.getString("type")) == BaseMapBean.MapType.CITY) {
                mapData.add(CityBean(jsonObject))
            }else {
                mapData.add(JSON.parseObject(jsonObject.toJSONString(), BaseMapBean::class.java))
            }
        }
    }


}