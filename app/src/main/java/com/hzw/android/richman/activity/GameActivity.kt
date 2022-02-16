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
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.PlayerInfoAdapter
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.dialog.ComputerOptionTisDialog
import com.hzw.android.richman.dialog.MapInfoDialog
import com.hzw.android.richman.dialog.PlayerInfoDialog
import com.hzw.android.richman.dialog.TipsDialog
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameLog
import com.hzw.android.richman.game.GameOption
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.listener.*
import com.hzw.android.richman.utils.LogUtil
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.MapUtil.setNextTurn
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
    OnAddLogListener, OnItemClickListener, OnBaseCityOptionListener, OnItemLongClickListener,
    OnClickTipsListener, OnSpecialOptionListener {

    private var playerViewList = mutableListOf<PlayerView>()
    private lateinit var computerOptionTipsDialog: ComputerOptionTisDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val isNewGame = intent.getBooleanExtra("newGame", true)
        if (isNewGame) {
            //新游戏
        } else {
            GameSave.loadMap()
        }

        GameData.INSTANCE.load()

        initViews()
    }

    private fun initViews() {
        mBtnWalk.setOnClickListener(this)
        mBtnFinishOption.setOnClickListener(this)
        mRootMap.setOnClickListener(this)
        mBaseMap.onMapClickListener = this
        GameLog.INSTANCE.onAddLogListener = this
        GameOption.onBaseCityOptionListener = this
        GameOption.onSpecialOptionListener = this
        mRvPlayerInfo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRvLog.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        computerOptionTipsDialog = ComputerOptionTisDialog(this)
        val playerInfoAdapter = PlayerInfoAdapter()
        playerInfoAdapter.setNewInstance(GameData.INSTANCE.playerData)
        playerInfoAdapter.setOnItemClickListener(this)
        playerInfoAdapter.setOnItemLongClickListener(this)
        mRvPlayerInfo.adapter = playerInfoAdapter
        mRvLog.adapter = GameLog.INSTANCE.logAdapter
        GameLog.INSTANCE.clear()
        Handler(Looper.getMainLooper()).postDelayed({
            initPlayerViews()
            TipsDialog(this, "准备开始游戏吧", this).show()
        }, 200)
    }


    private fun startGame() {

        optionStatus(walk = true, finish = false)

        showTurnTips(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (!GameData.INSTANCE.playerData[0].isPlayer) {
                mBtnWalk.performClick()
            } else {
                setNextTurn()
            }
        }, 2000)
    }

    private fun initPlayerViews() {
        for (item in GameData.INSTANCE.playerData) {

            val playerView = PlayerView(this)
            playerView.setData(item)
            mRootMap.addView(playerView)
            playerViewList.add(playerView)

            movePlayer(playerView, item, 0, this)
        }
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


        LogUtil.print("结束状态"+GameData.INSTANCE.currentPlayer().status.toString())

        optionStatus(
            walk = false,
            finish = GameData.INSTANCE.currentPlayer().isPlayer && GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_FALSE
        )

        refreshViews()

        if (!GameData.INSTANCE.currentPlayer().isPlayer) {
            Handler(Looper.getMainLooper()).postDelayed({
                GameOption.autoOption()
            }, 1000)
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

        Observable.interval(0, Value.WALK_TIME, TimeUnit.MILLISECONDS)
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
                        GameData.INSTANCE.currentPlayer().money += Value.START_ADD_MONEY
                        ToastUtil.show("经过了起点，获得 " + Value.START_ADD_MONEY)
                    }

                    if (t == count.toLong()) {
                        playerBean.walkIndex = next
                        if (restart) {
                            GameData.INSTANCE.currentPlayer().money += Value.START_ADD_MONEY
                            TipsDialog(
                                this@GameActivity,
                                "恭喜！再获得 " + Value.START_ADD_MONEY
                            ).show()
                        }
                        restart = false
                        onWalkListener.onWalkFinish()
                        playerView.mDisposable?.dispose()
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

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.mRootMap -> {
                if (mBaseMap.mapViewList[GameData.INSTANCE.currentPlayer().walkIndex] is BaseCityBean) {
                    mLlOption.visibility = if (mLlOption.visibility == VISIBLE) GONE else VISIBLE
                }
            }

            //点击投掷
            R.id.mBtnWalk -> {
                if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.PRISON) {
                    TipsDialog(this, "你出狱了，但也只能出狱而已", object : OnClickTipsListener {
                        override fun onClickYes() {
                            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.OPTION_FALSE
                            mBtnFinishOption.performClick()
                        }
                    }).show()
                } else {
                    MapUtil.walk(this)
                }
            }

            //点击结束
            R.id.mBtnFinishOption -> {
                mLlOption.removeAllViews()
                setNextTurn()
                mCamera.smoothScrollTo(
                    playerViewList[GameData.INSTANCE.optionPlayerTurnIndex].x.toInt() - cameraOffsetX,
                    0
                )
                showTurnTips(this)
                if (GameData.INSTANCE.currentPlayer().isPlayer) {
                    optionStatus(walk = true, finish = false)
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
        if (GameData.INSTANCE.mapData[index] is BaseCityBean) {
            showMapInfoDialog(GameData.INSTANCE.mapData[index])
        }
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

            is BaseCityBean -> {
                when (GameData.INSTANCE.currentMap()) {
                    is CityBean -> {
                        val cityBean = GameData.INSTANCE.currentMap() as CityBean
                        val cityView =
                            mBaseMap.mapViewList[GameData.INSTANCE.currentPlayer().walkIndex] as CityView
                        cityView.setData(cityBean)
                        val cityInfoView = CityInfoView(this)
                        cityInfoView.setData(cityBean)
                        mLlOption.addView(cityInfoView)
                    }
                    is AreaBean -> {
                        val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                        val areaView =
                            mBaseMap.mapViewList[GameData.INSTANCE.currentPlayer().walkIndex] as AreaView
                        areaView.setData(areaBean)
                        val areaInfoView = AreaInfoView(this)
                        areaInfoView.setData(areaBean)
                        mLlOption.addView(areaInfoView)
                    }

                }

                if (GameData.INSTANCE.currentPlayer().isPlayer) {
                    val optionView = BaseCityOptionView(this)
                    mLlOption.addView(optionView)
                }

                optionStatus(
                    false,
                    GameData.INSTANCE.currentPlayer().status != PlayerBean.STATUS.ATTACK
                )
            }

            is SpecialBean -> {
                val specialInfoView = SpecialInfoView(this)
                specialInfoView.onSpecialOptionListener = this
                val specialBean = GameData.INSTANCE.currentMap() as SpecialBean
                specialInfoView.setData(specialBean)
                mLlOption.addView(specialInfoView)
            }
        }
        mRvPlayerInfo.adapter?.notifyDataSetChanged()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mCamera.smoothScrollTo(playerViewList[position].x.toInt() - cameraOffsetX, 0)
    }

    override fun onItemLongClick(
        adapter: BaseQuickAdapter<*, *>,
        view: View,
        position: Int
    ): Boolean {
        val playerInfoView = PlayerInfoView(this)
        playerInfoView.setData(GameData.INSTANCE.playerData[position])
        PlayerInfoDialog(this, playerInfoView).show()
        return true
    }

    override fun onOnceOptionFinish(needFinish: Boolean) {
        refreshViews()
        if (!needFinish) {
            if (!GameData.INSTANCE.currentPlayer().isPlayer) {
                Handler(Looper.getMainLooper()).postDelayed({
                    GameOption.autoOption()
                }, 1000)
            }
        }
    }

    override fun onAllOptionFinish() {
        computerOptionTipsDialog.dismiss()
        mBtnFinishOption.performClick()
    }

    private fun showTurnTips(activity: GameActivity) {
        if (GameData.INSTANCE.currentPlayer().isPlayer) {
            TipsDialog(activity, "轮到 " + GameData.INSTANCE.currentPlayer().name).show()
        } else {
            computerOptionTipsDialog.show()
        }
    }

    override fun onClickYes() {
        startGame()
    }

    override fun onSure() {
        refreshViews()
        mLlOption.removeAllViews()
        if (!GameData.INSTANCE.currentPlayer().isPlayer) {
            Handler(Looper.getMainLooper()).postDelayed({
                computerOptionTipsDialog.dismiss()
                mBtnFinishOption.performClick()
            }, 1000)
        }
    }

}