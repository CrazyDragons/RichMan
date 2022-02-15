package com.hzw.android.richman.game

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.*
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.interfase.Option
import com.hzw.android.richman.listener.OnOptionListener
import com.hzw.android.richman.utils.MapUtil

/**
 * class GameOption
 * @author CrazyDragon
 * description 游戏操作
 * note
 * create date 2022/2/13
 */
object GameOption : Option {

    var onOptionListener: OnOptionListener? = null

    private fun randomOption(success: Int): Boolean {
        val x = Math.random() * Value.X_COMPUTER_BASE + 1
        return success >= x
    }

    private fun randomSelect(size: Int): Int {
        return (Math.random() * size).toInt()
    }

    fun autoOption() {
        val playerBean = GameData.INSTANCE.currentPlayer()
        when (GameData.INSTANCE.currentMap()) {
            is BaseCityBean -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                if (baseCityBean.owner == null) {
                    var canBuy = true
                    if (baseCityBean is CityBean && (playerBean.money - baseCityBean.buyPrice) < Value.COMPUTER_MIN_MONEY) {
                        canBuy = false
                    }
                    if (baseCityBean is AreaBean && (playerBean.army - Value.AREA_ARMY) < Value.COMPUTER_MIN_ARMY) {
                        canBuy = false
                    }
                    if (canBuy) {
                        if (randomOption(Value.X_COMPUTER_BUY)) {
                            buyBaseCity(baseCityBean)
                        } else {
                            onOptionListener?.onAllOptionFinish()
                        }
                    }
                } else {
                    if (baseCityBean.owner?.id == GameData.INSTANCE.currentPlayer().id) {
                        val canLevel =
                            (GameData.INSTANCE.currentMap() is CityBean)
                                    && (GameData.INSTANCE.currentMap() as CityBean).level < 3
                                    && playerBean.status == PlayerBean.STATUS.OPTION_FALSE
                                    && (playerBean.money - (GameData.INSTANCE.currentMap() as CityBean).buyPrice * Value.X_LEVEL_CITY_COST) >= Value.COMPUTER_MIN_MONEY

                        if (canLevel) {
                            if (randomOption(Value.X_COMPUTER_LEVEL)) {
                                levelCity(GameData.INSTANCE.currentMap() as CityBean, true)
                            }
                        } else {
                            if (randomOption(Value.X_COMPUTER_DEFENSE)) {
                                defense(
                                    baseCityBean,
                                    if (playerBean.generals.size == 0) null
                                    else playerBean.generals[randomSelect(playerBean.generals.size)],
                                    true
                                )
                            }
                        }
                        onOptionListener?.onAllOptionFinish()
                    } else {
                        if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_TRUE) {
                            onOptionListener?.onAllOptionFinish()
                        } else {
                            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.ATTACK

                            var canCost = true
                            var canAttack = true

                            if (baseCityBean is CityBean) {
                                canCost = playerBean.money >= baseCityBean.needCostMoney()
                                canAttack = playerBean.army >= baseCityBean.needCostArmy()
                            }

                            if (baseCityBean is AreaBean) {
                                canCost =
                                    playerBean.money >= baseCityBean.owner!!.allAreaCostMoney()
                                canAttack =
                                    playerBean.army >= baseCityBean.owner!!.allAreaCostArmy() && playerBean.generals.size > 0
                            }

                            val canPK: Boolean = playerBean.generals.size > 0

                            if (!canCost && !canPK && !canAttack) {
                                GameLog.INSTANCE.addSystemLog("进入拍卖")
                                return
                            }

                            var x = (Math.random() * Value.X_COMPUTER_BASE + 1).toInt()
                            while (!randomAttack(x, canCost, canPK, canAttack)) {
                                x = (Math.random() * Value.X_COMPUTER_BASE + 1).toInt()
                            }

                            when {
                                x <= Value.X_COMPUTER_COST -> {
                                    costBaseCity(baseCityBean, true)
                                }
                                x >= Value.X_COMPUTER_ATTACK -> {
                                    attack(
                                        baseCityBean,
                                        playerBean.generals[randomSelect(playerBean.generals.size)],
                                        true
                                    )
                                }
                                else -> {
                                    pk(
                                        baseCityBean,
                                        playerBean.generals[randomSelect(playerBean.generals.size)],
                                        true
                                    )
                                }
                            }
                            onOptionListener?.onAllOptionFinish()
                        }

                    }
                }
            }


