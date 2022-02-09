package com.hzw.android.richman.date

import com.hzw.android.richman.bean.BaseMapBean

/**
 * class Date
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
class Data private constructor() {

    var mapList = mutableListOf<BaseMapBean>()

    companion object {
        val INSTANCE: Data by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Data()
        }
    }


}