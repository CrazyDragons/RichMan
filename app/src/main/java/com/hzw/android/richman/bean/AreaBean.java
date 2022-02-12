package com.hzw.android.richman.bean;

import com.alibaba.fastjson.JSONObject;
import com.hzw.android.richman.base.BaseMapBean;

/**
 * class AreaBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class AreaBean extends BaseMapBean {

    int army = 2000;

    public AreaBean() {
    }

    public AreaBean(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.army = jsonObject.getIntValue("army");
        this.type = MapType.valueOf(jsonObject.getString("type"));
    }

    public AreaBean(String name) {
        this.name = name;
        this.type = MapType.AREA;
    }

    public int getArmy() {
        return army;
    }

    public void setArmy(int army) {
        this.army = army;
    }
}