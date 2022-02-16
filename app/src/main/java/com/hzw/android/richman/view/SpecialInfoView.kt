package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.cardview.widget.CardView
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.listener.OnSpecialOptionListener
import com.hzw.android.richman.utils.ScreenUtil
import com.hzw.android.richman.utils.ToastUtil
import kotlinx.android.synthetic.main.view_special_info.view.*

/**
 * class SpecialInfoView
 * @author CrazyDragon
 * description 特殊场景面板
 * note
 * create date 2022/2/12
 */
class SpecialInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    lateinit var onSpecialOptionListener: OnSpecialOptionListener
    var army = 0

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_special_info, this)
        radius = ScreenUtil.dp2px(context, 10).toFloat()
        mFlInfo.setOnClickListener(null)

        mSbArmy.max = Value.MAX_BUY_ARMY / Value.X_BUY_ARMY
        mTvMaxArmy.text = Value.MAX_BUY_ARMY.toString()


        mSbArmy.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                army = Value.X_BUY_ARMY * progress
                mTvArmy.text = army.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        mTvSure.setOnClickListener {

            if (GameData.INSTANCE.currentPlayer().money < army * 2) {
                ToastUtil.show("穷逼，钱不够")
                return@setOnClickListener
            }

            GameData.INSTANCE.currentPlayer().army += army
            GameData.INSTANCE.currentPlayer().money += army * Value.X_ARMY_MONEY
            onSpecialOptionListener.onSure()

        }
    }

    fun setData(specialBean: SpecialBean) {
        mFlInfo.setBackgroundResource(specialBean.background)
    }
}