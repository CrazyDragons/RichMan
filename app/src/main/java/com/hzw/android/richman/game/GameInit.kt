package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.SpecialBean

/**
 * class GameInit
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
class GameInit private constructor() {

    companion object {
        val INSTANCE: GameInit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameInit()
        }
    }

    fun map(): MutableList<BaseMapBean> {
        val mapList = mutableListOf<BaseMapBean>()

        mapList.add(SpecialBean(BaseMapBean.MapType.START))
        mapList.add(CityBean("许昌", R.drawable.bg_xizang,1500, CityBean.Color.A))
        mapList.add(CityBean("新野", R.drawable.bg_xizang,1000, CityBean.Color.A))
        mapList.add(CityBean("桂阳", R.drawable.bg_xizang,1000, CityBean.Color.A))
        mapList.add(CityBean("洛阳", R.drawable.bg_xizang,2000, CityBean.Color.A))
        mapList.add(CityBean("下邳", R.drawable.bg_xizang,1000, CityBean.Color.B))
        mapList.add(CityBean("徐州", R.drawable.bg_xizang,1000, CityBean.Color.B))
        mapList.add(CityBean("濮阳", R.drawable.bg_xizang,1500, CityBean.Color.B))
        mapList.add(CityBean("庐江", R.drawable.bg_xizang,1500, CityBean.Color.B))
        mapList.add(CityBean("平原", R.drawable.bg_xizang,1500, CityBean.Color.C))
        mapList.add(CityBean("晋阳", R.drawable.bg_xizang,1000, CityBean.Color.C))
        mapList.add(CityBean("代县", R.drawable.bg_xizang,1000, CityBean.Color.C))
        mapList.add(CityBean("南皮", R.drawable.bg_xizang,1000, CityBean.Color.C))
        mapList.add(CityBean("成都", R.drawable.bg_xizang,2000, CityBean.Color.D))
        mapList.add(CityBean("汉中", R.drawable.bg_xizang,1500, CityBean.Color.D))
        mapList.add(CityBean("西凉", R.drawable.bg_xizang,1000, CityBean.Color.D))
        mapList.add(CityBean("天水", R.drawable.bg_xizang,1000, CityBean.Color.D))
        mapList.add(CityBean("长沙", R.drawable.bg_xizang,2000, CityBean.Color.E))
        mapList.add(CityBean("永安", R.drawable.bg_xizang,1000, CityBean.Color.E))
        mapList.add(CityBean("江陵", R.drawable.bg_xizang,1500, CityBean.Color.E))
        mapList.add(CityBean("建业", R.drawable.bg_xizang,1000, CityBean.Color.E))
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

        GameSave.INSTANCE.saveMap(JSON.toJSONString(mapList))

        return mapList
    }
}