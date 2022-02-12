package com.hzw.android.richman.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.PlayerInfoAdapter
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameLog
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.listener.OnAddLogListener
import com.hzw.android.richman.listener.OnMapClickListener
import com.hzw.android.richman.listener.OnWalkListener
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.ToastUitl
import com.hzw.android.richman.view.AreaInfoView
import com.hzw.android.richman.view.CityInfoView
import com.hzw.android.richman.view.PlayerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit

class GameActivity : BaseActivity(),
    OnWalkListener,
    View.OnClickListener,
    OnMapClickListener,
    OnAddLogListener {

    private var playerViewList = mutableListOf<PlayerView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val isNewGame = intent.getBooleanExtra("newGame", true)
        if (isNewGame) {
            //新游戏
        } else {
            GameSave.INSTANCE.loadMap()
        }

        initViews()

        inItGame()
    }

    private fun initViews() {
        mBtnWalk.setOnClickListener(this)
        mBtnFinishOption.setOnClickListener(this)
        mBaseMap.onMapClickListener = this
        GameLog.INSTANCE.onAddLogListener = this
        mRvPlayerInfo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRvLog.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }


    private fun inItGame() {

        GameData.INSTANCE.load()


        val playerInfoAdapter = PlayerInfoAdapter()
        playerInfoAdapter.setNewInstance(GameData.INSTANCE.playerData)
        mRvPlayerInfo.adapter = playerInfoAdapter

        mRvLog.adapter = GameLog.INSTANCE.logAdapter

        Handler(Looper.getMainLooper()).postDelayed({


            for (item in GameData.INSTANCE.playerData) {

                val playerView = PlayerView(this)
                playerView.setData(item)
                mRootMap.addView(playerView)
                playerViewList.add(playerView)

                movePlayer(playerView, item, 0, this)
            }


            Handler(Looper.getMainLooper()).postDelayed({
                if (!GameData.INSTANCE.playerData[0].isPlayer) {
                    mBtnWalk.performClick()
                }else {
                    setTurn()
                }
            }, 2000)

        }, 200)


    }

    override fun onWalkStart(count: Int, isFinish: Boolean) {

        mTvWalk.text = count.toString()

        if (isFinish) {

            GameLog.INSTANCE.addWalkLog(count)

            movePlayer(
                playerViewList[GameData.INSTANCE.optionPlayerIndex],
                GameData.INSTANCE.currentPlayer(),
                count,
                this
            )
        }
    }

    override fun onWalkFinish(isNewGame: Boolean) {

        if (!isNewGame) {

            optionStatus(walk = isNewGame, finish = !isNewGame && GameData.INSTANCE.currentPlayer().isPlayer)

            optionComputer()

            whichPlayerWalk()

            setTurn()

            if (!GameData.INSTANCE.currentPlayer().isPlayer) {

                Handler(Looper.getMainLooper()).postDelayed({

                    mBtnFinishOption.performClick()
                }, 2000)
            }
        }
    }

    private fun movePlayer(
        playerView: PlayerView,
        playerBean: PlayerBean,
        count: Int,
        onWalkListener: OnWalkListener
    ) {

        playerBean.walkIndex += count
        var restart = false

        Observable.interval(0, 500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long?> {
                override fun onSubscribe(d: Disposable) {
                    playerView.mDisposable = d
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
                        ToastUitl.show("经过了起点")
                        restart = false
                    }


                    showMapInfo(GameData.INSTANCE.mapData[next])

                    if (t == count.toLong()) {
                        playerBean.walkIndex = next
                        playerView.mDisposable?.dispose()
                        onWalkListener.onWalkFinish(count == 0)
                    } else {
                        optionStatus(walk = false, finish = false)
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

    private fun setTurn() {
        for (item in GameData.INSTANCE.playerData) {
            item.isTurn = false
        }
        GameData.INSTANCE.currentPlayer().isTurn = true
    }

    private fun optionComputer() {
        GameLog.INSTANCE.addOptionLog()
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            //点击投掷
            R.id.mBtnWalk -> {
                MapUtil.walk(this)
            }

            //点击结束
            R.id.mBtnFinishOption -> {
                GameLog.INSTANCE.addTurnLog()
                mCamera.smoothScrollTo(playerViewList[GameData.INSTANCE.optionPlayerIndex].x.toInt() - cameraOffsetX, 0)
                if (GameData.INSTANCE.currentPlayer().isPlayer) {
                    optionStatus(walk = true, finish = false)
                } else {
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

    override fun onMapClick(index: Int) {
        showMapInfo(GameData.INSTANCE.mapData[index])
    }

    private fun showMapInfo(baseMapBean: BaseMapBean) {
        mFlInfo.removeAllViews()
        when (baseMapBean) {
            is CityBean -> {
                val mCityInfoView = CityInfoView(this@GameActivity)
                mCityInfoView.setData(baseMapBean)
                mFlInfo.addView(mCityInfoView)
            }

            is AreaBean -> {
                val mAreaInfoView = AreaInfoView(this@GameActivity)
                mAreaInfoView.setData(baseMapBean)
                mFlInfo.addView(mAreaInfoView)
            }
        }
    }

    override fun onAddLog() {
        mRvLog.scrollToPosition(GameLog.INSTANCE.logAdapter.data.size - 1)
    }


}