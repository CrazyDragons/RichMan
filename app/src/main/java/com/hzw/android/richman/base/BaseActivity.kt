package com.hzw.android.richman.base

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.utils.ScreenUtil

/**
 * class BaseActivity
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
open class BaseActivity : AppCompatActivity() {

    var cameraOffsetX = 0
    var playerOffsetX = 0
    var playerOffsetY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        playerOffsetX = ScreenUtil.dp2px(this, Value.PLAYER_OFFSET_X)
        playerOffsetY = ScreenUtil.dp2px(this, Value.PLAYER_OFFSET_Y)
        cameraOffsetX = (ScreenUtil.screenWidth * Value.CAMERA_OFFSET).toInt()
    }
}