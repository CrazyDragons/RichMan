package com.hzw.android.richman.bean

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.config.Value.AREA_ARMY
import com.hzw.android.richman.utils.MapUtil

/**
 * class AreaBean
 *
 * @author CrazyDragon
 * description 战区实体类
 * note
 * create date 2022/2/9
 */
class AreaBean : BaseCityBean {

    //兵力
    var army = AREA_ARMY

    var recover = true

    constructor(jsonObject: JSONObject) {
        id = jsonObject.getIntValue("id")
        name = jsonObject.getString("name")
        army = jsonObject.getIntValue("army")
        type = MapType.valueOf(jsonObject.getString("type"))
        cover = jsonObject.getIntValue("cover")
        buyPrice = army * 2
        ownerID = jsonObject.getIntValue("ownerID")
        generals = JSON.parseObject(jsonObject.getString("generals"), GeneralsBean::class.java)

    }

    constructor(name: String?) {
        this.name = name
        type = MapType.AREA
    }

    fun defense():Int {
        return if (generals != null) generals!!.defense() else 0  + (if (owner() != null) owner()!!.allArea() * Value.ADD_DEFENSE else 0) + (if (MapUtil.judgeAllColor(this)) Value.ALL_COLOR_DEFENSE else 0)
    }
}