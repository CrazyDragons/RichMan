package com.hzw.android.richman.game

import com.hzw.android.richman.adapter.LogAdapter
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.EquipmentBean
import com.hzw.android.richman.bean.GeneralsBean
import com.hzw.android.richman.bean.PlayerBean
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
    var onAddLogListener:OnAddLogListener? = null

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

    fun addWalkLog(walk: Int) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：投掷了 " + walk + " 点")
        onAddLogListener?.onAddLog()
    }

    fun addSystemLog(msg: String) {
        logAdapter.addData("系统：$msg")
        onAddLogListener?.onAddLog()
    }

    fun addBuyCityLog(baseCityBean: BaseCityBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：购买了 " + baseCityBean.name)
        onAddLogListener?.onAddLog()
    }

    fun addLevelCityLog(cityBean: CityBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：升级了 " + cityBean.name)
        onAddLogListener?.onAddLog()
    }

    fun addDefenseCityLog(baseCityBean: BaseCityBean, generalsBean: GeneralsBean?) {
        if (generalsBean == null) {
            logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：设置 " + baseCityBean.name + " 为空城")
        } else {
            logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：派 " + generalsBean.name + " 驻守了 " + baseCityBean.name)
        }
        onAddLogListener?.onAddLog()
    }

    fun addCostCityLog(baseCityBean: BaseCityBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：交了 " + baseCityBean.name + " 过路费")
        onAddLogListener?.onAddLog()
    }

    fun addPkLog(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, win: Boolean) {
        if (win) {
            if (baseCityBean.generals != null) {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "： " + generalsBean.name + " 在 " + baseCityBean.name + " 单挑 " + baseCityBean.generals!!.name + " 获胜，免过路费")
            } else {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "： " + generalsBean.name + " 在 " + baseCityBean.name + " 单挑小兵获胜，免过路费")
            }
        } else {
            if (baseCityBean.generals != null) {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：" + generalsBean.name + " 在 " + baseCityBean.name + " 单挑失败，多交过路费")
            } else {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：" + generalsBean.name + " 在 " + baseCityBean.name + " 单挑小兵失败， 多交过路费")
            }
        }
        onAddLogListener?.onAddLog()
    }

    fun addAttackLog(baseCityBean: BaseCityBean, generalsBean: GeneralsBean, win: Boolean) {
        if (win) {
            if (baseCityBean.generals != null) {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "： " + generalsBean.name + " 在 " + baseCityBean.name + " 攻城获胜，并俘虏 " + baseCityBean.generals!!.name)
            } else {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "： " + generalsBean.name + " 在 " + baseCityBean.name + " 攻空城获胜")
            }
        } else {
            if (baseCityBean.generals != null) {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：" + generalsBean.name + " 在 " + baseCityBean.name + " 攻城失败，被 " + baseCityBean.generals!!.name + " 俘虏")
            } else {
                logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：" + generalsBean.name + " 在 " + baseCityBean.name + " 攻空城失败")
            }
        }
        onAddLogListener?.onAddLog()
    }

    fun addArmyLog(army: Int) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：购买了 " + army + " 兵力")
        onAddLogListener?.onAddLog()
    }

    fun addGeneralsLog(generalsBean: GeneralsBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：获得了武将 " + generalsBean.name)
        onAddLogListener?.onAddLog()
    }

    fun addBigMoneyLog(count: Int) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：在金银岛获得了 " + count * 1000 + "")
        onAddLogListener?.onAddLog()
    }

    fun addFreeGeneralsLog(generalsBean: GeneralsBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：在茅庐获得了 " + generalsBean.name)
        onAddLogListener?.onAddLog()
    }

    fun addBankLog(give: PlayerBean?, get: PlayerBean?, money: Int) {
        logAdapter.addData("系统："+(if (give != null) give.name else "银行")+" 给了 "+ (if (get != null) get.name else " 银行 ")+" "+money)
        onAddLogListener?.onAddLog()
    }

    fun addEquipmentLog(equipmentBean: EquipmentBean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：在商店购买了 " + equipmentBean.name)
        onAddLogListener?.onAddLog()
    }

    fun addPrisonLog(money: Boolean) {
        logAdapter.addData(GameData.INSTANCE.currentPlayer().name + "：我有判头了，真刑"+ if (money) ", 还尼玛罚款5000" else "")
        onAddLogListener?.onAddLog()
    }

}