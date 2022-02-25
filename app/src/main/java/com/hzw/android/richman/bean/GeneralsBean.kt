package com.hzw.android.richman.bean

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.game.GameData

/**
 * class GeneralBean
 *
 * @author CrazyDragon
 * description 武将实体类
 * note
 * create date 2022/2/12
 */
class GeneralsBean {

    var name = ""

    var ownerID = 0

    var cityID = 0

    var cover = 0

    var life = 0
        get() = field + if (owner() != null && !owner()!!.isPlayer) 2 else 0

    var action = 0

    var attack = 0
        get() = field +
                if (owner() != null && owner()!!.buff == PlayerBean.BUFF.ADD_ATTACK) 5 else 0 +
                        if (owner() != null && !owner()!!.isPlayer) 10 else 0

    var defense = 0
        get() = field +
                if (owner() != null && owner()!!.buff == PlayerBean.BUFF.ADD_DEFENSE) 5 else 0 +
                        if (owner() != null && !owner()!!.isPlayer) 10 else 0

    constructor() {}

    constructor(name: String, cover: Int, life: Int, attack: Int, defense: Int) {
        this.name = name
        this.cover = cover
        this.life = life
        this.action = life
        this.attack = attack
        this.defense = defense
    }

    fun city(): BaseCityBean? {
        for (item in GameData.INSTANCE.mapData) {
            if (cityID == item.id && item is BaseCityBean)
                return item
        }
        return null
    }

    fun owner(): PlayerBean? {
        for (item in GameData.INSTANCE.playerData) {
            if (ownerID == item.id) {
                return item
            }
        }
        return null
    }


}