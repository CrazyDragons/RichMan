package com.hzw.android.richman.bean;

import androidx.annotation.DrawableRes;

import com.alibaba.fastjson.JSONObject;
import com.hzw.android.richman.base.BaseMapBean;

/**
 * class CityBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
public class CityBean extends BaseMapBean {

    int buyPrice;
    int level = 0;
    int cover;
    GeneralBean general;
    Color color;

    public enum Color {
        /**
         * A
         */
        A,
        /**
         * B
         */
        B,
        /**
         * C
         */
        C,
        /**
         * D
         */
        D,
        /**
         * E
         */
        E,
    }

    public CityBean(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.buyPrice = jsonObject.getIntValue("buyPrice");
        this.type = MapType.valueOf(jsonObject.getString("type"));
        this.color = Color.valueOf(jsonObject.getString("color"));
        this.cover = jsonObject.getIntValue("cover");
    }

    public CityBean(String name, @DrawableRes int cover, int buyPrice, Color color) {
        this.name = name;
        this.cover = cover;
        this.buyPrice = buyPrice;
        this.type = MapType.CITY;
        this.color = color;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GeneralBean getGeneral() {
        return general;
    }

    public void setGeneral(GeneralBean general) {
        this.general = general;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}