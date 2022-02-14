package com.hzw.android.richman.bean

/**
 * class EquipmentBean
 *
 * @author CrazyDragon
 * description 道具实体类
 * note
 * create date 2022/2/12
 */
class EquipmentBean(var type: TYPE) {

    enum class TYPE {
        A,B,C,D
    }

    var name = ""

    var desc = ""

    var owner:PlayerBean? = null

    init {
        when(type) {
            TYPE.A -> {
                name = "道具1"
                desc = "道具1的说明 巴拉巴拉小魔仙"
            }
            TYPE.B -> {
                name = "道具2"
                desc = "道具1的说明 巴拉巴拉小魔仙"
            }
            TYPE.C -> {
                name = "道具3"
                desc = "道具1的说明 巴拉巴拉小魔仙"
            }
            TYPE.D -> {
                name = "道具4"
                desc = "道具1的说明 巴拉巴拉小魔仙"
            }
        }
    }

}