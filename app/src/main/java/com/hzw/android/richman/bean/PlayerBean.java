package com.hzw.android.richman.bean;

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
}