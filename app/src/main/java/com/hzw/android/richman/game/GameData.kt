package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.*
import com.hzw.android.richman.config.Value

/**
 * class Date
 * @author CrazyDragon
 * description 游戏数据
 * note
 * create date 2022/2/10
 */
class GameData private constructor() {

    //操作角色轮询位置
    var optionPlayerTurnIndex = 0

    //角色数据
    var playerData = mutableListOf<PlayerBean>()

    //地图数据
    var mapData = mutableListOf<BaseMapBean>()

    //日志数据
    var logData = mutableListOf<String>()

    //武将数据
    var generalsData = mutableListOf<GeneralsBean>()

    //道具数据
    var equipmentData = mutableListOf<EquipmentBean>()

    companion object {
        val INSTANCE: GameData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameData()
        }
    }

    fun load() {
        playerData = JSON.parseArray(GameSave.loadPlayer(), PlayerBean::class.java)
        parseMap()
        generalsData = GameInit.INSTANCE.generals

        for (item in playerData) {
            for (i in Value.DEFAULT_GENERALS - 1 downTo 0) {
                generalsData[i].owner = item
                item.generals.add(generalsData[i])
                generalsData.removeAt(i)
            }
        }

        equipmentData = GameInit.INSTANCE.equipments
        for (item in playerData) {
            for (i in Value.DEFAULT_EQUIPMENTS - 1 downTo 0) {
                equipmentData[i].owner = item
                item.equipments.add(equipmentData[i])
                equipmentData.removeAt(i)
            }
        }
    }

    private fun parseMap() {
        val jsonArray = JSON.parseArray(GameSave.loadMap())
        for (i in 0 until jsonArray.size) {
            val jsonObject = jsonArray.getJSONObject(i)
            when (BaseMapBean.MapType.valueOf(jsonObject.getString("type"))) {

                BaseMapBean.MapType.CITY -> {
                    mapData.add(CityBean(jsonObject))
                }

                BaseMapBean.MapType.AREA -> {
                    mapData.add(AreaBean(jsonObject))
                }

                else -> {
                    mapData.add(SpecialBean(jsonObject))
                }
            }
        }
    }

    fun currentPlayer(): PlayerBean {
        return playerData[optionPlayerTurnIndex]
    }

    fun currentMap(): BaseMapBean {
        return mapData[currentPlayer().walkIndex]
    }
}