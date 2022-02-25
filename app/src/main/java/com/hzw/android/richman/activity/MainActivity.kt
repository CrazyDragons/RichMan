package com.hzw.android.richman.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameInit
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.ScreenUtil
import com.hzw.android.richman.utils.ToastUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

/**
 * class MainActivity
 *
 * @author CrazyDragon
 * description 游戏启动界面
 * note
 * create date 2022/2/9
 */
class MainActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.d(ScreenUtil.getScreenWidth(this))
        Logger.d(ScreenUtil.getScreenHeight(this))

        mTvGame.setOnClickListener(this)
        mTvGoOn.setOnClickListener(this)
        mTvDesc.setOnClickListener(this)
        mTvExit.setOnClickListener(this)

        MapUtil.generalsGDP(GameInit.INSTANCE.generals)
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.mTvGame -> {
                startActivity(Intent(this, ReadyActivity::class.java))
            }

            R.id.mTvGoOn -> {
                startActivity(
                    Intent(this, GameActivity::class.java)
                )
            }

            R.id.mTvDesc -> {
                ToastUtil.show("暂未开发此功能")
            }

            R.id.mTvExit -> {
                GameData.INSTANCE.currentPlayer()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mTvGoOn.isEnabled = !GameSave.newGame()
    }

}