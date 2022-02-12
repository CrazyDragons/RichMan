package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.game.GameData
import kotlinx.android.synthetic.main.view_city_info.view.*

/**
 * class CityView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
class CityInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_city_info, this)
    }

    fun setData(cityBean: CityBean) {
        this.visibility = View.VISIBLE
        mTvCityName.text = cityBean.name
        mIvCityCover.setBackgroundResource(cityBean.cover)
        mTvCityLevel.setLevel(cityBean.level, true)
        mTvOwner.text = "拥有者 " + GameData.INSTANCE.currentPlayer().name
        mTvCityLevelCost.text = "城墙建设费用 " + (cityBean.buyPrice / 2)
        mTvCityArmyCost.text = "守城损兵 " + (cityBean.buyPrice / 2)
        mTvCityLevel_0.text = "空城：过路费 " + (cityBean.buyPrice / 2) + "\t驻兵 " + cityBean.buyPrice
        mTvCityLevel_1.text = "小城：过路费 " + (cityBean.buyPrice) + "\t驻兵 " + cityBean.buyPrice * 2
        mTvCityLevel_2.text = "中城：过路费 " + (cityBean.buyPrice * 2) + "\t驻兵 " + cityBean.buyPrice * 4
        mTvCityLevel_3.text = "大城：过路费 " + (cityBean.buyPrice * 4) + "\t驻兵 " + cityBean.buyPrice * 8
        mTvCitySale.text = "抵押价 " + (cityBean.buyPrice / 2).toString()

        val colorA = ContextCompat.getColor(context, R.color.colorA)
        val colorB = ContextCompat.getColor(context, R.color.colorB)
        val colorC = ContextCompat.getColor(context, R.color.colorC)
        val colorD = ContextCompat.getColor(context, R.color.colorD)
        val colorE = ContextCompat.getColor(context, R.color.colorE)

        when (cityBean.color) {
            CityBean.Color.A -> {
                mClInfo.setBackgroundColor(colorA)
            }
            CityBean.Color.B -> {
                mClInfo.setBackgroundColor(colorB)
            }
            CityBean.Color.C -> {
                mClInfo.setBackgroundColor(colorC)
            }
            CityBean.Color.D -> {
                mClInfo.setBackgroundColor(colorD)
            }
            CityBean.Color.E -> {
                mClInfo.setBackgroundColor(colorE)
            }
            else -> {

            }

        }
    }

}