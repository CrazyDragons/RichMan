package com.hzw.android.richman.bean;

/**
 * class BaseMapBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class BaseMapBean {


    public enum MapType {
        /**
         * 起点
         */
        START,
        /**
         * 城池
         */
        CITY,
        /**
         * 古战场
         */
        AREA,
        /**
         * 征兵处
         */
        ARMY,
        /**
         * 职业介绍所
         */
        GENERALS,
        /**
         * 机会
         */
        CHANCE,
        /**
         * 商店
         */
        SHOP,
        /**
         * 钱
         */
        MONEY,
        /**
         * 金银岛
         */
        BIG_MONEY,
        /**
         * 茅庐
         */
        FREE_GENERALS,
        /**
         * 监狱
         */
        PRISON
    }

    public MapType type = MapType.START;

    public String name = "地图";


}