package com.hzw.android.richman.init

import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.BaseMapBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.SpecialBean

/**
 * class InitMap
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
class Init private constructor() {

    companion object {
        val INSTANCE: Init by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Init()
        }
    }

    fun map(): MutableList<BaseMapBean> {
        val mapList = mutableListOf<BaseMapBean>()

        mapList.add(SpecialBean(BaseMapBean.MapType.START))
        mapList.add(CityBean("许昌", 1500, CityBean.Color.A))
        mapList.add(CityBean("新野", 1000, CityBean.Color.A))
        mapList.add(CityBean("桂阳", 1000, CityBean.Color.A))
        mapList.add(CityBean("洛阳", 2000, CityBean.Color.A))
        mapList.add(CityBean("下邳", 1000, CityBean.Color.B))
        mapList.add(CityBean("徐州", 1000, CityBean.Color.B))
        mapList.add(CityBean("濮阳", 1500, CityBean.Color.B))
        mapList.add(CityBean("庐江", 1500, CityBean.Color.B))
        mapList.add(CityBean("平原", 1500, CityBean.Color.C))
        mapList.add(CityBean("晋阳", 1000, CityBean.Color.C))
        mapList.add(CityBean("代县", 1000, CityBean.Color.C))
        mapList.add(CityBean("南皮", 1000, CityBean.Color.C))
        mapList.add(CityBean("成都", 2000, CityBean.Color.D))
        mapList.add(CityBean("汉中", 1500, CityBean.Color.D))
        mapList.add(CityBean("西凉", 1000, CityBean.Color.D))
        mapList.add(CityBean("天水", 1000, CityBean.Color.D))
        mapList.add(CityBean("长沙", 2000, CityBean.Color.E))
        mapList.add(CityBean("永安", 1000, CityBean.Color.E))
        mapList.add(CityBean("江陵", 1500, CityBean.Color.E))
        mapList.add(CityBean("建业", 1000, CityBean.Color.E))
        mapList.add(AreaBean("赤壁"))
        mapList.add(AreaBean("五丈原"))
        mapList.add(AreaBean("官渡"))
        mapList.add(AreaBean("合肥"))
        mapList.add(AreaBean("黄巾"))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(SpecialBean(BaseMapBean.MapType.SHOP))
        mapList.add(SpecialBean(BaseMapBean.MapType.SHOP))
        mapList.add(SpecialBean(BaseMapBean.MapType.MONEY))
        mapList.add(SpecialBean(BaseMapBean.MapType.MONEY))
        mapList.add(SpecialBean(BaseMapBean.MapType.MONEY))
        mapList.add(SpecialBean(BaseMapBean.MapType.MONEY))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(SpecialBean(BaseMapBean.MapType.PRISON))
        mapList.add(SpecialBean(BaseMapBean.MapType.BIG_MONEY))
        mapList.add(SpecialBean(BaseMapBean.MapType.FREE_GENERALS))


        return mapList
    }
}