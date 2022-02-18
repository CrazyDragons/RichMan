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
}