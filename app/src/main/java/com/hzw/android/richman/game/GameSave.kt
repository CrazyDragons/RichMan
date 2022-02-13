package com.hzw.android.richman.game

import com.hzw.android.richman.base.BaseSave

/**
 * class GameSave
 * @author CrazyDragon
 * description 游戏存档
 * note
 * create date 2022/2/10
 */
object GameSave : BaseSave() {

    private const val MAP = "map"
    private const val PLAYER = "player"

    fun saveMap(json: String) {
        putStringData(MAP, json)
    }

    fun loadMap(): String {
        return getStringData(MAP)
    }

    fun savePlayer(json: String) {
        putStringData(PLAYER, json)
    }

    fun loadPlayer(): String {
        return getStringData(PLAYER)
    }

    fun clean() {
        saveMap("")
        savePlayer("")
    }
}
