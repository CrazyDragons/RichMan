package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.CityBean
import kotlinx.android.synthetic.main.view_city.view.*

/**
 * class CityView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
class CityView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_city, this)
    }

    fun setData(cityBean: CityBean) {
        mTvName.text = cityBean.name
        mTvPrice.text = cityBean.buyPrice.toString()
        mIvCityCover.setBackgroundResource(cityBean.cover)

        val colorA = ContextCompat.getColor(context, R.color.colorA)
        val colorB = ContextCompat.getColor(context, R.color.colorB)
        val colorC = ContextCompat.getColor(context, R.color.colorC)
        val colorD = ContextCompat.getColor(context, R.color.colorD)
        val colorE = ContextCompat.getColor(context, R.color.colorE)

        when(cityBean.color) {
            CityBean.Color.A -> {
                mIvCityBg.setBackgroundColor(colorA)
                mTvName.setTextColor(colorA)
                mTvPrice.setBackgroundColor(colorA)
            }
            CityBean.Color.B -> {
                mIvCityBg.setBackgroundColor(colorB)
                mTvName.setTextColor(colorB)
                mTvPrice.setBackgroundColor(colorB)
            }
            CityBean.Color.C -> {
                mIvCityBg.setBackgroundColor(colorC)
                mTvName.setTextColor(colorC)
                mTvPrice.setBackgroundColor(colorC)
            }
            CityBean.Color.D -> {
                mIvCityBg.setBackgroundColor(colorD)
                mTvName.setTextColor(colorD)
                mTvPrice.setBackgroundColor(colorD)
            }
            CityBean.Color.E -> {
                mIvCityBg.setBackgroundColor(colorE)
                mTvName.setTextColor(colorE)
                mTvPrice.setBackgroundColor(colorE)
            }else -> {

            }

        }
    }


}