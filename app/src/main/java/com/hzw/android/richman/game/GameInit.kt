package com.hzw.android.richman.game

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
    var stocks = mutableListOf<StockBean>()

    companion object {
        val INSTANCE: GameInit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameInit()
        }
    }

    init {
        initMap()
        initGenerals()
        initEquipments()
        initStocks()
    }

    private fun initMap() {
        mapList.add(SpecialBean(BaseMapBean.MapType.START))
        mapList.add(CityBean("安徽", R.drawable.bg_anhui, 3000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(AreaBean("北部战区"))
        mapList.add(CityBean("澳门", R.drawable.bg_aomen, 4000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.BIG_MONEY))
        mapList.add(CityBean("北京", R.drawable.bg_beijing, 5000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.FREE_GENERALS))
        mapList.add(CityBean("重庆", R.drawable.bg_chongqing, 3500, CityBean.Color.RED))
        mapList.add(CityBean("福建", R.drawable.bg_fujian, 3000, CityBean.Color.RED))
        mapList.add(SpecialBean(BaseMapBean.MapType.PRISON))
        mapList.add(AreaBean("东部战区"))
        mapList.add(CityBean("甘肃", R.drawable.bg_gansu, 1000, CityBean.Color.ORANGE))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(CityBean("广东", R.drawable.bg_guangdong, 4500, CityBean.Color.ORANGE))
        mapList.add(CityBean("广西", R.drawable.bg_guangxi, 1500, CityBean.Color.ORANGE))
        mapList.add(SpecialBean(BaseMapBean.MapType.SHOP))
        mapList.add(CityBean("贵州", R.drawable.bg_guizhou, 1500, CityBean.Color.ORANGE))
        mapList.add(AreaBean("西部战区"))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("海南", R.drawable.bg_hainan, 1500, CityBean.Color.ORANGE))
        mapList.add(CityBean("河北", R.drawable.bg_hebei, 3000, CityBean.Color.YELLOW))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(CityBean("黑龙", R.drawable.bg_heilongjiang, 2000, CityBean.Color.YELLOW))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("河南", R.drawable.bg_henan, 3500, CityBean.Color.YELLOW))
        mapList.add(CityBean("湖北", R.drawable.bg_hubei, 3000, CityBean.Color.YELLOW))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("湖南", R.drawable.bg_hunan, 3500, CityBean.Color.YELLOW))
        mapList.add(AreaBean("中部战区"))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(CityBean("江苏", R.drawable.bg_jiangsu, 4000, CityBean.Color.GREEN))
        mapList.add(CityBean("江西", R.drawable.bg_jiangxi, 2000, CityBean.Color.GREEN))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("吉林", R.drawable.bg_jilin, 2000, CityBean.Color.GREEN))
        mapList.add(CityBean("辽宁", R.drawable.bg_liaoning, 2500, CityBean.Color.GREEN))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(CityBean("内蒙", R.drawable.bg_neimenggu, 1500, CityBean.Color.GREEN))
        mapList.add(CityBean("宁夏", R.drawable.bg_ningxia, 1500, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("青海", R.drawable.bg_qinghai, 1000, CityBean.Color.QING))
        mapList.add(CityBean("山西", R.drawable.bg_shan1xi, 2000, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.SHOP))
        mapList.add(CityBean("陕西", R.drawable.bg_shan3xi, 2000, CityBean.Color.QING))
        mapList.add(CityBean("山东", R.drawable.bg_shandong, 3000, CityBean.Color.QING))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("上海", R.drawable.bg_shanghai, 4500, CityBean.Color.BLUE))
        mapList.add(CityBean("四川", R.drawable.bg_sichuan, 3500, CityBean.Color.BLUE))
        mapList.add(SpecialBean(BaseMapBean.MapType.CHANCE))
        mapList.add(CityBean("台湾", R.drawable.bg_taiwan, 3500, CityBean.Color.BLUE))
        mapList.add(CityBean("天津", R.drawable.bg_tianjin, 3000, CityBean.Color.BLUE))
        mapList.add(SpecialBean(BaseMapBean.MapType.BANK))
        mapList.add(CityBean("香港", R.drawable.bg_xianggang, 4000, CityBean.Color.BLUE))
        mapList.add(CityBean("新疆", R.drawable.bg_xinjiang, 1500, CityBean.Color.PURPLE))
        mapList.add(SpecialBean(BaseMapBean.MapType.ARMY))
        mapList.add(AreaBean("南部战区"))
        mapList.add(CityBean("西藏", R.drawable.bg_xizang, 1000, CityBean.Color.PURPLE))
        mapList.add(SpecialBean(BaseMapBean.MapType.GENERALS))
        mapList.add(CityBean("云南", R.drawable.bg_yunnan, 2500, CityBean.Color.PURPLE))
        mapList.add(CityBean("浙江", R.drawable.bg_zhejiang, 4000, CityBean.Color.PURPLE))

        for (i in 0 until  mapList.size) {
            mapList[i].id = i + 1
        }
    }

    private fun initGenerals() {

        //18
        generals.add(GeneralsBean("刘备",  R.drawable.icon_test, 5, 75, 70))
        generals.add(GeneralsBean("关羽",  R.drawable.icon_test, 3, 99, 85))
        generals.add(GeneralsBean("张飞",  R.drawable.icon_test, 3, 98, 80))
        generals.add(GeneralsBean("赵云",  R.drawable.icon_test, 3, 95, 84))
        generals.add(GeneralsBean("马超",  R.drawable.icon_test, 3, 90, 80))
        generals.add(GeneralsBean("黄忠",  R.drawable.icon_test, 4, 84, 75))
        generals.add(GeneralsBean("诸葛亮",  R.drawable.icon_test, 5, 70, 100))
        generals.add(GeneralsBean("魏延",  R.drawable.icon_test, 4, 80, 80))
        generals.add(GeneralsBean("庞统",  R.drawable.icon_test, 6, 65, 98))
        generals.add(GeneralsBean("姜维",  R.drawable.icon_test, 4, 81, 90))
        generals.add(GeneralsBean("关平",  R.drawable.icon_test, 5, 78, 75))
        generals.add(GeneralsBean("关兴",  R.drawable.icon_test, 5, 75, 72))
        generals.add(GeneralsBean("张苞",  R.drawable.icon_test, 5, 75, 72))
        generals.add(GeneralsBean("黄月英",  R.drawable.icon_test, 5, 73, 71))
        generals.add(GeneralsBean("马岱",  R.drawable.icon_test, 5, 77, 74))
        generals.add(GeneralsBean("徐庶",  R.drawable.icon_test, 6, 67, 85))
        generals.add(GeneralsBean("法正",  R.drawable.icon_test, 6, 65, 82))
        generals.add(GeneralsBean("严颜",  R.drawable.icon_test, 5, 78, 73))

        //18
        generals.add(GeneralsBean("曹操",  R.drawable.icon_test, 4, 80, 80))
        generals.add(GeneralsBean("夏侯惇",  R.drawable.icon_test, 3, 92, 84))
        generals.add(GeneralsBean("夏侯渊",  R.drawable.icon_test, 4, 84, 80))
        generals.add(GeneralsBean("徐晃",  R.drawable.icon_test, 3, 90, 88))
        generals.add(GeneralsBean("张辽",  R.drawable.icon_test, 3, 95, 83))
        generals.add(GeneralsBean("张郃",  R.drawable.icon_test, 4, 87, 76))
        generals.add(GeneralsBean("甄宓",  R.drawable.icon_test, 5, 71, 65))
        generals.add(GeneralsBean("典韦",  R.drawable.icon_test, 3, 91, 76))
        generals.add(GeneralsBean("司马懿",  R.drawable.icon_test, 6, 65, 95))
        generals.add(GeneralsBean("许褚",  R.drawable.icon_test, 3, 93, 81))
        generals.add(GeneralsBean("郭嘉",  R.drawable.icon_test, 5, 70, 91))
        generals.add(GeneralsBean("贾诩",  R.drawable.icon_test, 6, 68, 84))
        generals.add(GeneralsBean("庞德",  R.drawable.icon_test, 4, 82, 76))
        generals.add(GeneralsBean("曹丕",  R.drawable.icon_test, 5, 76, 71))
        generals.add(GeneralsBean("曹仁",  R.drawable.icon_test, 5, 72, 73))
        generals.add(GeneralsBean("于禁",  R.drawable.icon_test, 5, 79, 82))
        generals.add(GeneralsBean("李典",  R.drawable.icon_test, 5, 74, 70))
        generals.add(GeneralsBean("荀彧",  R.drawable.icon_test, 6, 69, 81))

        //18
        generals.add(GeneralsBean("孙权",  R.drawable.icon_test, 5, 70, 70))
        generals.add(GeneralsBean("孙策",  R.drawable.icon_test, 4, 80, 72))
        generals.add(GeneralsBean("孙坚",  R.drawable.icon_test, 5, 75, 70))
        generals.add(GeneralsBean("黄盖",  R.drawable.icon_test, 4, 83, 80))
        generals.add(GeneralsBean("大乔",  R.drawable.icon_test, 6, 68, 81))
        generals.add(GeneralsBean("小乔",  R.drawable.icon_test, 5, 70, 81))
        generals.add(GeneralsBean("吕蒙",  R.drawable.icon_test, 4, 89, 82))
        generals.add(GeneralsBean("甘宁",  R.drawable.icon_test, 3, 92, 85))
        generals.add(GeneralsBean("陆逊",  R.drawable.icon_test, 5, 71, 93))
        generals.add(GeneralsBean("孙尚香",  R.drawable.icon_test, 5, 78, 72))
        generals.add(GeneralsBean("周瑜",  R.drawable.icon_test, 5, 77, 91))
        generals.add(GeneralsBean("太史慈",  R.drawable.icon_test, 3, 90, 76))
        generals.add(GeneralsBean("周泰",  R.drawable.icon_test, 4, 82, 82))
        generals.add(GeneralsBean("凌统",  R.drawable.icon_test, 5, 78, 74))
        generals.add(GeneralsBean("鲁肃",  R.drawable.icon_test, 5, 78, 90))
        generals.add(GeneralsBean("丁奉",  R.drawable.icon_test, 6, 68, 77))
        generals.add(GeneralsBean("朱然",  R.drawable.icon_test, 5, 70, 74))
        generals.add(GeneralsBean("韩当",  R.drawable.icon_test, 6, 65, 71))

        //7
        generals.add(GeneralsBean("吕布",  R.drawable.icon_test, 3, 100, 80))
        generals.add(GeneralsBean("董卓",  R.drawable.icon_test, 5, 71, 82))
        generals.add(GeneralsBean("孟获",  R.drawable.icon_test, 4, 81, 72))
        generals.add(GeneralsBean("袁绍",  R.drawable.icon_test, 5, 76, 72))
        generals.add(GeneralsBean("祝融",  R.drawable.icon_test, 4, 83, 78))
        generals.add(GeneralsBean("张角",  R.drawable.icon_test, 5, 79, 83))
        generals.add(GeneralsBean("貂蝉",  R.drawable.icon_test, 6, 67, 94))

    }

    private fun initEquipments() {
        equipments.add(EquipmentBean(EquipmentBean.TYPE.A))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.A))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.B))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.B))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.C))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.C))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.D))
        equipments.add(EquipmentBean(EquipmentBean.TYPE.D))
    }

    private fun initStocks() {
        stocks.add(StockBean("A"))
        stocks.add(StockBean("B"))
        stocks.add(StockBean("C"))
        stocks.add(StockBean("D"))
        stocks.add(StockBean("E"))
        stocks.add(StockBean("F"))
    }
}