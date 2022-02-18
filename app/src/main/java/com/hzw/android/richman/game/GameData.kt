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

    var turnCount = 0

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

    //股票数据
    var stocksData = mutableListOf<StockBean>()

    companion object {
        val INSTANCE: GameData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameData()
        }
    }

    fun giveGenerals(playerBean: PlayerBean, number: Int) {
        for (i in 1 .. if (generalsData.size > number) number else generalsData.size) {
            val generalsBean = generalsData[(Math.random() * generalsData.size).toInt()]
            playerBean.generals.add(generalsBean)
            generalsData.remove(generalsBean)
        }
    }

    fun giveEquipments(playerBean: PlayerBean, number: Int) {
        for (i in 1 .. if (equipmentData.size > number) number else equipmentData.size) {
            val equipmentBean = equipmentData[(Math.random() * equipmentData.size).toInt()]
            playerBean.equipments.add(equipmentBean)
            equipmentData.remove(equipmentBean)
        }
    }

    fun load() {
        playerData = JSON.parseArray(GameSave.loadPlayer(), PlayerBean::class.java)
        optionPlayerTurnIndex = playerData.size - 1
        parseMap()
        generalsData = GameInit.INSTANCE.generals
        equipmentData = GameInit.INSTANCE.equipments
        stocksData = GameInit.INSTANCE.stocks

        for (item in playerData) {
            giveGenerals(item, Value.DEFAULT_GENERALS)
        }

        for (item in playerData) {
            giveEquipments(item, Value.DEFAULT_EQUIPMENTS)
        }

        for (item in playerData) {
            item.loadBuff()
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