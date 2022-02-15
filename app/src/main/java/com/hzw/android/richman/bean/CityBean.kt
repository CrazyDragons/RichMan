package com.hzw.android.richman.bean

import androidx.annotation.DrawableRes
import com.alibaba.fastjson.JSONObject
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.config.Value

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
        name = jsonObject.getString("name")
        buyPrice = jsonObject.getIntValue("buyPrice")
        type = MapType.valueOf(jsonObject.getString("type"))
        color = Color.valueOf(jsonObject.getString("color"))
        cover = jsonObject.getIntValue("cover")
    }

    constructor(name: String?, @DrawableRes cover: Int, buyPrice: Int, color: Color?) {
        this.name = name
        this.cover = cover
        this.buyPrice = buyPrice
        type = MapType.CITY
        this.color = color
    }

    fun needCostMoney():Int {
        return when(level) {
            0 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_0).toInt()
            1 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_1)
            2 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_2)
            3 -> (buyPrice * Value.X_CITY_MONEY_LEVEL_3)
            else -> 0
        }
    }

    fun needCostArmy():Int {
        return when(level) {
            0 -> (buyPrice * Value.X_CITY_ARMY_LEVEL_0).toInt()
            1 -> (buyPrice * Value.X_CITY_ARMY_LEVEL_1)
            2 -> (buyPrice * Value.X_CITY_ARMY_LEVEL_2)
            3 -> (buyPrice * Value.X_CITY_ARMY_LEVEL_3)
            else -> 0
        }
    }
}