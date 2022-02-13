package com.hzw.android.richman.bean

import com.hzw.android.richman.config.Value
import java.util.*

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
    var generals = ArrayList<GeneralBean>()

    //城池
    var city = ArrayList<CityBean>()

    //道具
    var equipments = ArrayList<EquipmentBean>()

    //地图位置
    var walkIndex = 0

    //是否是当前回合
    var isTurn = false


}