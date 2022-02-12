package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.game.GameData
import kotlinx.android.synthetic.main.view_area_info.view.*

/**
 * class AreaInfoView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/12
 */
class AreaInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_area_info, this)
    }

    fun setData(areaBean: AreaBean) {
        mTvAreaName.text = areaBean.name
        mTvOwner.text = "拥有者 " + GameData.INSTANCE.currentPlayer().name
        mTvAreaArmyCost.text = "守城损兵 " + (areaBean.army / 2)
        mTvAreaLevel_1.text = "一区：过路费 " + (areaBean.army / 2) + "\t驻兵 " + areaBean.army
        mTvAreaLevel_2.text = "二区：过路费 " + (areaBean.army) + "\t驻兵 " + areaBean.army * 2
        mTvAreaLevel_3.text = "三区：过路费 " + (areaBean.army * 2) + "\t驻兵 " + areaBean.army * 4
        mTvAreaLevel_4.text = "四区：过路费 " + (areaBean.army * 4) + "\t驻兵 " + areaBean.army * 8
        mTvAreaLevel_5.text = "五区：过路费 " + (areaBean.army * 8) + "\t驻兵 " + areaBean.army * 16
        mTvAreaSale.text = "抵押价 " + (areaBean.army / 2).toString()
    }
}