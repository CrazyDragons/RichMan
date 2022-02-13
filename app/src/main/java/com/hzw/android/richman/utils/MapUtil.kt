package com.hzw.android.richman.utils

import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameLog
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
                        onWalkListener.onWalkStart(count, true)
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
        for (i in 0 until GameData.INSTANCE.playerData.size) {
            if (GameData.INSTANCE.playerData[i].isTurn) {
                return if (i < GameData.INSTANCE.playerData.size - 1) {
                    GameData.INSTANCE.optionPlayerIndex = i + 1
                } else {
                    GameData.INSTANCE.optionPlayerIndex = 0
                }
            }
        }
    }

    fun setNextTurn() {
        whichPlayerWalk()
        for (item in GameData.INSTANCE.playerData) {
            item.isTurn = false
        }
        GameData.INSTANCE.currentPlayer().isTurn = true
        GameLog.INSTANCE.addTurnLog()
    }
}