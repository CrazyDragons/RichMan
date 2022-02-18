package com.hzw.android.richman.bean

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import java.math.BigDecimal
import kotlin.math.pow

/**
 * class PlayerBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
class PlayerBean(//昵称
    var name: String,
    var buff: BUFF,
    var isPlayer: Boolean
) {

    //id
    var id = 0

    //金钱
    var money = Value.DEFAULT_MONEY

    //兵力
    var army = Value.DEFAULT_ARMY

    //武将
    var generals = mutableListOf<GeneralsBean>()

    //城池
    var city = mutableListOf<BaseCityBean>()

    //道具
    var equipments = mutableListOf<EquipmentBean>()

    var stocks = mutableListOf<StockBean>()

    //地图位置
    var walkIndex = 0

    //状态
    var status = STATUS.READY

    var bank = BANK.ABC

    enum class STATUS {
        //准备状态
        READY,

        //操作状态
        OPTION_FALSE,

        //操作状态
        OPTION_TRUE,

        //攻击状态
        ATTACK,

        //监狱状态
        PRISON,

        //负债
        LOSER
    }

    enum class BUFF {
        ADD_COST,
        REDUCE_COST,
        ADD_ATTACK,
        ADD_DEFENSE,
        ADD_ATTACK_ARMY,
        REDUCE_ATTACK_ARMY,
        ADD_MONEY,
        ADD_ARMY,
        ADD_CITY,
        ADD_GENERALS,
        ADD_EQUIPMENTS,
        ADD_STOCK
    }

    enum class BANK {
        ICBC, ABC, CCB, BOC, BOCM
    }


    fun loadBuff(){
        if (buff == BUFF.ADD_MONEY) {
            money += (money*0.5).toInt()
        }
        if (buff == BUFF.ADD_ARMY) {
            army += (army*0.2).toInt()
        }
        if (buff == BUFF.ADD_STOCK) {
            stocks.add(StockBean("A", 50))
        }
        if (buff == BUFF.ADD_CITY) {
            val baseCityBean = GameData.INSTANCE.mapData[1] as BaseCityBean
            baseCityBean.owner = this
            city.add(baseCityBean)
        }
        if (buff == BUFF.ADD_GENERALS) {
            GameData.INSTANCE.giveGenerals(this, 2)
        }
        if (buff == BUFF.ADD_EQUIPMENTS) {
            GameData.INSTANCE.giveEquipments(this, 2)
        }
    }


    private fun getSingleGDP(playerBean: PlayerBean): Double {
        return playerBean.money + playerBean.army * 2 +
                getCityMoney(playerBean.city) + playerBean.allGenerals().size * 2000 + playerBean.equipments.size * 2000
    }

    private fun getCityMoney(arrayList: MutableList<BaseCityBean>): Double {
        var money = 0.0
        for (i in arrayList.indices) {

            if (arrayList[i] is CityBean) {
                money += arrayList[i].buyPrice * 2.0.pow((arrayList[i] as CityBean).level.toDouble())
            }

        }
        return money
    }

    fun allGenerals(): MutableList<GeneralsBean> {
        val list = mutableListOf<GeneralsBean>()
        for (item in city) {
            if (item.generals != null) {
                list.add(item.generals!!)
            }
        }
        list.addAll(generals)
        return list
    }

    fun allAttackGenerals(): MutableList<GeneralsBean> {
        val list = mutableListOf<GeneralsBean>()
        for (item in generals) {
            if (item.action > Value.ACTION_ATTACK) {
                list.add(item)
            }
        }
        return list
    }

    fun allAreaCostMoney(): Int {
        if (status == STATUS.PRISON) {
            return 0
        } else {
            val buff = if (buff == BUFF.ADD_COST) 1.1 else 1.0
            val deBuff = if (GameData.INSTANCE.currentPlayer().buff == BUFF.REDUCE_COST) 0.9 else 1.0
            return when (allArea()) {
                1 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_1 * buff * deBuff).toInt()
                2 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_2 * buff * deBuff).toInt()
                3 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_3 * buff * deBuff).toInt()
                4 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_4 * buff * deBuff).toInt()
                5 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_5 * buff * deBuff).toInt()
                else -> {
                    0
                }
            }
        }
    }

    fun allAreaCostArmy(): Int {
        val isPrison = if (status == STATUS.PRISON) 0.5 else 1.0
        val x = if (allArea() == 5) Value.X_ALL_COLOR_ARMY else 1.0
        val buff = if (buff == BUFF.ADD_ATTACK_ARMY) 1.1 else 1.0
        val deBuff = if (GameData.INSTANCE.currentPlayer().buff == BUFF.REDUCE_ATTACK_ARMY) 0.9 else 1.0
        return when (allArea()) {
            1 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_1 * x * isPrison * buff * deBuff).toInt()
            2 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_2 * x * isPrison * buff * deBuff).toInt()
            3 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_3 * x * isPrison * buff * deBuff).toInt()
            4 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_4 * x * isPrison * buff * deBuff).toInt()
            5 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_5 * x * isPrison * buff * deBuff).toInt()
            else -> {
                0
            }
        }
    }

    fun allArea(): Int {
        var x = 0
        for (item in city) {
            if (item is AreaBean) {
                x += 1
            }
        }
        return x
    }

    fun GDP(): String {
        val x: Double
        var sum = 0.0
        for (i in GameData.INSTANCE.playerData) {
            sum += getSingleGDP(i)
        }
        x = getSingleGDP(this) / sum
        return (getDouble2(x) * 100).toInt().toString() + "%"
    }

    private fun getDouble2(value: Double): Double {
        return BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun stockMoney():Int{
        var sum = 0
        for (item in GameData.INSTANCE.stocksData) {
            sum += stockNumber(item.name) * item.newPrice
        }
        return sum
    }

    fun stockNumber(name: String): Int {
        for (item in stocks) {
            if (item.name == name) {
                return item.number
            }
        }
        return 0
    }

    fun stockNumber(): Int {
        var s = 0
        for (item in stocks) {
           s += item.number
        }
        return s
    }

    fun buyStock(stockBean: StockBean, number: Int) {

        if (haveStock(stockBean)) {
            for (item in stocks) {
                if (item.name == stockBean.name) {
                    item.number += number
                }
            }
        } else {
            stocks.add(StockBean(stockBean.name, number))
        }


        money -= stockBean.newPrice * number
    }

    fun saleStock(stockBean: StockBean, number: Int) {
        for (item in stocks) {
            if (item.name == stockBean.name) {
                item.number -= number
            }
        }
        money += stockBean.newPrice * number
    }

    private fun haveStock(stockBean: StockBean): Boolean {
        for (item in stocks) {
            if (item.name == stockBean.name) {
                return true
            }
        }
        return false
    }
}