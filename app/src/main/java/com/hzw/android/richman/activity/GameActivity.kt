package com.hzw.android.richman.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.date.GameData
import com.hzw.android.richman.listener.OnWalkListener
import com.hzw.android.richman.save.GameSave
import com.hzw.android.richman.utils.LogUiti
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.view.PlayerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit

class GameActivity : BaseActivity(), OnWalkListener, View.OnClickListener {

    private var optionPlayerIndex = 0
    private var playerViewList = mutableListOf<PlayerView>()
    var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val isNewGame = intent.getBooleanExtra("newGame", true)
        if (isNewGame) {
            //新游戏
        } else {
            GameSave.INSTANCE.loadMap()
        }

        mBtnWalk.setOnClickListener(this)
        mBtnFinishOption.setOnClickListener(this)

        inItGame()
    }


    private fun inItGame() {

        GameData.INSTANCE.load()

        Handler(Looper.getMainLooper()).postDelayed({

            for (item in GameData.INSTANCE.playerData) {

                val playerView = PlayerView(this)
                playerView.setData(item)
                mRootMap.addView(playerView)
                playerViewList.add(playerView)

                movePlayer(playerView, item, 0)
            }

            if (!GameData.INSTANCE.playerData[0].isPlayer) {
                mBtnWalk.performClick()
            }

        }, 200)
    }

    override fun onWalkFinish(count: Int, isFinish: Boolean) {

        mTvWalk.text = count.toString()

        if (isFinish) {
            val playerBean = GameData.INSTANCE.playerData[optionPlayerIndex]
            val playerView = playerViewList[optionPlayerIndex]


            movePlayer(playerView, playerBean, count)

            setTurn()


            if (!playerBean.isPlayer) {

                optionComputer()

                Handler(Looper.getMainLooper()).postDelayed({
                    mBtnFinishOption.performClick()
                }, 200)
            }
        }
    }

    private fun movePlayer(playerView: PlayerView, playerBean: PlayerBean, count: Int) {

        playerBean.walkIndex += count
        var restart = false

        Observable.interval(0, 500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onNext(t: Long) {

                    var next = playerBean.walkIndex - count + t.toInt()

                    if (next >= mBaseMap.mapViewList.size) {
                        if (next == mBaseMap.mapViewList.size) {
                            restart = true
                        }
                        next -= mBaseMap.mapViewList.size

                    }

                    playerView.translationX = mBaseMap.mapViewList[next].x + playerOffsetX
                    playerView.translationY = mBaseMap.mapViewList[next].y + playerOffsetY
                    mCamera.smoothScrollTo(playerView.x.toInt() - cameraOffsetX, 0)
                    if (restart) {
                        Toast.makeText(this@GameActivity, "经过了起点", Toast.LENGTH_SHORT).show()
                        restart = false
                    }

                    if (t == count.toLong()) {
                        playerBean.walkIndex = next
                        mDisposable?.dispose()
                    }

                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }

    private fun whichPlayerWalk(): Int {
        for (i in 0 until GameData.INSTANCE.playerData.size) {
            if (GameData.INSTANCE.playerData[i].isTurn) {
                return if (i < GameData.INSTANCE.playerData.size - 1) {
                    i + 1
                } else {
                    0
                }
            }
        }
        return 0
    }

    private fun setTurn() {
        for (item in GameData.INSTANCE.playerData) {
            item.isTurn = false
        }
        GameData.INSTANCE.playerData[optionPlayerIndex].isTurn = true
    }

    private fun optionComputer() {
        LogUiti.Print(GameData.INSTANCE.playerData[optionPlayerIndex].name + "正在操作")
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            //点击投掷
            R.id.mBtnWalk -> {
                optionStatus(walk = false, finish = true)
                if (GameData.INSTANCE.playerData[optionPlayerIndex].isPlayer) {
                    LogUiti.Print("玩家点了投掷")
                } else {
                    optionStatus(walk = false, finish = false)
                    LogUiti.Print(GameData.INSTANCE.playerData[optionPlayerIndex].name + "点了投掷")
                }
                MapUtil.walk(this)
            }

            //点击结束
            R.id.mBtnFinishOption -> {

                if (GameData.INSTANCE.playerData[optionPlayerIndex].isPlayer) {
                    LogUiti.Print("玩家点了结束")
                } else {
                    LogUiti.Print(GameData.INSTANCE.playerData[optionPlayerIndex].name + "点了结束")
                }

                optionPlayerIndex = whichPlayerWalk()

                if (GameData.INSTANCE.playerData[optionPlayerIndex].isPlayer) {
                    LogUiti.Print("轮到玩家")
                    optionStatus(walk = true, finish = false)
                } else {
                    optionStatus(walk = false, finish = false)
                    Handler(Looper.getMainLooper()).postDelayed({
                        mBtnWalk.performClick()
                    }, 200)
                }
            }
        }
    }

    private fun optionStatus(walk: Boolean, finish: Boolean) {
        mBtnWalk.isEnabled = walk
        mBtnFinishOption.isEnabled = finish
    }


}