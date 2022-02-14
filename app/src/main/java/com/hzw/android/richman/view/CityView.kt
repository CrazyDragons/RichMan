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
 * description 城池视图
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
        mViewLevel.setLevel(cityBean.level, false)
        mIvDefense.visibility = if (cityBean.generals == null) GONE else VISIBLE
        mTvOwner.visibility = if (cityBean.owner == null) GONE else VISIBLE
        mTvOwner.text = cityBean.owner?.name

        when (cityBean.color) {
            CityBean.Color.RED -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
            }
            CityBean.Color.ORANGE -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOrange))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorOrange))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOrange))
            }
            CityBean.Color.YELLOW -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorYellow))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow))
            }
            CityBean.Color.GREEN -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorGreen))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen))
            }
            CityBean.Color.QING -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorQing))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorQing))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorQing))
            }
            CityBean.Color.BLUE -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorBlue))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue))
            }
            CityBean.Color.PURPLE -> {
                mIvCityBg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPurple))
                mTvName.setTextColor(ContextCompat.getColor(context, R.color.colorPurple))
                mTvPrice.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPurple))
            }
            else -> {

            }

        }
    }


}