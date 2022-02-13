package com.hzw.android.richman.config

/**
 * class MapsValue
 *
 * @author CrazyDragon
 * description 游戏参数
 * note
 * create date 2022/2/9
 */
object Value {

    //玩家X轴偏移量
    const val PLAYER_OFFSET_X = 20

    //玩家Y轴偏移量
    const val PLAYER_OFFSET_Y = 30

    //相机X轴系数
    const val CAMERA_OFFSET_X = 0.375f

    ///最大投掷数
    const val MAX_WALK = 12

    //投掷随机次数
    const val WALK_TURN: Long = 20

    //单次投掷时间
    const val WALK_TURN_TIME: Long = 20

    //X轴地图数
    const val X_COUNT = 27

    //Y轴地图数
    const val Y_COUNT = 5

    //战区兵力
    const val AREA_ARMY = 2000

    //升级城池系数
    const val LEVEL_CITY_COST_X = 0.5

    //守城损兵系数
    const val DEFENSE_ARMY_COST_X = 0.5

    //抵押系数
    const val SALE_X = 0.5

    //空城过路费系数
    const val LEVEL_CITY_MONEY_0 = 0.5

    ///小城过路费系数
    const val LEVEL_CITY_MONEY_1 = 1

    //中城过路费系数
    const val LEVEL_CITY_MONEY_2 = 2

    //大城过路费系数
    const val LEVEL_CITY_MONEY_3 = 4

    //空城驻兵系数
    const val LEVEL_CITY_ARMY_0 = 0.5

    ///小城驻兵系数
    const val LEVEL_CITY_ARMY_1 = 1

    //中城驻兵系数
    const val LEVEL_CITY_ARMY_2 = 2

    ///大城驻兵系数
    const val LEVEL_CITY_ARMY_3 = 4

    ///一区过路费系数
    const val LEVEL_AREA_MONEY_1 = 0.5

    //二区过路费系数
    const val LEVEL_AREA_MONEY_2 = 2

    //三区过路费系数
    const val LEVEL_AREA_MONEY_3 = 4

    ///四区过路费系数
    const val LEVEL_AREA_MONEY_4 = 8

    //五区过路费系数
    const val LEVEL_AREA_MONEY_5 = 16

    ///一区驻兵系数
    const val LEVEL_AREA_ARMY_1 = 1

    //二区驻兵系数
    const val LEVEL_AREA_ARMY_2 = 2

    //三区驻兵系数
    const val LEVEL_AREA_ARMY_3 = 4

    //四区驻兵系数
    const val LEVEL_AREA_ARMY_4 = 8

    //五区驻兵系数
    const val LEVEL_AREA_ARMY_5 = 16

    //默认初始金钱
    const val DEFAULT_MONEY = 10000

    //默认初始兵力
    const val DEFAULT_ARMY = 10000

    //默认初始武将
    const val DEFAULT_GENERALS = 5
}