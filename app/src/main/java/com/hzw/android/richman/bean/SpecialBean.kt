package com.hzw.android.richman.bean

import androidx.annotation.DrawableRes
import com.alibaba.fastjson.JSONObject
import com.hzw.android.richman.MyApplication
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseMapBean

/**
 * class MapBean
 *
 * @author CrazyDragon
 * description 特殊地图实体类
 * note
 * create date 2022/2/9
 */
class SpecialBean : BaseMapBean {

    @DrawableRes
    var background = 0

    constructor(jsonObject: JSONObject) {
        name = jsonObject.getString("name")
        type = MapType.valueOf(jsonObject.getString("type"))
        background = jsonObject.getIntValue("background")
    }

    constructor(mapType: MapType) {
        type = mapType
        if (mapType == MapType.START) {
            name = MyApplication.getContext().resources.getString(R.string.start)
            background = R.drawable.bg_start
        }
        if (mapType == MapType.ARMY) {
            name = MyApplication.getContext().resources.getString(R.string.army)
            background = R.drawable.bg_army
        }
        if (mapType == MapType.GENERALS) {
            name = MyApplication.getContext().resources.getString(R.string.generals)
            background = R.drawable.bg_generals
        }
        if (mapType == MapType.CHANCE) {
            name = MyApplication.getContext().resources.getString(R.string.chance)
            background = R.drawable.bg_chance
        }
        if (mapType == MapType.SHOP) {
            name = MyApplication.getContext().resources.getString(R.string.shop)
            background = R.drawable.bg_shop
        }
        if (mapType == MapType.BANK) {
            name = MyApplication.getContext().resources.getString(R.string.bank)
            background = R.drawable.bg_bank
        }
        if (mapType == MapType.BIG_MONEY) {
            name = MyApplication.getContext().resources.getString(R.string.big_bank)
            background = R.drawable.bg_big_money
        }
        if (mapType == MapType.FREE_GENERALS) {
            name = MyApplication.getContext().resources.getString(R.string.free_generals)
            background = R.drawable.bg_free_generals
        }
        if (mapType == MapType.PRISON) {
            name = MyApplication.getContext().resources.getString(R.string.prison)
            background = R.drawable.bg_prison
        }
    }
}