package com.hzw.android.richman.bean

import com.hzw.android.richman.game.GameData

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

    var price = 1000

    var ownerID = 0

    init {
        when(type) {
            TYPE.A -> {
                name = "道具1"
                desc = "道具1的说明"
            }
            TYPE.B -> {
                name = "道具2"
                desc = "道具2的说明"
            }
            TYPE.C -> {
                name = "道具3"
                desc = "道具2的说明"
            }
            TYPE.D -> {
                name = "道具4"
                desc = "道具4的说明"
            }
        }
    }

    fun owner():PlayerBean? {
        for (item in GameData.INSTANCE.playerData) {
            if (ownerID == item.id) {
                return item
            }
        }
        return null
    }

}