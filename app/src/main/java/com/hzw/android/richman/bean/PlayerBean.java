package com.hzw.android.richman.bean;

import java.util.ArrayList;

/**
 * class PlayerBean
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
public class PlayerBean {

    int id = 0;
    String name;
    int money = 10000;
    int army = 10000;
    ArrayList<GeneralBean> generals = new ArrayList<>();
    ArrayList<CityBean> citys = new ArrayList<>();
    ArrayList<EquipmentBean> equipments = new ArrayList<>();
    int walkIndex = 0;
    boolean isTurn = false;
    boolean isPlayer = true;

    public PlayerBean() {
    }

    public PlayerBean(String name) {
        this.name = name;
    }

    public PlayerBean(String name, boolean isPlayer) {
        this.name = name;
        this.isPlayer = isPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWalkIndex() {
        return walkIndex;
    }

    public void setWalkIndex(int walkIndex) {
        this.walkIndex = walkIndex;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getArmy() {
        return army;
    }

    public void setArmy(int army) {
        this.army = army;
    }

    public ArrayList<GeneralBean> getGenerals() {
        return generals;
    }

    public void setGenerals(ArrayList<GeneralBean> generals) {
        this.generals = generals;
    }

    public ArrayList<CityBean> getCitys() {
        return citys;
    }

    public void setCitys(ArrayList<CityBean> citys) {
        this.citys = citys;
    }

    public ArrayList<EquipmentBean> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<EquipmentBean> equipments) {
        this.equipments = equipments;
    }
}