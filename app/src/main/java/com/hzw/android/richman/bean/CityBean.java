package com.hzw.android.richman.bean;

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
    int salePrice;
    int star = 0;
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

    public CityBean(String name, int buyPrice, Color color) {
        this.name = name;
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

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}