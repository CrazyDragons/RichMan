package com.hzw.android.richman

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit

class Game : AppCompatActivity() {

    var mDisposable: Disposable? = null

    var walkCount = 0
    var cameraOffsetX = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        Handler().postDelayed({
            mPlayer.translationX = mMap.cityViewList[walkCount].x
            mPlayer.translationY = mMap.cityViewList[walkCount].y
            mCamera.smoothScrollTo(mPlayer.x.toInt() - cameraOffsetX, 0)
        }, 200)

        mBtnWalk.setOnClickListener {
            walk()
        }

        val metrics = DisplayMetrics()
        val display: Display = this.windowManager.defaultDisplay
        display.getRealMetrics(metrics)
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        Log.e("-------width", width.toString())
        Log.e("-------height", height.toString())


        cameraOffsetX = (width*0.375).toInt()
    }

    private fun walk() {

        Observable.interval(0, 20, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onNext(t: Long) {

                    val count = (Math.random() * 12 + 1).toInt()

                    mTvWalk.text = count.toString()

                    if (t == 20L) {
                        walkCount += count
                        mDisposable?.dispose()
                        mPlayer.translationX = mMap.cityViewList[walkCount].x
                        mPlayer.translationY = mMap.cityViewList[walkCount].y
                        mCamera.smoothScrollTo(mPlayer.x.toInt() - cameraOffsetX, 0)
                    }

                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

}