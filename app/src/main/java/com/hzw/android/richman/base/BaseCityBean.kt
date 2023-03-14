package com.hzw.android.richman.base

import com.hzw.android.richman.bean.GeneralsBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.game.GameData

/**
 * class BaseCityBean
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
open class BaseCityBean : BaseMapBean() {

    //购入价
    var buyPrice = 0

    //城池封面
    var cover = 0

    //武将
    var generals: GeneralsBean? = null

    var ownerID = 0

    fun owner():PlayerBean? {
        for (item in GameData.INSTANCE.playerData) {
            if (ownerID == item.id) {
                return item
            }
        }
        return null
    }


}