package com.hzw.android.richman.base

import com.hzw.android.richman.bean.GeneralBean
import com.hzw.android.richman.bean.PlayerBean

/**
 * class BaseCityBean
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
open class BaseCityBean : BaseMapBean(){

    //购入价
    var buyPrice = 0

    //城池封面
    var cover = 0

    //武将
    var general: GeneralBean? = null

    //拥有者
    var owner: PlayerBean? = null
}