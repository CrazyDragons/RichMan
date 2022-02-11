package com.hzw.android.richman.activity

import android.os.Bundle
import android.os.Handler
import com.hzw.android.richman.R
import com.hzw.android.richman.date.GameData
import com.hzw.android.richman.listener.OnWalkListener
import com.hzw.android.richman.utils.LogUiti
import com.hzw.android.richman.utils.MapUtil.walk
import com.hzw.android.richman.view.PlayerView
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity(), OnWalkListener {

    private var index = 0
    private var playerViewList = mutableListOf<PlayerView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val isNewGame = intent.getBooleanExtra("newGame", true)


        mBtnWalk.setOnClickListener {
            if (GameData.INSTANCE.playerData[index].isPlayer) {
                LogUiti.Print("玩家点了开始")
            } else {
                LogUiti.Print(GameData.INSTANCE.playerData[index].name + "点了投掷")
            }
            walk(this)
        }

        mBtnFinishOption.setOnClickListener {
            if (GameData.INSTANCE.playerData[index].isPlayer) {
                LogUiti.Print("玩家点了结束")
            }else {
                LogUiti.Print(GameData.INSTANCE.playerData[index].name + "点了结束")
            }
            index = whichPlayerWalk()
//            LogUiti.Print("下一次操作的角色位置" + index)

            if (!GameData.INSTANCE.playerData[index].isPlayer) {

                Handler().postDelayed({
                    mBtnWalk.performClick()
                }, 200)


            } else {
                LogUiti.Print("轮到玩家")
            }
        }

        inItGame()

        if (!GameData.INSTANCE.playerData[0].isPlayer) {
            mBtnWalk.performClick()
        }
    }


    private fun inItGame() {

        GameData.INSTANCE.load()

        Handler().postDelayed({

            for (item in GameData.INSTANCE.playerData) {

                val playerView = PlayerView(this)
                playerView.setData(item)
                mRootMap.addView(playerView)
                playerViewList.add(playerView)

                playerView.translationX = mBaseMap.mapViewList[0].x + playerOffset
                playerView.translationY = mBaseMap.mapViewList[0].y + playerOffset
                mCamera.smoothScrollTo(playerView.x.toInt() - cameraOffsetX, 0)
            }

        }, 200)


    }

    override fun onWalkFinish(count: Int) {
        mTvWalk.text = count.toString()

        val playerBean = GameData.INSTANCE.playerData[index]
        val playerView = playerViewList[index]

        playerBean.walkIndex += count
        if (playerBean.walkIndex >= mBaseMap.mapViewList.size) {
            playerBean.walkIndex -= mBaseMap.mapViewList.size
        }
        playerView.translationX = mBaseMap.mapViewList[playerBean.walkIndex].x + playerOffset
        playerView.translationY = mBaseMap.mapViewList[playerBean.walkIndex].y + playerOffset
        mCamera.smoothScrollTo(playerView.x.toInt() - cameraOffsetX, 0)

        setTurn()


        if (!playerBean.isPlayer) {
            LogUiti.Print(GameData.INSTANCE.playerData[index].name+"正在操作")
            Handler().postDelayed({
                mBtnFinishOption.performClick()
            }, 200)
        }
    }

    private fun whichPlayerWalk(): Int {
        for (i in 0 until GameData.INSTANCE.playerData.size) {
            if (GameData.INSTANCE.playerData[i].isTurn) {
                if (i < GameData.INSTANCE.playerData.size - 1) {
                    return i + 1
                }else {
                    return 0
                }
            }
        }
        return 0
    }

    private fun setTurn() {
        for (item in GameData.INSTANCE.playerData) {
            item.isTurn = false
        }
        GameData.INSTANCE.playerData[index].isTurn = true
    }


}