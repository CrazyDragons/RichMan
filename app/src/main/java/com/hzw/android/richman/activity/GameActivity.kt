package com.hzw.android.richman.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.PlayerInfoAdapter
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.dialog.MapInfoDialog
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameLog
import com.hzw.android.richman.game.GameOption
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.listener.OnAddLogListener
import com.hzw.android.richman.listener.OnMapClickListener
import com.hzw.android.richman.listener.OnOptionListener
import com.hzw.android.richman.listener.OnWalkListener
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.MapUtil.setNextTurn
import com.hzw.android.richman.utils.MapUtil.showTurnTips
import com.hzw.android.richman.utils.ToastUtil
import com.hzw.android.richman.view.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_game.*
import java.util.concurrent.TimeUnit

/**
 * class GameActivity
 *
 * @author CrazyDragon
 * description 游戏主界面
 * note
 * create date 2022/2/9
 */
class GameActivity : BaseActivity(),
    OnWalkListener,
    View.OnClickListener,
    OnMapClickListener,
    OnAddLogListener, OnItemClickListener,OnOptionListener {

    private var playerViewList = mutableListOf<PlayerView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val isNewGame = intent.getBooleanExtra("newGame", true)
        if (isNewGame) {
            //新游戏
        } else {
            GameSave.loadMap()
        }

        initViews()

        inItGame()
    }

    private fun initViews() {
        mBtnWalk.setOnClickListener(this)
        mBtnFinishOption.setOnClickListener(this)
        mRootMap.setOnClickListener(this)
        mBaseMap.onMapClickListener = this
        GameLog.INSTANCE.onAddLogListener = this
        GameOption.onOptionListener = this
        mRvPlayerInfo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRvLog.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }


    private fun inItGame() {

        GameData.INSTANCE.load()

        val playerInfoAdapter = PlayerInfoAdapter()
        playerInfoAdapter.setNewInstance(GameData.INSTANCE.playerData)
        playerInfoAdapter.setOnItemClickListener(this)
        mRvPlayerInfo.adapter = playerInfoAdapter
        mRvLog.adapter = GameLog.INSTANCE.logAdapter

        optionStatus(walk = true, finish = false)

        GameLog.INSTANCE.clear()
        GameLog.INSTANCE.addSystemLog("欢迎来到大富翁")

        Handler(Looper.getMainLooper()).postDelayed({


            for (item in GameData.INSTANCE.playerData) {

                val playerView = PlayerView(this)
                playerView.setData(item)
                mRootMap.addView(playerView)
                playerViewList.add(playerView)

                movePlayer(playerView, item, 0, this)
            }

            showTurnTips(this)

            Handler(Looper.getMainLooper()).postDelayed({
                if (!GameData.INSTANCE.playerData[0].isPlayer) {
                    mBtnWalk.performClick()
                } else {
                    setNextTurn()
                }
            }, 2000)

        }, 200)


    }

    override fun onWalkStart(count: Int, isFinish: Boolean) {

        mTvWalk.text = count.toString()

        if (isFinish) {

            GameLog.INSTANCE.addWalkLog(count)

            movePlayer(
                playerViewList[GameData.INSTANCE.optionPlayerTurnIndex],
                GameData.INSTANCE.currentPlayer(),
                count,
                this
            )
        }
    }

    override fun onWalkFinish() {

        optionStatus(
            walk = false,
            finish = GameData.INSTANCE.currentPlayer().isPlayer && GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_FALSE
        )

        optionComputer()

        refreshViews()

        if (!GameData.INSTANCE.currentPlayer().isPlayer) {
            Handler(Looper.getMainLooper()).postDelayed({

                mBtnFinishOption.performClick()
            }, 2000)
        }
    }

    private fun movePlayer(
        playerView: PlayerView,
        playerBean: PlayerBean,
        count: Int,
        onWalkListener: OnWalkListener
    ) {

        if (count == 0) {
            playerView.translationX = mBaseMap.mapViewList[0].x + playerOffsetX
            playerView.translationY = mBaseMap.mapViewList[0].y + playerOffsetY
            mCamera.smoothScrollTo(playerView.x.toInt() - cameraOffsetX, 0)
            return
        }

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
                        ToastUtil.show("经过了起点")
                        restart = false
                    }


                    if (t == count.toLong()) {
                        playerBean.walkIndex = next
                        playerView.mDisposable?.dispose()
                        onWalkListener.onWalkFinish()
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

    private fun optionComputer() {
        GameLog.INSTANCE.addOptionLog()
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.mRootMap -> {
                mLlOption.visibility = if (mLlOption.visibility == VISIBLE) GONE else VISIBLE
            }

            //点击投掷
            R.id.mBtnWalk -> {
                MapUtil.walk(this)
            }

            //点击结束
            R.id.mBtnFinishOption -> {
                mLlOption.removeAllViews()
                setNextTurn()
                mCamera.smoothScrollTo(
                    playerViewList[GameData.INSTANCE.optionPlayerTurnIndex].x.toInt() - cameraOffsetX,
                    0
                )
                if (GameData.INSTANCE.currentPlayer().isPlayer) {
                    optionStatus(walk = true, finish = false)
                    showTurnTips(this)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        mBtnWalk.performClick()
                    }, 1000)
                }
            }
        }
    }

    private fun optionStatus(walk: Boolean, finish: Boolean) {
        mBtnWalk.isEnabled = walk
        mBtnFinishOption.isEnabled = finish
    }

    override fun onMapClick(index: Int) {
        showMapInfoDialog(GameData.INSTANCE.mapData[index])
    }

    private fun showMapInfoDialog(baseMapBean: BaseMapBean) {
        mLlOption.visibility = GONE
        var view = View(this)
        when (baseMapBean) {
            is CityBean -> {
                val mCityInfoView = CityInfoView(this@GameActivity)
                mCityInfoView.setData(baseMapBean)
                view = mCityInfoView
            }

            is AreaBean -> {
                val mAreaInfoView = AreaInfoView(this@GameActivity)
                mAreaInfoView.setData(baseMapBean)
                view = mAreaInfoView
            }

            is SpecialBean -> {
                val mSpecialInfoView = SpecialInfoView(this@GameActivity)
                mSpecialInfoView.setData(baseMapBean)
                view = mSpecialInfoView
            }
        }

        MapInfoDialog(this, view).show()
    }

    override fun onAddLog() {
        mRvLog.scrollToPosition(GameLog.INSTANCE.logAdapter.data.size - 1)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun refreshViews() {
        mLlOption.visibility = VISIBLE
        mLlOption.removeAllViews()
        when (GameData.INSTANCE.currentMap()) {
            is CityBean -> {
                val cityBean = GameData.INSTANCE.currentMap() as CityBean
                val cityView =
                    mBaseMap.mapViewList[GameData.INSTANCE.currentPlayer().walkIndex] as CityView
                val cityInfoView = CityInfoView(this)
                cityView.setData(cityBean)
                cityInfoView.setData(cityBean)
                val optionView = OptionView(this)
                mLlOption.addView(cityInfoView)
                mLlOption.addView(optionView)
                mRvPlayerInfo.adapter?.notifyDataSetChanged()
                optionStatus(false, GameData.INSTANCE.currentPlayer().status != PlayerBean.STATUS.ATTACK)
            }

            is AreaBean -> {
                val areaInfoView = AreaInfoView(this)
                val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                areaInfoView.setData(areaBean)
                mLlOption.addView(areaInfoView)
            }

            is SpecialBean -> {
                val specialInfoView = SpecialInfoView(this)
                val specialBean = GameData.INSTANCE.currentMap() as SpecialBean
                specialInfoView.setData(specialBean)
                mLlOption.addView(specialInfoView)
            }
        }
        GameLog.INSTANCE.addSystemLog("更新了数据")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mCamera.smoothScrollTo(playerViewList[position].x.toInt() - cameraOffsetX, 0)
    }

    override fun onOptionFinish() {
        refreshViews()
    }
}