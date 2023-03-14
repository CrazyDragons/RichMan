package com.hzw.android.richman.base

/**
 * class BaseMapBean
 *
 * @author CrazyDragon
 * description 基础地图类
 * note
 * create date 2022/2/9
 */
open class BaseMapBean {
    enum class MapType {
        //起点
        START,

        //城池
        CITY,

        //战区
        AREA,

        //兵力
        ARMY,

        //职业介绍所
        GENERALS,

        //机会
        CHANCE,

        //商店
        SHOP,

        //银行
        BANK,

        //金银岛
        BIG_MONEY,

        //茅庐
        FREE_GENERALS,

        //监狱
        PRISON
    }

    var id = 0

    /**
     * 地图类型
     */
    var type: MapType? = null

    /**
     * 地图名
     */
    var name: String? = null
}