            is SpecialBean -> {
                val specialBean = GameData.INSTANCE.currentMap() as SpecialBean
                onOptionListener?.onAllOptionFinish()
            }
        }
    }

    private fun randomAttack(
        x: Int,
        canCost: Boolean,
        canPK: Boolean,
        canAttack: Boolean
    ): Boolean {
        when {
            x < Value.X_COMPUTER_COST && canCost -> {
                return true
            }
            x > Value.X_COMPUTER_ATTACK && canAttack -> {
                return true
            }
            x in Value.X_COMPUTER_COST..Value.X_COMPUTER_ATTACK && canPK -> {
                return true
            }
        }
        return false
    }

    override fun buyBaseCity(baseCityBean: BaseCityBean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        when (baseCityBean) {
            is CityBean -> {
                playerBean.money -= baseCityBean.buyPrice
            }

            is AreaBean -> {
                playerBean.army -= baseCityBean.army
            }
        }
        baseCityBean.owner = playerBean
        playerBean.city.add(baseCityBean)
        GameLog.INSTANCE.addBuyCityLog(baseCityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOnceOptionFinish(false)
    }

    override fun levelCity(cityBean: CityBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.money -= (cityBean.buyPrice * Value.X_LEVEL_CITY_COST).toInt()
        cityBean.level = cityBean.level + 1
        GameLog.INSTANCE.addLevelCityLog(cityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOnceOptionFinish(needFinish)
    }

    override fun defense(
        baseCityBean: BaseCityBean,
        generalsBean: GeneralsBean?,
        needFinish: Boolean
    ) {
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
        GameLog.INSTANCE.addDefenseCityLog(baseCityBean, generalsBean)
        onOptionListener?.onOnceOptionFinish(needFinish)
    }

    fun pk(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        //如果城池有武将
        if (baseCityBean.generals != null) {
            when {
                //如果攻方获胜
                generalsBean.attack > baseCityBean.generals!!.defense -> {
                    GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, true)
                    generalsLife(generalsBean, false)
                }
                //如果攻方失败
                generalsBean.attack < baseCityBean.generals!!.defense -> {
                    GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, false)
                    costMoney(baseCityBean, generalsBean)
                    generalsLife(generalsBean, false)
                }
                //平局
                else -> {
                    //同归于尽
                    GameLog.INSTANCE.addSystemLog("单挑同归于尽")
                    resetGenerals(baseCityBean.generals!!)
                    resetGenerals(generalsBean)
                    baseCityBean.owner!!.generals.remove(baseCityBean.generals)
                    generalsBean.owner!!.generals.remove(generalsBean)
                }
            }
        }
        //没有武将
        else {
            //攻方攻击力小于随机防御力
            if (generalsBean.attack < Math.random() * 100) {
                GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, false)
                costMoney(baseCityBean, generalsBean)
            } else {
                GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, true)
            }
            generalsLife(generalsBean, false)
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOnceOptionFinish(needFinish)
    }

    fun attack(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        costArmy(baseCityBean, generalsBean)
        //如果城池有武将
        val addDefense =
            if (baseCityBean is CityBean) baseCityBean.level * Value.ADD_DEFENSE + if (MapUtil.judgeAllColor(baseCityBean)) Value.ALL_COLOR_DEFENSE else 0
            else baseCityBean.owner!!.allArea() * Value.ADD_DEFENSE + if (MapUtil.judgeAllColor(baseCityBean)) Value.ALL_COLOR_DEFENSE else 0
        if (baseCityBean.generals != null) {
            when {
                //如果攻方获胜
                generalsBean.attack > baseCityBean.generals!!.defense + addDefense -> {
                    GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, true)
                    baseCityBean.owner!!.city.remove(baseCityBean)
                    baseCityBean.owner = playerBean
                    playerBean.city.add(baseCityBean)
                    if (generalsLife(generalsBean, true)) {
                        win(generalsBean, baseCityBean.generals!!)
                    }
                }
                //如果攻方失败
                generalsBean.attack < baseCityBean.generals!!.defense + addDefense -> {
                    GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, false)
                    if (generalsLife(generalsBean, true)) {

                        win(baseCityBean.generals!!, generalsBean)
                    }
                }
                //平局
                else -> {
                    //同归于尽
                    GameLog.INSTANCE.addSystemLog("同归于尽")
                    resetGenerals(baseCityBean.generals!!)
                    resetGenerals(generalsBean)
                    baseCityBean.owner!!.generals.remove(baseCityBean.generals)
                    generalsBean.owner!!.generals.remove(generalsBean)
                }
            }
        }
        //没有武将
        else {
            //攻方攻击力大于随机防御力
            if (generalsBean.attack >= Math.random() * Value.X_COMPUTER_BASE + addDefense) {
                GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, true)
                baseCityBean.owner!!.city.remove(baseCityBean)
                baseCityBean.owner = playerBean
                playerBean.city.add(baseCityBean)
            } else {
                GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, false)
            }
            generalsLife(generalsBean, true)
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onOptionListener?.onOnceOptionFinish(needFinish)
    }

    private fun costMoney(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val x = if (baseCityBean.generals != null) Value.X_PK_LOSER else Value.X_PK_LOSER_EMPTY
        if (baseCityBean is CityBean) {
            generalsBean.owner!!.money -= (baseCityBean.needCostMoney() * x * if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1).toInt()
            baseCityBean.owner!!.money += (baseCityBean.needCostMoney() * x * if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1).toInt()
        }
        if (baseCityBean is AreaBean) {
            generalsBean.owner!!.money -= (baseCityBean.owner!!.allAreaCostMoney() * x + if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1).toInt()
            baseCityBean.owner!!.money += (baseCityBean.owner!!.allAreaCostMoney() * x + if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1).toInt()
        }
    }

    private fun costArmy(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val x =
            if (baseCityBean.generals != null) Value.X_ATTACK_LOSER else Value.X_ATTACK_LOSER_EMPTY
        if (baseCityBean is CityBean) {
            generalsBean.owner!!.army -= baseCityBean.needCostArmy() * x
            baseCityBean.owner!!.army -= (baseCityBean.buyPrice * Value.X_DEFENSE_ARMY_COST).toInt()
        }
        if (baseCityBean is AreaBean) {
            generalsBean.owner!!.army -= baseCityBean.owner!!.allAreaCostArmy() * x
            baseCityBean.owner!!.army -= (baseCityBean.army * Value.X_DEFENSE_ARMY_COST).toInt()
        }
    }

    private fun win(win: GeneralsBean, loser: GeneralsBean) {
        //回血
        loser.action = loser.life
        loser.owner = win.owner
        loser.city = null
        //败方失去武将
        loser.owner!!.generals.remove(loser)
        win.owner!!.generals.add(loser)
    }

    private fun generalsLife(generalsBean: GeneralsBean, attack: Boolean): Boolean {
        //胜方武将体力减少
        generalsBean.action = generalsBean.action - if (attack) Value.ACTION_ATTACK else Value.ACTION_PK
        //如果体力为0
        if (generalsBean.action == 0) {
            //武将死亡
            generalsBean.owner!!.generals.remove(generalsBean)
            //回武将池
            resetGenerals(generalsBean)

            return false
        }

        return true
    }

    private fun resetGenerals(generalsBean: GeneralsBean) {
        generalsBean.city = null
        generalsBean.owner = null
        generalsBean.action = generalsBean.life
        GameData.INSTANCE.generalsData.add(generalsBean)
    }


    override fun costBaseCity(baseCityBean: BaseCityBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()

        when (baseCityBean) {
            is CityBean -> {
                playerBean.money -= baseCityBean.needCostMoney() * if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1
                baseCityBean.owner!!.money += baseCityBean.needCostMoney() * if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1
            }

            is AreaBean -> {
                playerBean.money -= baseCityBean.owner!!.allAreaCostMoney() * if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1
                baseCityBean.owner!!.money += baseCityBean.owner!!.allAreaCostMoney() * if (MapUtil.judgeAllColor(baseCityBean)) Value.X_ALL_COLOR_MONEY else 1
            }
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        GameLog.INSTANCE.addCostCityLog(baseCityBean)
        onOptionListener?.onOnceOptionFinish(needFinish)
    }


}