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
    const val WALK_TURN: Long = 10

    //单次投掷时间
    const val WALK_TURN_TIME: Long = 20

    //单格行走时间
    const val WALK_TIME: Long = 500

    //X轴地图数
    const val COUNT_MAP_X = 27

    //Y轴地图数
    const val COUNT_MAP_Y = 5

    //战区兵力
    const val AREA_ARMY = 1000

    //升级城池系数
    const val X_LEVEL_CITY_COST = 0.5

    //守城损兵
    const val DEFENSE_ARMY_COST = 500

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
    const val X_CITY_ARMY_LEVEL_0 = 1

    ///小城驻兵系数
    const val X_CITY_ARMY_LEVEL_1 = 2

    //中城驻兵系数
    const val X_CITY_ARMY_LEVEL_2 = 4

    ///大城驻兵系数
    const val X_CITY_ARMY_LEVEL_3 = 8

    ///一区过路费系数
    const val X_AREA_MONEY_LEVEL_1 = 2

    //二区过路费系数
    const val X_AREA_MONEY_LEVEL_2 = 4

    //三区过路费系数
    const val X_AREA_MONEY_LEVEL_3 = 8

    ///四区过路费系数
    const val X_AREA_MONEY_LEVEL_4 = 15

    //五区过路费系数
    const val X_AREA_MONEY_LEVEL_5 = 30

    ///一区驻兵系数
    const val X_AREA_ARMY_LEVEL_1 = 1

    //二区驻兵系数
    const val X_AREA_ARMY_LEVEL_2 = 2

    //三区驻兵系数
    const val X_AREA_ARMY_LEVEL_3 = 5

    //四区驻兵系数
    const val X_AREA_ARMY_LEVEL_4 = 10

    //五区驻兵系数
    const val X_AREA_ARMY_LEVEL_5 = 20

    //默认初始金钱
    const val DEFAULT_MONEY = 20000

    //默认初始兵力
    const val DEFAULT_ARMY = 4000

    //默认初始武将
    const val DEFAULT_GENERALS = 8

    //默认初始道具
    const val DEFAULT_EQUIPMENTS = 0

    //电脑金钱高于此才会操作
    const val COMPUTER_MIN_MONEY = 0

    //电脑兵力高于此才会操作
    const val COMPUTER_MIN_ARMY = 5000

    //最大概率
    const val X_COMPUTER_BASE = 100

    //电脑购买城池概率
    const val X_COMPUTER_BUY = 100

    //电脑升级城池概率
    const val X_COMPUTER_LEVEL = 90

    //电脑驻守城池概率
    const val X_COMPUTER_DEFENSE = 80

    //电脑选择交费概率
    const val X_COMPUTER_COST = 40

    //电脑选择攻城概率
    const val X_COMPUTER_ATTACK = 70

    //电脑购买兵力的经济占比
    const val X_COMPUTER_ARMY = 0.2

    //经过起点增加金钱
    const val START_ADD_MONEY = 2000

    //有驻守武将PK失败损钱系数
    const val X_PK_LOSER = 1.5

    //无驻守武将PK失败损钱系数
    const val X_PK_LOSER_EMPTY = 2.0

    //有驻守武将攻城失败损兵系数
    const val X_ATTACK_LOSER = 1

    //无驻守武将攻城失败损兵系数
    const val X_ATTACK_LOSER_EMPTY = 2

    //守城增加防御
    const val ADD_DEFENSE = 5

    //同色增钱系数
    const val X_ALL_COLOR_MONEY = 2

    //同色增兵系数
    const val X_ALL_COLOR_ARMY = 1.5

    //同色增防
    const val ALL_COLOR_DEFENSE = 20

    //攻城消耗行动力
    const val ACTION_ATTACK = 2

    //单挑消耗行动力
    const val ACTION_PK = 1

    //购买兵力单位
    const val X_BUY_ARMY = 500

    //购买武将价格
    const val X_BUY_GENERALS = 2000

    //最大购买兵力
    const val MAX_BUY_ARMY = 10000

    //兵力价格
    const val X_ARMY_MONEY = 2
}