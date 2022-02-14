package com.hzw.android.richman.game

import com.hzw.android.richman.adapter.LogAdapter
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.listener.OnAddLogListener

/**
 * class GameLog
 * @author CrazyDragon
 * description 游戏日志
 * note
 * create date 2022/2/12
 */
class GameLog {

    var logAdapter = LogAdapter()
    lateinit var onAddLogListener: OnAddLogListener

    companion object {
        val INSTANCE: GameLog by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GameLog()
        }
    }

    init {
        logAdapter.setNewInstance(GameData.INSTANCE.logData)
    }

    fun clear() {
        logAdapter.data.clear()
        GameData.INSTANCE.logData.clear()
    }

    fun addOptionLog() {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：正在操作中")
        onAddLogListener.onAddLog()
    }

    fun addWalkLog(walk: Int) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：投掷了" + walk + "点")
        onAddLogListener.onAddLog()
    }

    fun addSystemLog(msg: String) {
        logAdapter.addData("系统：$msg")
        onAddLogListener.onAddLog()
    }

    fun addBuyCityLog(baseCityBean: BaseCityBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：购买了" + baseCityBean.name)
        onAddLogListener.onAddLog()
    }

    fun addLevelCityLog(cityBean: CityBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：升级了" + cityBean.name)
        onAddLogListener.onAddLog()
    }

    fun addCostCityLog(cityBean: CityBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：交过路费 " + cityBean.name)
        onAddLogListener.onAddLog()
    }

}