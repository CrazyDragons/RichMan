package com.hzw.android.richman.utils

import com.hzw.android.richman.activity.GameActivity
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.dialog.TipsDialog
import com.hzw.android.richman.game.GameData
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
                        onWalkListener.onWalkStart(1, true)
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
            if (GameData.INSTANCE.playerData[i].status == PlayerBean.STATUS.OPTION_FALSE || GameData.INSTANCE.playerData[i].status == PlayerBean.STATUS.OPTION_TRUE) {
                return if (i < GameData.INSTANCE.playerData.size - 1) {
                    GameData.INSTANCE.optionPlayerTurnIndex = i + 1
                } else {
                    GameData.INSTANCE.optionPlayerTurnIndex = 0
                }
            }
        }
    }

    fun setNextTurn() {
        whichPlayerWalk()
        for (item in GameData.INSTANCE.playerData) {
            item.status = PlayerBean.STATUS.READY
        }
        GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.OPTION_FALSE
    }

    fun showTurnTips(activity: GameActivity) {
        TipsDialog(activity, "轮到 "+GameData.INSTANCE.currentPlayer().name).show()
    }
}