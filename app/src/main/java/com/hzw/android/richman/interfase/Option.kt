package com.hzw.android.richman.interfase

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.EquipmentBean
import com.hzw.android.richman.bean.GeneralsBean

/**
 * class Option
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
interface Option {
    fun buyBaseCity(baseCityBean: BaseCityBean)
    fun levelCity(cityBean: CityBean, needFinish:Boolean)
    fun costBaseCity(baseCityBean: BaseCityBean, needFinish:Boolean)
    fun defense(baseCityBean: BaseCityBean, generalsBean: GeneralsBean?, needFinish:Boolean)
    fun pk(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, needFinish: Boolean)
    fun attack(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, needFinish: Boolean)

    fun buyArmy(army:Int)
    fun buyGenerals()
    fun bigMoney(count:Int)
    fun freeGenerals(count: Int)
    fun bank(count: Int)
    fun shop(equipmentBean: EquipmentBean)
    fun chance(count: Int)
    fun prison(count: Int)
}