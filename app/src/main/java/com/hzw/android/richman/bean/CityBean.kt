package com.hzw.android.richman.bean

import androidx.annotation.DrawableRes
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.utils.MapUtil

/**
 * class CityBean
 *
 * @author CrazyDragon
 * description 城池实体类
 * note
 * create date 2022/2/9
 */
class CityBean : BaseCityBean {

    //城池颜色
    var color: Color? = null

    //城池级别
    var level = 0

    var defense = 0
        get() = field + level * Value.ADD_DEFENSE + if (MapUtil.judgeAllColor(this)) Value.ALL_COLOR_DEFENSE else 0

    enum class Color {
        //红
        RED,

        //橙
        ORANGE,

        //黄
        YELLOW,

        //绿
        GREEN,

        //青
        QING,

        //蓝
        BLUE,

        //紫
        PURPLE
    }


    constructor(jsonObject: JSONObject) {
        id = jsonObject.getIntValue("id")
        ownerID = jsonObject.getIntValue("ownerID")
        name = jsonObject.getString("name")
        buyPrice = jsonObject.getIntValue("buyPrice")
        generals = JSON.parseObject(jsonObject.getString("generals"), GeneralsBean::class.java)
        type = MapType.valueOf(jsonObject.getString("type"))
        color = Color.valueOf(jsonObject.getString("color"))
        cover = jsonObject.getIntValue("cover")
        level = jsonObject.getIntValue("level")
        defense = jsonObject.getIntValue("defense")
    }

    constructor(name: String?, @DrawableRes cover: Int, buyPrice: Int, color: Color?) {
        this.name = name
        this.cover = cover
        this.buyPrice = buyPrice
        type = MapType.CITY
        this.color = color
    }

    fun needCostMoney(): Int {
        return if (owner()!!.status == PlayerBean.STATUS.PRISON) {
            0
        }else {
            val color = if (MapUtil.judgeAllColor(this)) Value.X_ALL_COLOR_MONEY else 1
            val buff = if (owner()!!.buff == PlayerBean.BUFF.ADD_COST) 1.1 else 1.0
            val deBuff = if (GameData.INSTANCE.currentPlayer().buff == PlayerBean.BUFF.REDUCE_COST) 0.9 else 1.0
            when (level) {
                0 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_0 * color * buff * deBuff).toInt()
                1 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_1 * color * buff * deBuff).toInt()
                2 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_2 * color * buff * deBuff).toInt()
                3 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_3 * color * buff * deBuff).toInt()
                else -> 0
            }
        }
    }

    fun needCostArmy(): Int {
        val isPrison = if (owner()!!.status == PlayerBean.STATUS.PRISON) 0.5 else 1.0
        val color = if (MapUtil.judgeAllColor(this)) Value.X_ALL_COLOR_ARMY else 1.0
        val buff = if (owner()!!.buff == PlayerBean.BUFF.ADD_ATTACK_ARMY) 1.1 else 1.0
        val deBuff = if (GameData.INSTANCE.currentPlayer().buff == PlayerBean.BUFF.REDUCE_ATTACK_ARMY) 0.9 else 1.0
        return when (level) {
            0 -> (Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_0 * color * isPrison * buff * deBuff).toInt()
            1 -> (Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_1 * color * isPrison * buff * deBuff).toInt()
            2 -> (Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_2 * color * isPrison * buff * deBuff).toInt()
            3 -> (Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_3 * color * isPrison * buff * deBuff).toInt()
            else -> 0
        }
    }
}