package com.hzw.android.richman.game

import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.*
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.config.Value.DEFENSE_ARMY_COST
import com.hzw.android.richman.interfase.Option
import com.hzw.android.richman.listener.OnBaseCityOptionListener
import com.hzw.android.richman.listener.OnSpecialOptionListener

/**
 * class GameOption
 * @author CrazyDragon
 * description 游戏操作
 * note
 * create date 2022/2/13
 */
object GameOption : Option {

    var onBaseCityOptionListener: OnBaseCityOptionListener? = null
    var onSpecialOptionListener: OnSpecialOptionListener? = null

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
                if (baseCityBean.ownerID == 0) {
                    if (baseCityBean is CityBean) {
                        if ((playerBean.money - baseCityBean.buyPrice) >= Value.COMPUTER_MIN_MONEY) {
                            if (randomOption(Value.X_COMPUTER_BUY)) {
                                buyBaseCity(baseCityBean)
                            } else {
                                onBaseCityOptionListener?.onAllOptionFinish()
                            }
                        } else {
                            onBaseCityOptionListener?.onAllOptionFinish()
                        }
                    }
                    if (baseCityBean is AreaBean) {
                        if ((playerBean.army - Value.AREA_ARMY) >= Value.COMPUTER_MIN_ARMY) {
                            if (randomOption(Value.X_COMPUTER_BUY)) {
                                buyBaseCity(baseCityBean)
                            } else {
                                onBaseCityOptionListener?.onAllOptionFinish()
                            }
                        } else {
                            onBaseCityOptionListener?.onAllOptionFinish()
                        }
                    }
                } else {
                    if (baseCityBean.ownerID == GameData.INSTANCE.currentPlayer().id) {
                        val canLevel =
                            (GameData.INSTANCE.currentMap() is CityBean)
                                    && (GameData.INSTANCE.currentMap() as CityBean).level < 3
                                    && playerBean.status == PlayerBean.STATUS.OPTION_FALSE
                                    && (playerBean.money - (GameData.INSTANCE.currentMap() as CityBean).buyPrice * Value.X_LEVEL_CITY_COST) >= Value.COMPUTER_MIN_MONEY

                        val canSearch =
                            (GameData.INSTANCE.currentMap() is CityBean) && GameData.INSTANCE.currentPlayer().money >= Value.X_BUY_GENERALS && (GameData.INSTANCE.currentMap() as CityBean).search
                        val canRecover =
                            (GameData.INSTANCE.currentMap() is AreaBean) && GameData.INSTANCE.currentPlayer().money >= Value.X_BUY_GENERALS && (GameData.INSTANCE.currentMap() as AreaBean).recover
                        if (canSearch && randomOption(70)) {
                            search()
                        }

                        if (canRecover && randomOption(30)) {
                            recover()
                        }

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
                        onBaseCityOptionListener?.onAllOptionFinish()
                    } else {
                        if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_TRUE) {
                            onBaseCityOptionListener?.onAllOptionFinish()
                        } else {
                            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.ATTACK

                            var canAttack = true

                            if (baseCityBean is CityBean) {
                                canAttack =
                                    playerBean.army >= baseCityBean.needCostArmy() && playerBean.generals.size > 0
                            }

                            if (baseCityBean is AreaBean) {
                                canAttack =
                                    playerBean.army >= baseCityBean.owner()!!
                                        .allAreaCostArmy() && playerBean.generals.size > 0
                            }

                            val canPK: Boolean = playerBean.generals.size > 0

                            var x = (Math.random() * Value.X_COMPUTER_BASE + 1).toInt()
                            while (!randomAttack(x, canPK, canAttack)) {
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
                            onBaseCityOptionListener?.onAllOptionFinish()
                        }

                    }
                }
            }


            is SpecialBean -> {
                val specialBean = GameData.INSTANCE.currentMap() as SpecialBean
                when (specialBean.type) {
                    BaseMapBean.MapType.ARMY -> {
                        if (playerBean.army < 5000 && (5000 - playerBean.army) * Value.X_ARMY_MONEY < playerBean.money) {
                            buyArmy(5000 - playerBean.army)
                        } else {
                            onSpecialOptionListener?.onSure()
                        }
                    }
                    BaseMapBean.MapType.GENERALS -> {
                        if (playerBean.money >= Value.COMPUTER_MIN_MONEY) {
                            buyGenerals(randomCount())
                        } else {
                            onSpecialOptionListener?.onSure()
                        }
                    }
                    BaseMapBean.MapType.BIG_MONEY -> {
                        bigMoney(randomCount())
                    }
                    BaseMapBean.MapType.FREE_GENERALS -> {
                        freeGenerals(judgeFreeGenerals(randomCount()))
                    }
                    BaseMapBean.MapType.BANK -> {
                        bank(randomCount())
                    }
                    BaseMapBean.MapType.SHOP -> {
                        shop(GameData.INSTANCE.equipmentData[0])
                    }
                    BaseMapBean.MapType.CHANCE -> {
                        chance(randomCount())
                    }

                    BaseMapBean.MapType.PRISON -> {
                        prison(randomCount())
                    }

                    else -> {
                        onSpecialOptionListener?.onSure()
                    }
                }
            }
        }
    }

    private fun randomCount(): Int {
        return (Math.random() * Value.MAX_WALK + 1).toInt()
    }

    private fun randomAttack(
        x: Int,
        canPK: Boolean,
        canAttack: Boolean
    ): Boolean {
        when {
            x < Value.X_COMPUTER_COST -> {
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
        baseCityBean.ownerID = playerBean.id
        playerBean.city.add(baseCityBean)
        GameLog.INSTANCE.addBuyCityLog(baseCityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onBaseCityOptionListener?.onOnceOptionFinish(false)
    }

    override fun levelCity(cityBean: CityBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.money -= (cityBean.buyPrice * Value.X_LEVEL_CITY_COST).toInt()
        cityBean.level++
        GameLog.INSTANCE.addLevelCityLog(cityBean)
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onBaseCityOptionListener?.onOnceOptionFinish(needFinish)
    }

    override fun defense(
        baseCityBean: BaseCityBean,
        generalsBean: GeneralsBean?,
        needFinish: Boolean
    ) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        if (baseCityBean.generals != null) {
            baseCityBean.generals?.cityID = 0
            playerBean.generals.add(baseCityBean.generals!!)
        }
        if (generalsBean != null) {
            playerBean.generals.remove(generalsBean)
        }
        baseCityBean.generals = generalsBean
        generalsBean?.cityID = baseCityBean.id
        GameLog.INSTANCE.addDefenseCityLog(baseCityBean, generalsBean)
        onBaseCityOptionListener?.onOnceOptionFinish(needFinish)
    }

    override fun search() {
        val playerBean = GameData.INSTANCE.currentPlayer()
        if (randomOption(70)) {
            GameData.INSTANCE.giveGenerals(playerBean, 1)
        } else {
            GameLog.INSTANCE.addSystemLog("没有搜索到武将")
        }
        playerBean.money -= Value.X_BUY_GENERALS
        (GameData.INSTANCE.currentMap() as CityBean).search = false
        onBaseCityOptionListener?.onOnceOptionFinish(true)
    }

    override fun recover() {
        val playerBean = GameData.INSTANCE.currentPlayer()
        for (item in playerBean.generals) {
            if (item.action < item.life) {
                item.action++
            }
        }
        GameLog.INSTANCE.addSystemLog("回复了武将体力")
        (GameData.INSTANCE.currentMap() as AreaBean).recover = false
        onBaseCityOptionListener?.onOnceOptionFinish(true)
    }

    override fun costBaseCity(baseCityBean: BaseCityBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()

        when (baseCityBean) {
            is CityBean -> {
                playerBean.money -= baseCityBean.needCostMoney()
                baseCityBean.owner()!!.money += baseCityBean.needCostMoney()
            }

            is AreaBean -> {
                playerBean.money -= baseCityBean.owner()!!.allAreaCostMoney()
                baseCityBean.owner()!!.money += baseCityBean.owner()!!.allAreaCostMoney()
            }
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        GameLog.INSTANCE.addCostCityLog(baseCityBean)
        onBaseCityOptionListener?.onOnceOptionFinish(needFinish)
    }

    override fun pk(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, needFinish: Boolean) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        //如果城池有武将
        if (baseCityBean.generals != null) {
            when {
                //如果攻方获胜
                generalsBean.attack() > baseCityBean.generals!!.defense() -> {
                    GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, true)
                    generalsLife(generalsBean, false)
                }
                //如果攻方失败
                generalsBean.attack() < baseCityBean.generals!!.defense() -> {
                    GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, false)
                    costMoney(baseCityBean, generalsBean)
                    generalsLife(generalsBean, false)
                }
                //平局
                else -> {
                    //同归于尽
                    GameLog.INSTANCE.addSystemLog("单挑同归于尽")
                    baseCityBean.owner()!!.generals.remove(baseCityBean.generals)
                    generalsBean.owner()!!.generals.remove(generalsBean)
                    resetGenerals(baseCityBean.generals!!)
                    resetGenerals(generalsBean)
                }
            }
        }
        //没有武将
        else {
            //攻方攻击力小于随机防御力
            if (generalsBean.attack() < 50 + Math.random() * Value.X_COMPUTER_EMPTY_CITY) {
                GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, false)
                costMoney(baseCityBean, generalsBean)
            } else {
                GameLog.INSTANCE.addPkLog(baseCityBean, generalsBean, true)
            }
            generalsLife(generalsBean, false)
        }
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE
        onBaseCityOptionListener?.onOnceOptionFinish(needFinish)
    }

    override fun attack(
        baseCityBean: BaseCityBean,
        generalsBean: GeneralsBean
    ) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.status = PlayerBean.STATUS.OPTION_TRUE

        if (baseCityBean.owner()!!.army < DEFENSE_ARMY_COST) {
            if (baseCityBean.generals != null) {
                win(generalsBean, baseCityBean.generals!!)
            }
            baseCityBean.owner()!!.city.remove(baseCityBean)
            baseCityBean.ownerID = playerBean.id
            baseCityBean.generals = null
            playerBean.city.add(baseCityBean)
            GameLog.INSTANCE.addSystemLog("由于驻守兵力不足，攻方直接占有城池并俘虏武将")
            onBaseCityOptionListener?.onOnceOptionFinish(false)
        } else {
            costArmy(baseCityBean, generalsBean)
            //如果城池有武将
            if (baseCityBean.generals != null) {
                when {
                    //如果攻方获胜
                    generalsBean.attack() > if (baseCityBean is CityBean) baseCityBean.defense() else if (baseCityBean is AreaBean) baseCityBean.defense() else 0 -> {
                        GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, true)
                        baseCityBean.owner()!!.city.remove(baseCityBean)
                        baseCityBean.ownerID = playerBean.id
                        playerBean.city.add(baseCityBean)
                        if (generalsLife(generalsBean, true)) {
                            win(generalsBean, baseCityBean.generals!!)
                        }
                        baseCityBean.generals = null
                        onBaseCityOptionListener?.onOnceOptionFinish(false)
                    }
                    //如果攻方失败
                    generalsBean.attack() < if (baseCityBean is CityBean) baseCityBean.defense() else if (baseCityBean is AreaBean) baseCityBean.defense() else 0 -> {
                        GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, false)
                        if (generalsLife(generalsBean, true)) {

                            win(baseCityBean.generals!!, generalsBean)
                        }
                        onBaseCityOptionListener?.onOnceOptionFinish(true)
                    }
                    //平局
                    else -> {
                        //同归于尽
                        GameLog.INSTANCE.addSystemLog("同归于尽")
                        baseCityBean.owner()!!.generals.remove(baseCityBean.generals)
                        generalsBean.owner()!!.generals.remove(generalsBean)
                        resetGenerals(baseCityBean.generals!!)
                        resetGenerals(generalsBean)
                        onBaseCityOptionListener?.onOnceOptionFinish(true)
                    }
                }
            }
            //没有武将
            else {
                //攻方攻击力大于随机防御力
                if (generalsBean.attack() >= 50 + Math.random() * Value.X_COMPUTER_EMPTY_CITY) {
                    GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, true)
                    baseCityBean.owner()!!.city.remove(baseCityBean)
                    baseCityBean.ownerID = playerBean.id
                    playerBean.city.add(baseCityBean)
                    onBaseCityOptionListener?.onOnceOptionFinish(false)
                } else {
                    GameLog.INSTANCE.addAttackLog(baseCityBean, generalsBean, false)
                    onBaseCityOptionListener?.onOnceOptionFinish(true)
                }
                generalsLife(generalsBean, true)
            }
        }
    }

    override fun buyArmy(army: Int) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.army += army
        playerBean.money -= army * Value.X_ARMY_MONEY
        GameLog.INSTANCE.addArmyLog(army)
        onSpecialOptionListener?.onSure()
    }

    override fun buyGenerals(count: Int) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        val listA = mutableListOf<GeneralsBean>()
        val listS = mutableListOf<GeneralsBean>()
        for (item in GameData.INSTANCE.generalsData) {
            if (item.attack in 85..94 || item.defense in 85..94) {
                listA.add(item)
            }
            if (item.attack >= 95 || item.defense >= 95) {
                listS.add(item)
            }
        }
        if (count <= 9 && listA.isNotEmpty()) {
            val generalsBean = listA[randomSelect(listA.size)]
            GameData.INSTANCE.generalsData.remove(generalsBean)
            generalsBean.ownerID = playerBean.id
            playerBean.generals.add(generalsBean)
            playerBean.money -= Value.X_BUY_GENERALS
            GameLog.INSTANCE.addGeneralsLog(generalsBean)
        }
        if (count > 9 && listS.isNotEmpty()) {
            val generalsBean = listS[randomSelect(listS.size)]
            GameData.INSTANCE.generalsData.remove(generalsBean)
            generalsBean.ownerID = playerBean.id
            playerBean.generals.add(generalsBean)
            playerBean.money -= Value.X_BUY_GENERALS
            GameLog.INSTANCE.addGeneralsLog(generalsBean)
        }
        onSpecialOptionListener?.onSure()
    }

    override fun bigMoney(count: Int) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        playerBean.money += count * 1000
        GameLog.INSTANCE.addBigMoneyLog(count)
        onSpecialOptionListener?.onSure()
    }

    override fun freeGenerals(count: Int) {
        GameData.INSTANCE.giveGenerals(GameData.INSTANCE.currentPlayer(), count)
        onSpecialOptionListener?.onSure()
    }

    fun judgeFreeGenerals(count: Int): Int {
        when (count) {
            in 1..6 -> {
                return 1
            }
            in 7..9 -> {
                return 2
            }
            in 10..11 -> {
                return 3
            }
            12 -> {
                return 5
            }
            else -> {
                return 0
            }
        }
    }

    override fun bank(count: Int) {
        val playerBean = GameData.INSTANCE.currentPlayer()
        when (count) {
            in 1..3 -> {
                for (item in GameData.INSTANCE.playerData) {
                    giveMoney(playerBean, item, 1000)
                }
            }
            in 4..6 -> {
                giveMoney(playerBean, null, 1000)
            }
            in 7..9 -> {
                giveMoney(null, playerBean, 1000)
            }
            in 10..12 -> {
                for (item in GameData.INSTANCE.playerData) {
                    giveMoney(item, playerBean, 1000)
                }
            }
        }
        onSpecialOptionListener?.onSure()
    }

    override fun shop(equipmentBean: EquipmentBean) {
        GameData.INSTANCE.equipmentData.remove(equipmentBean)
        GameData.INSTANCE.currentPlayer().equipments.add(equipmentBean)
        GameData.INSTANCE.currentPlayer().money -= equipmentBean.price
        GameLog.INSTANCE.addEquipmentLog(equipmentBean)
        onSpecialOptionListener?.onSure()
    }

    override fun chance(count: Int) {
        onSpecialOptionListener?.onSure()
    }

    override fun prison(count: Int) {
        when (count) {
            in 1..2 -> {
                GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.PRISON
                GameData.INSTANCE.currentPlayer().money -= 5000
                GameLog.INSTANCE.addPrisonLog(true)
            }
            in 3..10 -> {
                GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.PRISON
                GameLog.INSTANCE.addPrisonLog(false)
            }
            else -> {

            }
        }
        onSpecialOptionListener?.onSure()
    }

    private fun giveMoney(give: PlayerBean?, get: PlayerBean?, money: Int) {
        if (give != null) {
            give.money -= money
        }
        if (get != null) {
            get.money += money
        }
        GameLog.INSTANCE.addBankLog(give, get, money)
    }

    private fun costMoney(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val x = if (baseCityBean.generals != null) Value.X_PK_LOSER else Value.X_PK_LOSER_EMPTY
        if (baseCityBean is CityBean) {
            generalsBean.owner()!!.money -= (baseCityBean.needCostMoney() * x).toInt()
            baseCityBean.owner()!!.money += (baseCityBean.needCostMoney() * x).toInt()
        }
        if (baseCityBean is AreaBean) {
            generalsBean.owner()!!.money -= (baseCityBean.owner()!!.allAreaCostMoney() * x).toInt()
            baseCityBean.owner()!!.money += (baseCityBean.owner()!!.allAreaCostMoney() * x).toInt()
        }
    }

    private fun costArmy(baseCityBean: BaseCityBean, generalsBean: GeneralsBean) {
        val x =
            if (baseCityBean.generals != null) Value.X_ATTACK_LOSER else Value.X_ATTACK_LOSER_EMPTY
        if (baseCityBean is CityBean) {
            generalsBean.owner()!!.army -= baseCityBean.needCostArmy() * x
            baseCityBean.owner()!!.army -= DEFENSE_ARMY_COST
        }
        if (baseCityBean is AreaBean) {
            generalsBean.owner()!!.army -= baseCityBean.owner()!!.allAreaCostArmy() * x
            baseCityBean.owner()!!.army -= DEFENSE_ARMY_COST
        }
    }

    private fun win(win: GeneralsBean, loser: GeneralsBean) {
        //回血
        loser.action = loser.life
        loser.cityID = 0
        //败方失去武将
        loser.owner()!!.generals.remove(loser)
        loser.ownerID = win.ownerID
        win.owner()!!.generals.add(loser)
    }

    private fun generalsLife(generalsBean: GeneralsBean, attack: Boolean): Boolean {
        //胜方武将体力减少
        generalsBean.action =
            generalsBean.action - if (attack) Value.ACTION_ATTACK else Value.ACTION_PK
        //如果体力为0
        if (generalsBean.action() <= 0) {
            //武将死亡
            generalsBean.owner()!!.generals.remove(generalsBean)
            //回武将池
            resetGenerals(generalsBean)

            return false
        }

        return true
    }

    private fun resetGenerals(generalsBean: GeneralsBean) {
        generalsBean.cityID = 0
        generalsBean.ownerID = 0
        generalsBean.action = generalsBean.life
        GameData.INSTANCE.generalsData.add(generalsBean)
    }


}