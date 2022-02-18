package com.hzw.android.richman.utils

import android.widget.TextView
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.listener.OnCountListener
import com.hzw.android.richman.listener.OnWalkListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * class MapUtil
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
object MapUtil {

    var mWalkDisposable: Disposable? = null

    fun walk(onWalkListener: OnWalkListener) {
        Observable.interval(0, Value.WALK_TURN_TIME, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onSubscribe(d: Disposable) {
                    mWalkDisposable = d
                }

                override fun onNext(t: Long) {
                    val count = (Math.random() * Value.MAX_WALK + 1).toInt()
                    onWalkListener.onWalkStart(count, false)
                    if (t == Value.WALK_TURN) {
                        onWalkListener.onWalkStart(Value.TEST, true)
                        mWalkDisposable?.dispose()
                    }

                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    private fun whichPlayerWalk() {

        if (GameData.INSTANCE.optionPlayerTurnIndex < GameData.INSTANCE.playerData.size - 1) {
            GameData.INSTANCE.optionPlayerTurnIndex++
        } else {
            GameData.INSTANCE.turnCount++
            if (GameData.INSTANCE.turnCount % 5 == 0) {
                for (stock in GameData.INSTANCE.stocksData) {
                    stock.randomX()
                }
            }
            GameData.INSTANCE.optionPlayerTurnIndex = 0
            for (stock in GameData.INSTANCE.stocksData) {
                stock.change()
            }
        }
    }

    fun setNextTurn() {
        whichPlayerWalk()
        for (item in GameData.INSTANCE.playerData) {
            if (item.status != PlayerBean.STATUS.PRISON) {
                item.status = PlayerBean.STATUS.READY
            }
        }
        if (GameData.INSTANCE.currentPlayer().status != PlayerBean.STATUS.PRISON) {
            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.OPTION_FALSE
        }
    }

    fun judgeAllColor(baseCityBean: BaseCityBean): Boolean {
        if (baseCityBean.owner == null) {
            return false
        }
        if (baseCityBean is CityBean) {
            val color = baseCityBean.color
            var x = 0
            for (item in baseCityBean.owner!!.city) {
                if (item is CityBean && item.color == color) {
                    x += 1
                }
            }
            return if (x == 5) {
                true
            } else color == CityBean.Color.PURPLE && x == 4
        }
        if (baseCityBean is AreaBean) {
            return baseCityBean.owner!!.allArea() == 5
        }
        return false
    }

    fun count(textView: TextView, onCountListener: OnCountListener) {
        Observable.interval(0, Value.WALK_TURN_TIME, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onSubscribe(d: Disposable) {
                    mWalkDisposable = d
                }

                override fun onNext(t: Long) {
                    val count = (Math.random() * Value.MAX_WALK + 1).toInt()
                    textView.text = count.toString()
                    if (t == Value.WALK_TURN) {
                        onCountListener.onCount(count)
                        mWalkDisposable?.dispose()
                    }

                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    fun bankMsg(count: Int): String {
        return when (count) {
            in 1..3 -> {
                "向其他玩家打款1000"
            }
            in 4..6 -> {
                "向银行存款1000"
            }
            in 7..9 -> {
                "银行向你打款1000"
            }
            in 10..12 -> {
                "其他玩家向你打款1000"
            }
            else -> ""
        }
    }

    fun prisonMsg(count: Int): String {
        return when (count) {
            in 1..2 -> {
                "坐牢并罚款5000"
            }
            in 3..10 -> {
                "坐牢"
            }
            in 11..12 -> {
                "无罪释放"
            }
            else -> ""
        }
    }

    fun buffDesc(buff: PlayerBean.BUFF): String {
        return when(buff) {
            PlayerBean.BUFF.ADD_COST -> "收租金增加10%"
            PlayerBean.BUFF.REDUCE_COST -> "交租金减少10%"
            PlayerBean.BUFF.ADD_ATTACK_ARMY -> "驻兵增加10%"
            PlayerBean.BUFF.REDUCE_ATTACK_ARMY -> "损兵减加10%"
            PlayerBean.BUFF.ADD_ATTACK -> "攻击增加5"
            PlayerBean.BUFF.ADD_DEFENSE -> "防御增加5"
            PlayerBean.BUFF.ADD_MONEY -> "初始金额+50%"
            PlayerBean.BUFF.ADD_ARMY -> "初始金额+50%"
            PlayerBean.BUFF.ADD_GENERALS -> "初始武将+2"
            PlayerBean.BUFF.ADD_EQUIPMENTS -> "初始道具+2"
            PlayerBean.BUFF.ADD_CITY -> "初始城池+1"
            PlayerBean.BUFF.ADD_STOCK -> "初始股票+50股"
        }
    }
    
    fun bankDesc(bank: PlayerBean.BANK):String {
        return when(bank) {
            PlayerBean.BANK.ABC -> {
                "经过起点：得2000\n12点奖励：得1000\n1点惩罚：亏1000"
            }
            PlayerBean.BANK.BOC -> {
                "经过起点：得4000（50%）\n12点奖励：得2000（50%）\n1点惩罚：亏2000（50%）"
            }
            PlayerBean.BANK.BOCM -> {
                "经过起点：得本金20%\n12点奖励：得本金5%\n1点惩罚：亏本金15%"
            }
            PlayerBean.BANK.CCB -> {
                "经过起点：得距离起点步数*500\n12点奖励：得500\n1点惩罚：亏1500"
            }
            PlayerBean.BANK.ICBC -> {
                "经过起点：得股票数*100\n12点奖励：得股票数*20\n1点惩罚：亏股票数*20"
            }
        }
    }
}