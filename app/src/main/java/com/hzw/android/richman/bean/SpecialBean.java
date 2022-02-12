package com.hzw.android.richman.bean;

import androidx.annotation.DrawableRes;

import com.alibaba.fastjson.JSONObject;
import com.hzw.android.richman.R;
import com.hzw.android.richman.base.BaseMapBean;

/**
 * class MapBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class SpecialBean extends BaseMapBean {

    @DrawableRes
    int bg;

    public SpecialBean() {
    }

    public SpecialBean(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.type = MapType.valueOf(jsonObject.getString("type"));
        this.bg = jsonObject.getIntValue("bg");
    }

    public SpecialBean(MapType mapType) {
        this.type = mapType;
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

    public int getBg() {

        if (type == MapType.START) {
            return R.drawable.bg_start;
        }
        if (type == MapType.ARMY) {
            return R.drawable.bg_army;
        }
        if (type == MapType.GENERALS) {
            return R.drawable.bg_generals;
        }
        if (type == MapType.CHANCE) {
            return R.drawable.bg_chance;
        }
        if (type == MapType.SHOP) {
            return R.drawable.bg_shop;
        }
        if (type == MapType.MONEY) {
            return R.drawable.bg_money;
        }
        if (type == MapType.BIG_MONEY) {
            return R.drawable.bg_big_money;
        }
        if (type == MapType.FREE_GENERALS) {
            return R.drawable.bg_free_generals;
        }
        if (type == MapType.PRISON) {
            return R.drawable.bg_prison;
        }
        return 0;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

}