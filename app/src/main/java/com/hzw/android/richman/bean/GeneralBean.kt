package com.hzw.android.richman.bean

import com.hzw.android.richman.base.BaseCityBean

/**
 * class GeneralBean
 *
 * @author CrazyDragon
 * description 武将实体类
 * note
 * create date 2022/2/12
 */
class GeneralBean {

    var name = ""

    var owner: PlayerBean? = null

    var city: BaseCityBean? = null

    var cover = 0

    var life = 0

    var action = 0

    var attack = 0

    var defense = 0

    constructor(){}

    constructor(name: String, cover: Int, life: Int, attack: Int, defense: Int) {
        this.name = name
        this.cover = cover
        this.life = life
        this.action = life
        this.attack = attack
        this.defense = defense
    }
}