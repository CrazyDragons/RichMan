package com.hzw.android.richman.game

import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.interfase.Option

/**
 * class GameOption
 * @author CrazyDragon
 * description 游戏操作
 * note
 * create date 2022/2/13
 */
object GameOption : Option {

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
    }


}