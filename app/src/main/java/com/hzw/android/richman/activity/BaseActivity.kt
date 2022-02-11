package com.hzw.android.richman.activity

import android.os.Bundle
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
    var playerOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        playerOffset = ScreenUtil.dp2px(this, Value.PLAYER_OFFSET)
        cameraOffsetX = (ScreenUtil.screenWidth * Value.CAMERA_OFFSET).toInt()
    }
}