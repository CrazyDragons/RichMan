package com.hzw.android.richman.bean;

import androidx.annotation.ColorInt;

import com.hzw.android.richman.R;

/**
 * class MapBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class SpecialBean extends BaseMapBean {

    MapType mapType;
    @ColorInt
    int color;

    public SpecialBean(MapType mapType) {
        this.mapType = mapType;
        if (mapType == MapType.START) {
            name =  "起点";
        }
        if (mapType == MapType.ARMY) {
            name =  "征兵处";
        }
        if (mapType == MapType.GENERALS) {
            name =  "职业介绍所";
        }
        if (mapType == MapType.CHANCE) {
            name =  "机会";
        }
        if (mapType == MapType.SHOP) {
            name =  "商店";
        }
        if (mapType == MapType.MONEY) {
            name =  "银行";
        }
        if (mapType == MapType.BIG_MONEY) {
            name =  "金银岛";
        }
        if (mapType == MapType.FREE_GENERALS) {
            name =  "茅庐";
        }
        if (mapType == MapType.PRISON) {
            name =  "监狱";
        }
    }

    public int getColor() {

        if (mapType == MapType.START) {
            return R.color.start;
        }
        if (mapType == MapType.ARMY) {
            return R.color.army;
        }
        if (mapType == MapType.GENERALS) {
            return R.color.generals;
        }
        if (mapType == MapType.CHANCE) {
            return R.color.change;
        }
        if (mapType == MapType.SHOP) {
            return R.color.shop;
        }
        if (mapType == MapType.MONEY) {
            return R.color.money;
        }
        if (mapType == MapType.BIG_MONEY) {
            return R.color.big_money;
        }
        if (mapType == MapType.FREE_GENERALS) {
            return R.color.free_generals;
        }
        if (mapType == MapType.PRISON) {
            return R.color.prison;
        }
        return 0;
    }

    public void setColor(int color) {
        this.color = color;
    }

}