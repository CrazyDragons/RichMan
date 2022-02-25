package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.utils.ScreenUtil
import kotlinx.android.synthetic.main.view_city_info.view.*

/**
 * class CityView
 * @author CrazyDragon
 * description 城池信息面板
 * note
 * create date 2022/2/9
 */
class CityInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_city_info, this)
        radius = ScreenUtil.dp2px(context, 10).toFloat()
    }

    fun setData(cityBean: CityBean) {
        this.visibility = View.VISIBLE
        mTvCityName.text = cityBean.name
        mIvCityCover.setBackgroundResource(cityBean.cover)
        mTvCityLevel.setLevel(cityBean.level, true)
        mTvOwner.visibility = if (cityBean.owner == null) GONE else VISIBLE
        mIvCityGeneral.visibility = if (cityBean.generals == null) GONE else VISIBLE
        mTvOwner.text = cityBean.owner?.name
        mTvCityLevelCost.text =
            String.format(resources.getString(R.string.level_cost), (cityBean.buyPrice * Value.X_LEVEL_CITY_COST).toInt())
        mTvCityArmyCost.text =
            String.format(resources.getString(R.string.defense_cost), Value.DEFENSE_ARMY_COST)
        mTvCityLevel_0.text =
            String.format(
                resources.getString(R.string.city_level_0),
                (cityBean.buyPrice * Value.X_CITY_MONEY_LEVEL_0).toInt(),
                (Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_0).toInt()
            )
        mTvCityLevel_1.text =
            String.format(
                resources.getString(R.string.city_level_1),
                cityBean.buyPrice * Value.X_CITY_MONEY_LEVEL_1,
                Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_1
            )
        mTvCityLevel_2.text =
            String.format(
                resources.getString(R.string.city_level_2) , cityBean.buyPrice * Value.X_CITY_MONEY_LEVEL_2,
                Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_2
            )
        mTvCityLevel_3.text =
            String.format(
                resources.getString(R.string.city_level_3) , cityBean.buyPrice * Value.X_CITY_MONEY_LEVEL_3,
                Value.DEFENSE_ARMY_COST * Value.X_CITY_ARMY_LEVEL_3
            )
        mTvCitySale.text =
            String.format(resources.getString(R.string.sale_cost), (cityBean.buyPrice * Value.X_SALE).toInt())

        when (cityBean.color) {
            CityBean.Color.RED -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
            }
            CityBean.Color.ORANGE -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOrange))
            }
            CityBean.Color.YELLOW -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow))
            }
            CityBean.Color.GREEN -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen))
            }
            CityBean.Color.QING -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorQing))
            }
            CityBean.Color.BLUE -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue))
            }
            CityBean.Color.PURPLE -> {
                mClInfo.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPurple))
            }
            else -> {

            }

        }
    }

}