package com.hzw.android.richman.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.PlayerInfoAdapter
import com.hzw.android.richman.adapter.StockAdapter
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.dialog.*
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameLog
import com.hzw.android.richman.game.GameOption
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.listener.*
import com.hzw.android.richman.utils.LogUtil
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.MapUtil.setNextTurn
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
    OnClickTipsListener, OnSpecialOptionListener, OnRefreshListener {

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
        mRvStock.layoutManager = GridLayoutManager(this, 6)
        mRvPlayerInfo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRvLog.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        computerOptionTipsDialog = ComputerOptionTisDialog(this)
        val stockAdapter = StockAdapter()
        val playerInfoAdapter = PlayerInfoAdapter()
        stockAdapter.setOnItemClickListener(this)
        playerInfoAdapter.setOnItemClickListener(this)
        playerInfoAdapter.setOnItemLongClickListener(this)
        mRvStock.adapter = stockAdapter
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

        TipsDialog(this, "轮到 " + GameData.INSTANCE.playerData[0].name).show()

        if (!GameData.INSTANCE.playerData[0].isPlayer) {
            mBtnWalk.performClick()
        } else {
            setNextTurn()
        }
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


        LogUtil.print("结束状态" + GameData.INSTANCE.currentPlayer().status.toString())

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

                @SuppressLint("NotifyDataSetChanged")
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
                        when (GameData.INSTANCE.currentPlayer().bank) {
                            PlayerBean.BANK.ABC -> {
                                GameData.INSTANCE.currentPlayer().money += Value.START_ADD_MONEY
                            }
                            PlayerBean.BANK.BOC -> {
                                GameData.INSTANCE.currentPlayer().money += if (Math.random() > 0.5) Value.START_ADD_MONEY * 2 else 0
                            }
                            PlayerBean.BANK.BOCM -> {
                                GameData.INSTANCE.currentPlayer().money += (GameData.INSTANCE.currentPlayer().money * 0.2).toInt()
                            }
                            PlayerBean.BANK.CCB -> {
                                GameData.INSTANCE.currentPlayer().money += t.toInt() * 500
                            }
                            PlayerBean.BANK.ICBC -> {
                                GameData.INSTANCE.currentPlayer().money += GameData.INSTANCE.currentPlayer().stockNumber() * 100
                            }
                        }

                    }

                    if (t == count.toLong()) {


                        if (count == 1) {
                            when (GameData.INSTANCE.currentPlayer().bank) {
                                PlayerBean.BANK.ABC -> {
                                    GameData.INSTANCE.currentPlayer().money -= Value.START_ADD_MONEY/2
                                }
                                PlayerBean.BANK.BOC -> {
                                    GameData.INSTANCE.currentPlayer().money -= if (Math.random() > 0.5) Value.START_ADD_MONEY else 0
                                }
                                PlayerBean.BANK.BOCM -> {
                                    GameData.INSTANCE.currentPlayer().money -= (GameData.INSTANCE.currentPlayer().money * 0.15).toInt()
                                }
                                PlayerBean.BANK.CCB -> {
                                    GameData.INSTANCE.currentPlayer().money -= 500
                                }
                                PlayerBean.BANK.ICBC -> {
                                    GameData.INSTANCE.currentPlayer().money -= GameData.INSTANCE.currentPlayer().stockNumber() * 20
                                }
                            }
                        }


                        if (count == 12) {
                            when (GameData.INSTANCE.currentPlayer().bank) {
                                PlayerBean.BANK.ABC -> {
                                    GameData.INSTANCE.currentPlayer().money += Value.START_ADD_MONEY/2
                                }
                                PlayerBean.BANK.BOC -> {
                                    GameData.INSTANCE.currentPlayer().money += if (Math.random() > 0.5) Value.START_ADD_MONEY else 0
                                }
                                PlayerBean.BANK.BOCM -> {
                                    GameData.INSTANCE.currentPlayer().money += (GameData.INSTANCE.currentPlayer().money * 0.05).toInt()
                                }
                                PlayerBean.BANK.CCB -> {
                                    GameData.INSTANCE.currentPlayer().money += 1500
                                }
                                PlayerBean.BANK.ICBC -> {
                                    GameData.INSTANCE.currentPlayer().money += GameData.INSTANCE.currentPlayer().stockNumber() * 20
                                }
                            }
                        }





                        playerBean.walkIndex = next


                        if (restart) {
                            whenOnlyStart()
                        }



                        mRvStock.adapter?.notifyDataSetChanged()
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
                if (GameData.INSTANCE.currentPlayer().money < 0) {
                    TipsDialog(this, "先把欠银行的钱还了吧", object : OnClickTipsListener{
                        override fun onClickYes() {
                            SaleDialog(this@GameActivity, this@GameActivity).show()
                        }
                    }).show()
                    return

//                    if (!GameData.INSTANCE.currentPlayer().isPlayer) {
//                        computerOptionTipsDialog.dismiss()
//                    }
//
//                    val killIndex = GameData.INSTANCE.optionPlayerTurnIndex
//
//                    setNextTurn()
//
//
//                    GameLog.INSTANCE.addSystemLog(GameData.INSTANCE.playerData[killIndex].name+"退出游戏")
//                    GameData.INSTANCE.playerData.remove(GameData.INSTANCE.playerData[killIndex])
//                    playerViewList.remove(playerViewList[killIndex])
//                    mRvPlayerInfo.adapter?.notifyDataSetChanged()
//
//
//                    if (GameData.INSTANCE.playerData.size == 1) {
//                        GameData.INSTANCE.optionPlayerTurnIndex = 0
//                        TipsDialog(this, GameData.INSTANCE.currentPlayer().name+"，你才是真正的三国无双！").show()
//                    }
//
//                    mBtnFinishOption.performClick()
//                    return
                }

                if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.PRISON) {
                    TipsDialog(this, "还有一年徒刑", object : OnClickTipsListener {
                        override fun onClickYes() {
                            GameData.INSTANCE.currentPlayer().status =
                                PlayerBean.STATUS.OPTION_FALSE
                            mBtnFinishOption.performClick()
                        }
                    }).show()
                } else {
                    MapUtil.walk(this)
                }
            }

            //点击结束
            R.id.mBtnFinishOption -> {

                if (GameData.INSTANCE.currentPlayer().money < 0) {
                    TipsDialog(this, "注意，刚刚银行已经帮你垫付费用，下回合将进行拍卖", object : OnClickTipsListener{
                        override fun onClickYes() {
                            onClickFinish()
                        }
                    }).show()
                }else {
                    onClickFinish()
                }
            }
        }
    }

    private fun onClickFinish() {
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
        if (adapter is PlayerInfoAdapter) {
            mCamera.smoothScrollTo(playerViewList[position].x.toInt() - cameraOffsetX, 0)
        } else if (adapter is StockAdapter) {
            StockDialog(this, adapter.data[position], this).show()
        }
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onRefreshData() {
        mRvPlayerInfo.adapter?.notifyDataSetChanged()
    }

    private fun whenOnlyStart() {

    }

}