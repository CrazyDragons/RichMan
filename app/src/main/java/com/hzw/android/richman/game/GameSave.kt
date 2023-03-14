package com.hzw.android.richman.game

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.hzw.android.richman.base.BaseSave
import com.orhanobut.logger.Logger

/**
 * class GameSave
 * @author CrazyDragon
 * description 游戏存档
 * note
 * create date 2022/2/10
 */
object GameSave : BaseSave() {

    private const val NEW_GAME = "new_game"
    private const val DATA = "data"
    private const val PLAYER = "player"

    fun newGame(newGame:Boolean) {
        putBooleanData(NEW_GAME, newGame)
    }

    fun newGame():Boolean {
        return getBooleanData(NEW_GAME)
    }

    fun save() {
        GameLog.INSTANCE.addSystemLog("自动存档成功")
        val json = JSON.toJSONString(GameData.INSTANCE, SerializerFeature.DisableCircularReferenceDetect)
        Logger.d(json)
        saveData(json)
    }

    private fun saveData(json: String) {
        putStringData(DATA, json)
    }

    fun loadData():String {
        return getStringData(DATA)
    }

    fun savePlayer(json: String) {
        putStringData(PLAYER, json)
    }

    fun loadPlayer(): String {
        return getStringData(PLAYER)
    }
}
