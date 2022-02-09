package com.hzw.android.richman.utils

import com.hzw.android.richman.config.Value.*
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

    var mDisposable: Disposable? = null

    fun walk(onWalkListener: OnWalkListener) {
        Observable.interval(0, WALK_TURN_TIME, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onNext(t: Long) {

                    if (t == WALK_TURN) {
                        val count = (Math.random() * MAX_WALK + 1).toInt()
                        onWalkListener.onWalkFinish(count)
                        mDisposable?.dispose()
                    }

                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }
}