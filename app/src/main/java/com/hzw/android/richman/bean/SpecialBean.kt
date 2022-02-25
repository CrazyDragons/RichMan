package com.hzw.android.richman.bean

import androidx.annotation.DrawableRes
import com.alibaba.fastjson.JSONObject
import com.hzw.android.richman.MyApplication
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.config.Value

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

    var desc = ""

    constructor(jsonObject: JSONObject) {
        name = jsonObject.getString("name")
        type = MapType.valueOf(jsonObject.getString("type"))
        desc = jsonObject.getString("desc")
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
            desc = "以每名小兵"+Value.X_ARMY_MONEY+"两的价格征兵"
        }
        if (mapType == MapType.GENERALS) {
            name = MyApplication.getContext().resources.getString(R.string.generals)
            background = R.drawable.bg_generals
            desc = "每次以"+Value.X_BUY_GENERALS +"价格随机获得1-2名武将（大于10点获得2名）"
        }
        if (mapType == MapType.CHANCE) {
            name = MyApplication.getContext().resources.getString(R.string.chance)
            background = R.drawable.bg_chance
            desc = "机会描述"
        }
        if (mapType == MapType.SHOP) {
            name = MyApplication.getContext().resources.getString(R.string.shop)
            background = R.drawable.bg_shop
        }
        if (mapType == MapType.BANK) {
            name = MyApplication.getContext().resources.getString(R.string.bank)
            background = R.drawable.bg_bank
            desc = "投掷一次骰子\n1-3点向其他玩家打款1000\n4-6向银行存1000\n7-9点银行向你发放1000\n10-12其他玩家向你打款1000"
        }
        if (mapType == MapType.BIG_MONEY) {
            name = MyApplication.getContext().resources.getString(R.string.big_bank)
            background = R.drawable.bg_big_money
            desc = "投掷一次骰子\n获取投掷点数 x 1000的金钱"
        }
        if (mapType == MapType.FREE_GENERALS) {
            name = MyApplication.getContext().resources.getString(R.string.free_generals)
            background = R.drawable.bg_free_generals
            desc = "投掷一次骰子\n1-6点得武将1名\n7-9点得2名\n10-11点得3名\n12点得5名"
        }
        if (mapType == MapType.PRISON) {
            name = MyApplication.getContext().resources.getString(R.string.prison)
            background = R.drawable.bg_prison
            desc =  "投掷一次骰子\n1-2点坐牢并罚款5000\n3-10点坐牢\n10-11点得3名\n11-12点无罪释放"
        }
    }
}