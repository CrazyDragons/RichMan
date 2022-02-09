package com.hzw.android.richman.activity

import android.os.Bundle
import android.os.Handler
import com.hzw.android.richman.R
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.listener.OnWalkListener
import com.hzw.android.richman.utils.MapUtil.walk
import com.hzw.android.richman.utils.ScreenUtil
import com.hzw.android.richman.view.PlayerView
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity(),OnWalkListener {

    var walkCount = 0
    var cameraOffsetX = 0
    var playerOffset = 0

    private var playerView: PlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        playerOffset = ScreenUtil.dp2px(this, Value.PLAYER_OFFSET)
        cameraOffsetX = (ScreenUtil.screenWidth * Value.CAMERA_OFFSET).toInt()

        playerView = PlayerView(this)
        mRootMap.addView(playerView)

        Handler().postDelayed({
            playerView!!.translationX = mBaseMap.mapViewList[walkCount].x + playerOffset
            playerView!!.translationY = mBaseMap.mapViewList[walkCount].y + playerOffset
            mCamera.smoothScrollTo(playerView!!.x.toInt() - cameraOffsetX, 0)
        }, 200)

        mBtnWalk.setOnClickListener {
            walk(this)
        }
    }

    override fun onWalkFinish(count: Int) {
        mTvWalk.text = count.toString()
        walkCount += count
        if (walkCount >= mBaseMap.mapViewList.size) {
            walkCount -= mBaseMap.mapViewList.size
        }
        playerView!!.translationX = mBaseMap.mapViewList[walkCount].x + playerOffset
        playerView!!.translationY = mBaseMap.mapViewList[walkCount].y + playerOffset
        mCamera.smoothScrollTo(playerView!!.x.toInt() - cameraOffsetX, 0)
    }

}