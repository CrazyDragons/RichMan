package com.hzw.android.richman.bean;

/**
 * class AreaBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class AreaBean extends BaseMapBean{
    int buyPrice = 1000;
    int salePrice;

    public AreaBean(String name) {
        this.name = name;
        this.type = MapType.AREA;
    }
} 