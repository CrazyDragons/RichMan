package com.hzw.android.richman.base

import com.hzw.android.richman.bean.GeneralsBean
import com.hzw.android.richman.bean.PlayerBean

/**
 * class BaseCityBean
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
open class BaseCityBean : BaseMapBean() {

    //城池级别
    var level = 0

    //购入价
    var buyPrice = 0

    //城池封面
    var cover = 0

    //武将
    var generals: GeneralsBean? = null

    //拥有者
    var owner: PlayerBean? = null
}