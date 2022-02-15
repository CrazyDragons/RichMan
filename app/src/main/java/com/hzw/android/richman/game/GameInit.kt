package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.*

/**
 * class GameInit
 * @author CrazyDragon
 * description 游戏初始化数据
 * note
 * create date 2022/2/9
 */
class GameInit private constructor() {

    var mapList = mutableListOf<BaseMapBean>()
    var generals = mutableListOf<GeneralsBean>()
    var equipments = mutableListOf<EquipmentBean>()

    companion object {
        val INSTANCE: GameInit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameInit()
        }
    }

    init {
        initMap()
        initGenerals()
        initEquipments()
    }

    private fun initMap() {
        mapList.add(SpecialBean(BaseMapBean.MapType.START))
        mapList.add(CityBean("安徽", R.drawable.bg_anhui, 1500, CityBean.Color.RED))
        mapList.add(AreaBean("东部战区"))
        mapList.add(AreaBean("西部战区"))
        mapList.add(CityBean("澳门", R.drawable.bg_aomen, 1000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.BIG_MONEY))
        mapList.add(CityBean("北京", R.drawable.bg_beijing, 1000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.FREE_GENERALS))
        mapList.add(CityBean("重庆", R.drawable.bg_chongqing, 2000, CityBean.Color.RED))
        mapList.add(CityBean("福建", R.drawable.bg_fujian, 1000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.SHOP))
        mapList.add(CityBean("甘肃", R.drawable.bg_gansu, 1000, CityBean.Color.ORANGE))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(CityBean("广东", R.drawable.bg_guangdong, 1500, CityBean.Color.ORANGE))
        mapList.add(CityBean("广西", R.drawable.bg_guangxi, 1500, CityBean.Color.ORANGE))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("贵州", R.drawable.bg_guizhou, 1500, CityBean.Color.ORANGE))
        mapList.add(CityBean("海南", R.drawable.bg_hainan, 1000, CityBean.Color.ORANGE))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(CityBean("河北", R.drawable.bg_hebei, 1000, CityBean.Color.YELLOW))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(CityBean("黑龙", R.drawable.bg_heilongjiang, 1000, CityBean.Color.YELLOW))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("河南", R.drawable.bg_henan, 2000, CityBean.Color.YELLOW))
        mapList.add(CityBean("湖北", R.drawable.bg_hubei, 1500, CityBean.Color.YELLOW))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("湖南", R.drawable.bg_hunan, 1000, CityBean.Color.YELLOW))
        mapList.add(AreaBean("南部战区"))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("江苏", R.drawable.bg_jiangsu, 1000, CityBean.Color.GREEN))
        mapList.add(CityBean("江西", R.drawable.bg_jiangxi, 2000, CityBean.Color.GREEN))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("吉林", R.drawable.bg_jilin, 1000, CityBean.Color.GREEN))
        mapList.add(CityBean("辽宁", R.drawable.bg_liaoning, 1500, CityBean.Color.GREEN))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(CityBean("内蒙", R.drawable.bg_neimenggu, 1000, CityBean.Color.GREEN))
        mapList.add(CityBean("宁夏", R.drawable.bg_ningxia, 1000, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("青海", R.drawable.bg_qinghai, 1000, CityBean.Color.QING))
        mapList.add(CityBean("山西", R.drawable.bg_shan1xi, 1000, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.SHOP))
        mapList.add(AreaBean("北部战区"))
        mapList.add(CityBean("陕西", R.drawable.bg_shan3xi, 1000, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("山东", R.drawable.bg_shandong, 1000, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("上海", R.drawable.bg_shanghai, 1000, CityBean.Color.BLUE))
        mapList.add(CityBean("四川", R.drawable.bg_sichuan, 1000, CityBean.Color.BLUE))
        mapList.add(SpecialBean(BaseMapBean.MapType.PRISON))
        mapList.add(CityBean("台湾", R.drawable.bg_taiwan, 1000, CityBean.Color.BLUE))
        mapList.add(CityBean("天津", R.drawable.bg_tianjin, 1000, CityBean.Color.BLUE))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("香港", R.drawable.bg_xianggang, 1000, CityBean.Color.BLUE))
        mapList.add(CityBean("新疆", R.drawable.bg_xinjiang, 1000, CityBean.Color.PURPLE))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(AreaBean("中部战区"))
        mapList.add(CityBean("西藏", R.drawable.bg_xizang, 1000, CityBean.Color.PURPLE))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(CityBean("云南", R.drawable.bg_yunnan, 1000, CityBean.Color.PURPLE))
        mapList.add(CityBean("浙江", R.drawable.bg_zhejiang, 1000, CityBean.Color.PURPLE))

        GameSave.saveMap(JSON.toJSONString(mapList))
    }

    private fun initGenerals() {

        for (i in 1 .. 50) {
            generals.add(GeneralsBean("将$i", R.drawable.icon_test, 1 + (Math.random()*80).toInt()/20, (Math.random()*100).toInt(), (Math.random()*100).toInt()))
        }

    }

    private fun initEquipments() {
        equipments.add(EquipmentBean(EquipmentBean.TYPE.A))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.B))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.C))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.D))
    }
}