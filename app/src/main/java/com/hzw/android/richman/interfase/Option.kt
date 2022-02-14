package com.hzw.android.richman.interfase

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.GeneralBean

/**
 * class Option
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
interface Option {
    fun buyBaseCity(baseCityBean: BaseCityBean)
    fun levelCity(cityBean: CityBean)
    fun costBaseCity(baseCityBean: BaseCityBean)
    fun defense(baseCityBean: BaseCityBean, generalBean: GeneralBean?)
}