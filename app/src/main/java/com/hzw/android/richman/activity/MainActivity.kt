package com.hzw.android.richman.activity

import android.content.Intent
import android.os.Bundle
import com.hzw.android.richman.R
import com.hzw.android.richman.save.Save
import com.hzw.android.richman.utils.LogUiti
import com.hzw.android.richman.utils.ScreenUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScreenUtil.getScreenWidth(this)
        ScreenUtil.getScreenHeight(this)

        mTvGame.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        mTvGoOn.setOnClickListener {
            LogUiti.Print(Save.INSTANCE.loadMap())
        }

        mTvExit.setOnClickListener {
            Save.INSTANCE.clean()
        }
    }

}