package com.hzw.android.richman.bean

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import java.math.BigDecimal
import java.util.*
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
    var name: String?,//是否是玩家
    var isPlayer: Boolean
) {

    //id
    var id = 0

    //金钱
    var money = Value.DEFAULT_MONEY

    //兵力
    var army = Value.DEFAULT_ARMY

    //武将
    var generals = ArrayList<GeneralsBean>()

    //城池
    var city = ArrayList<BaseCityBean>()

    //道具
    var equipments = ArrayList<EquipmentBean>()

    //地图位置
    var walkIndex = 0

    //状态
    var status = STATUS.READY

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
        PRISON
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

    fun allAttackGenerals():MutableList<GeneralsBean> {
        val list = mutableListOf<GeneralsBean>()
        for (item in generals) {
            if (item.action > Value.ACTION_ATTACK) {
                list.add(item)
            }
        }
        return list
    }

    fun allAreaCostMoney(): Int {
        return when (allArea()) {
            1 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_1)
            2 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_2)
            3 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_3)
            4 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_4)
            5 -> return (Value.AREA_ARMY * Value.X_AREA_MONEY_LEVEL_5)
            else -> {
                0
            }
        }
    }

    fun allAreaCostArmy(): Int {
        return when (allArea()) {
            1 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_1)
            2 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_2)
            3 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_3)
            4 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_4)
            5 -> return (Value.AREA_ARMY * Value.X_AREA_ARMY_LEVEL_5)
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


}