package com.hzw.android.richman.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.config.Constants
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.utils.LogUtil
import com.hzw.android.richman.utils.ScreenUtil
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

        LogUtil.print(ScreenUtil.getScreenWidth(this))
        LogUtil.print(ScreenUtil.getScreenHeight(this))

        mTvGame.setOnClickListener(this)
        mTvGoOn.setOnClickListener(this)
        mTvExit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.mTvGame -> {
                startActivity(Intent(this, ReadyActivity::class.java))
                finish()
            }

            R.id.mTvGoOn -> {
                startActivity(
                    Intent(this, GameActivity::class.java).putExtra(
                        (Constants.NEW_GAME),
                        false
                    )
                )
                finish()
            }

            R.id.mTvExit -> {
                GameSave.clean()
                finish()
            }
        }
    }

}