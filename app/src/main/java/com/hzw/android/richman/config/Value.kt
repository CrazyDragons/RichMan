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

    const val TEST = 1

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

    //单格行走时间
    const val WALK_TIME: Long = 500

    //X轴地图数
    const val COUNT_MAP_X = 27

    //Y轴地图数
    const val COUNT_MAP_Y = 5

    //战区兵力
    const val AREA_ARMY = 2000

    //升级城池系数
    const val X_LEVEL_CITY_COST = 0.5

    //守城损兵系数
    const val X_DEFENSE_ARMY_COST = 0.5

    //抵押系数
    const val X_SALE = 0.5

    //空城过路费系数
    const val X_CITY_MONEY_LEVEL_0 = 0.5

    ///小城过路费系数
    const val X_CITY_MONEY_LEVEL_1 = 1

    //中城过路费系数
    const val X_CITY_MONEY_LEVEL_2 = 2

    //大城过路费系数
    const val X_CITY_MONEY_LEVEL_3 = 4

    //空城驻兵系数
    const val X_CITY_ARMY_LEVEL_0 = 0.5

    ///小城驻兵系数
    const val X_CITY_ARMY_LEVEL_1 = 1

    //中城驻兵系数
    const val X_CITY_ARMY_LEVEL_2 = 2

    ///大城驻兵系数
    const val X_CITY_ARMY_LEVEL_3 = 4

    ///一区过路费系数
    const val X_AREA_MONEY_LEVEL_1 = 0.5

    //二区过路费系数
    const val X_AREA_MONEY_LEVEL_2 = 1

    //三区过路费系数
    const val X_AREA_MONEY_LEVEL_3 = 2

    ///四区过路费系数
    const val X_AREA_MONEY_LEVEL_4 = 4

    //五区过路费系数
    const val X_AREA_MONEY_LEVEL_5 = 8

    ///一区驻兵系数
    const val X_AREA_ARMY_LEVEL_1 = 1

    //二区驻兵系数
    const val X_AREA_ARMY_LEVEL_2 = 2

    //三区驻兵系数
    const val X_AREA_ARMY_LEVEL_3 = 3

    //四区驻兵系数
    const val X_AREA_ARMY_LEVEL_4 = 5

    //五区驻兵系数
    const val X_AREA_ARMY_LEVEL_5 = 10

    //默认初始金钱
    const val DEFAULT_MONEY = 10000

    //默认初始兵力
    const val DEFAULT_ARMY = 10000

    //默认初始武将
    const val DEFAULT_GENERALS = 5

    //默认初始道具
    const val DEFAULT_EQUIPMENTS = 1

    //电脑金钱高于此才会操作
    const val COMPUTER_MIN_MONEY = 5000

    //电脑兵力高于此才会操作
    const val COMPUTER_MIN_ARMY = 5000

    //最大概率
    const val X_COMPUTER_BASE = 100

    //电脑购买城池概率
    const val X_COMPUTER_BUY = 60

    //电脑升级城池概率
    const val X_COMPUTER_LEVEL = 80

    //电脑助手城池概率
    const val X_COMPUTER_DEFENSE = 90

    //电脑选择交费概率
    const val X_COMPUTER_COST = 60

    //电脑选择攻城概率
    const val X_COMPUTER_ATTACK = 90
}