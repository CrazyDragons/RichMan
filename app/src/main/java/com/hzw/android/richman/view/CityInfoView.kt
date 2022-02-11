package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.CityBean
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
        mTvCityLevel.text = cityBean.level.toString()
        mTvCityLevelCost.text = "城墙建设费用 " + (cityBean.buyPrice / 2).toString()
        mTvCityLevel_0.text = "空城过路费 " + (cityBean.buyPrice / 2).toString()
        mTvCityLevel_1.text = "小城过路费 " + (cityBean.buyPrice).toString()
        mTvCityLevel_2.text = "中城过路费 " + (cityBean.buyPrice * 2).toString()
        mTvCityLevel_3.text = "大城过路费 " + (cityBean.buyPrice * 4).toString()
        mTvCitySale.text = "抵押价 " + (cityBean.buyPrice / 2).toString()
    }

}