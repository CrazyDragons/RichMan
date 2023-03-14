package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.base.BaseCityBean
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

    private fun clean() {
        turnCount = 0
        optionPlayerTurnIndex = 0
        playerData.clear()
        generalsData.clear()
        equipmentData.clear()
        stocksData.clear()
        mapData.clear()
        logData.clear()
    }

    fun giveBaseCity(playerBean: PlayerBean) {
        val list = mutableListOf<BaseCityBean>()
        for (item in mapData) {
            if (item is BaseCityBean && item.ownerID == 0) {
                list.add(item)
            }
        }
        if (list.isNotEmpty()) {
            val baseCityBean = list[(Math.random() * list.size + 1).toInt()]
            baseCityBean.ownerID = playerBean.id
            playerBean.city.add(baseCityBean)
        }

    }

    fun giveGenerals(playerBean: PlayerBean, number: Int) {
        for (i in 1 .. if (generalsData.size > number) number else generalsData.size) {
            val generalsBean = generalsData[(Math.random() * generalsData.size).toInt()]
            GameLog.INSTANCE.addGeneralsLog(generalsBean)
            generalsBean.ownerID = playerBean.id
            playerBean.generals.add(generalsBean)
            generalsData.remove(generalsBean)
        }
    }

    fun giveEquipments(playerBean: PlayerBean, number: Int) {
        for (i in 1 .. if (equipmentData.size > number) number else equipmentData.size) {
            val equipmentBean = equipmentData[(Math.random() * equipmentData.size).toInt()]
            equipmentBean.ownerID = playerBean.id
            playerBean.equipments.add(equipmentBean)
            equipmentData.remove(equipmentBean)
        }
    }

    fun changeStock(double: Double) {
        for (stock in stocksData) {
            stock.change(double)
        }
    }


    fun init() {
        clean()
        parsePlayer(GameSave.loadPlayer())
        mapData = GameInit.initMap()
        generalsData = GameInit.initGenerals()
        equipmentData = GameInit.initEquipments()
        stocksData = GameInit.initStocks()

        for (item in playerData) {
            giveGenerals(item, Value.DEFAULT_GENERALS)
        }

        for (item in playerData) {
            giveEquipments(item, Value.DEFAULT_EQUIPMENTS)
        }

        for (item in playerData) {
            item.loadBuff()
        }

        for (item in playerData) {
            if (!item.isPlayer) {
                item.money += Value.DEFAULT_MONEY/2
                item.stocks.add(StockBean("A", 50))
                giveGenerals(item, 3)
            }
        }

        GameLog.INSTANCE.clear()
    }

    fun load() {
        clean()
        val jsonObject = JSON.parseObject(GameSave.loadData())
        parseMap(jsonObject.getString("mapData"))
        parsePlayer(jsonObject.getString("playerData"))
        generalsData = JSON.parseArray(jsonObject.getString("generalsData"), GeneralsBean::class.java)
        equipmentData = JSON.parseArray(jsonObject.getString("equipmentData"), EquipmentBean::class.java)
        stocksData = JSON.parseArray(jsonObject.getString("stocksData"), StockBean::class.java)
        logData = JSON.parseArray(jsonObject.getString("logData"), String::class.java)
        turnCount = jsonObject.getIntValue("turnCount")
        optionPlayerTurnIndex = jsonObject.getIntValue("optionPlayerTurnIndex")
    }

    private fun parseMap(json: String) {
        val jsonArray = JSON.parseArray(json)
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

    private fun parsePlayer(json: String) {
        val jsonArray = JSON.parseArray(json)
        for (i in 0 until jsonArray.size) {
            val jsonObject = jsonArray.getJSONObject(i)
            playerData.add(PlayerBean(jsonObject))
        }
    }


    fun currentPlayer(): PlayerBean {
        return playerData[optionPlayerTurnIndex]
    }

    fun currentMap(): BaseMapBean {
        return mapData[currentPlayer().walkIndex]
    }
}