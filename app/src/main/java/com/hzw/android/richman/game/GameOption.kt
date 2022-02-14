package com.hzw.android.richman.game

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.GeneralBean
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
                    buyBaseCity(cityBean)
                }
            }
        }
    }

    override fun buyBaseCity(baseCityBean: BaseCityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        when(baseCityBean) {
            is  CityBean -> {
                playerBean.money -= baseCityBean.buyPrice
                baseCityBean.level = 3
            }

            is AreaBean -> {
                playerBean.army -= baseCityBean.army
            }
        }
        baseCityBean.owner = playerBean
        playerBean.city.add(baseCityBean)
        GameLog.INSTANCE.addBuyCityLog(baseCityBean)
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

    override fun defense(baseCityBean: BaseCityBean, generalBean: GeneralBean?) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        if (baseCityBean.general != null) {
            baseCityBean.general?.city = null
            playerBean.generals.add(baseCityBean.general!!)
        }
        if (generalBean != null) {
            playerBean.generals.remove(generalBean)
        }
        baseCityBean.general = generalBean
        generalBean?.city = baseCityBean
        onOptionListener?.onOptionFinish()
    }

    override fun costBaseCity(baseCityBean: BaseCityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()

        when(baseCityBean) {
            is CityBean -> {
                playerBean.money -= baseCityBean.needCost()
                baseCityBean.owner!!.money += baseCityBean.needCost()
            }

            is AreaBean -> {
                playerBean.money -= baseCityBean.owner!!.allAreaCost()
                baseCityBean.owner!!.money += baseCityBean.owner!!.allAreaCost()
            }
        }


        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }


}