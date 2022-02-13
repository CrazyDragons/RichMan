package com.hzw.android.richman.game

import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.interfase.Option
import com.hzw.android.richman.listener.OnOptionListener

/**
 * class GameOption
 * @author CrazyDragon
 * description 游戏操作
 * note
 * create date 2022/2/13
 */
object GameOption : Option {

    var onOptionListener:OnOptionListener? = null

    fun option() {

        when (GameData.INSTANCE.currentMap()) {
            is CityBean -> {
                val cityBean = GameData.INSTANCE.currentMap() as CityBean
                if (cityBean.owner == null) {
                    buyCity(cityBean)
                }
            }
        }
    }

    override fun buyCity(cityBean: CityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.money -= cityBean.buyPrice
        cityBean.owner = playerBean
        playerBean.city.add(cityBean)
        GameLog.INSTANCE.addBuyCityLog(cityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }

    override fun levelCity(cityBean: CityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.money -= (cityBean.buyPrice * Value.LEVEL_CITY_COST_X).toInt()
        cityBean.level = cityBean.level + 1
        GameLog.INSTANCE.addLevelCityLog(cityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }

    override fun costCity(cityBean: CityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.money -= cityBean.getCostMoney()
        cityBean.owner!!.money += cityBean.getCostMoney()
        GameLog.INSTANCE.addCostCityLog(cityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }


}