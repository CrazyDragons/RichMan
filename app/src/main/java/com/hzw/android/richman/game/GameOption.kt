package com.hzw.android.richman.game

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.GeneralsBean
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

    var onOptionListener: OnOptionListener? = null

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
        when (baseCityBean) {
            is CityBean -> {
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

    override fun defense(baseCityBean: BaseCityBean, generalsBean: GeneralsBean?) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        if (baseCityBean.generals != null) {
            baseCityBean.generals?.city = null
            playerBean.generals.add(baseCityBean.generals!!)
        }
        if (generalsBean != null) {
            playerBean.generals.remove(generalsBean)
        }
        baseCityBean.generals = generalsBean
        generalsBean?.city = baseCityBean
        onOptionListener?.onOptionFinish()
    }

    fun pk(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        //如果城池有武将
        if (baseCityBean.generals != null) {
            when {
                //如果攻方获胜
                generalsBean.attack > baseCityBean.generals!!.defense -> {
                    GameLog.INSTANCE.addSystemLog("攻方获胜")
                    generalsLife(generalsBean, false)
                }
                //如果攻方失败
                generalsBean.attack < baseCityBean.generals!!.defense -> {
                    GameLog.INSTANCE.addSystemLog("攻方失败")
                    costMoney(baseCityBean, generalsBean)
                    generalsLife(generalsBean, false)
                }
                //平局
                else -> {
                    //同归于尽
                    baseCityBean.owner!!.generals.remove(baseCityBean.generals)
                    baseCityBean.owner = null
                    generalsBean.owner!!.generals.remove(generalsBean)
                }
            }
        }
        //没有武将
        else {
            //攻方攻击力小于随机防御力
            if (generalsBean.attack < Math.random() * 100) {
                GameLog.INSTANCE.addSystemLog("攻方连空城都打不下来？")
                costMoney(baseCityBean, generalsBean)
                generalsLife(generalsBean, false)
            }
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }

    fun attack(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        costArmy(baseCityBean, generalsBean)
        //如果城池有武将
        if (baseCityBean.generals != null) {
            when {
                //如果攻方获胜
                generalsBean.attack > baseCityBean.generals!!.defense -> {
                    GameLog.INSTANCE.addSystemLog("攻方获胜")
                    win(generalsBean, baseCityBean.generals!!)
                    baseCityBean.owner!!.city.remove(baseCityBean)
                    baseCityBean.generals = null
                    baseCityBean.owner = playerBean
                    playerBean.city.add(baseCityBean)
                    generalsLife(generalsBean, true)
                }
                //如果攻方失败
                generalsBean.attack < baseCityBean.generals!!.defense -> {
                    GameLog.INSTANCE.addSystemLog("攻方失败")
                    win(baseCityBean.generals!!, generalsBean)
                    generalsLife(generalsBean, true)
                }
                //平局
                else -> {
                    //同归于尽
                    baseCityBean.owner!!.generals.remove(baseCityBean.generals)
                    baseCityBean.owner = null
                    generalsBean.owner!!.generals.remove(generalsBean)
                }
            }
        }
        //没有武将
        else {
            //攻方攻击力小于随机防御力
            if (generalsBean.attack < Math.random() * 100) {
                GameLog.INSTANCE.addSystemLog("攻方连空城都pk不过？")
                baseCityBean.owner = GameData.INSTANCE.currentPlayer()
                baseCityBean.generals = null
                generalsLife(generalsBean, true)
            }
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }

    private fun costMoney(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val x = if (baseCityBean.generals != null) 1.5 else 0.5
        if (baseCityBean is CityBean) {
            generalsBean.owner!!.money -= (baseCityBean.needCostMoney() * x).toInt()
            baseCityBean.owner!!.money += (baseCityBean.needCostMoney() * x).toInt()
        }
        if (baseCityBean is AreaBean) {
            generalsBean.owner!!.money -= (baseCityBean.owner!!.allAreaCostMoney() * x).toInt()
            baseCityBean.owner!!.money += (baseCityBean.owner!!.allAreaCostMoney() * x).toInt()
        }
    }

    private fun costArmy(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        if (baseCityBean is CityBean) {
            generalsBean.owner!!.army -= baseCityBean.needCostArmy() / if (baseCityBean.generals != null) 1 else 2
            baseCityBean.owner!!.army -= (baseCityBean.buyPrice * Value.DEFENSE_ARMY_COST_X).toInt()
        }
        if (baseCityBean is AreaBean) {
            generalsBean.owner!!.money -= baseCityBean.owner!!.allAreaCostArmy() / if (baseCityBean.generals != null) 1 else 2
            baseCityBean.owner!!.army -= (baseCityBean.army * Value.DEFENSE_ARMY_COST_X).toInt()
        }
    }

    private fun win(win: GeneralsBean, loser: GeneralsBean) {
        //胜方俘虏败方武将
        win.owner!!.generals.add(loser)
        //败方失去武将
        loser.city = null
        loser.owner!!.generals.remove(loser)
    }

    private fun generalsLife(generalsBean: GeneralsBean, attack: Boolean) {
        //胜方武将体力减少
        generalsBean.action = generalsBean.action - if (attack) 2 else 1
        //如果体力为0
        if (generalsBean.action == 0) {
            //武将死亡
            generalsBean.owner!!.generals.remove(generalsBean)
            //回武将池
            generalsBean.action = generalsBean.life
            GameData.INSTANCE.generalsData.add(generalsBean)
        }
    }


    override fun costBaseCity(baseCityBean: BaseCityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()

        when (baseCityBean) {
            is CityBean -> {
                playerBean.money -= baseCityBean.needCostMoney()
                baseCityBean.owner!!.money += baseCityBean.needCostMoney()
            }

            is AreaBean -> {
                playerBean.money -= baseCityBean.owner!!.allAreaCostMoney()
                baseCityBean.owner!!.money += baseCityBean.owner!!.allAreaCostMoney()
            }
        }


        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOptionFinish()
    }


}