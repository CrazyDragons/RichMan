package com.hzw.android.richman.bean

/**
 * class EquipmentBean
 *
 * @author CrazyDragon
 * description 道具实体类
 * note
 * create date 2022/2/12
 */
class EquipmentBean {

    var name = NAME.A

    var desc = ""

    constructor(name: NAME, desc: String) {
        this.name = name
        this.desc = desc
    }

    enum class NAME {
        A,B,C,D
    }


}