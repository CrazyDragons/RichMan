package com.hzw.android.richman.bean

import com.alibaba.fastjson.JSONObject
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.config.Value.AREA_ARMY

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

    constructor(jsonObject: JSONObject) {
        name = jsonObject.getString("name")
        army = jsonObject.getIntValue("army")
        type = MapType.valueOf(jsonObject.getString("type"))
        cover = jsonObject.getIntValue("cover")
        buyPrice = army * 2
    }

    constructor(name: String?) {
        this.name = name
        type = MapType.AREA
    }
}