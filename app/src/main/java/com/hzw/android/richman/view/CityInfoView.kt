package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
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
        mTvOwner.text =
            String.format(resources.getString(R.string.owner), GameData.INSTANCE.currentPlayer().name)
        mTvCityLevelCost.text =
            String.format(resources.getString(R.string.level_cost), (cityBean.buyPrice * Value.LEVEL_CITY_COST_X).toInt())
        mTvCityArmyCost.text =
            String.format(resources.getString(R.string.defense_cost), (cityBean.buyPrice * Value.DEFENSE_ARMY_COST_X).toInt())
        mTvCityLevel_0.text =
            String.format(
                resources.getString(R.string.city_level_0),
                (cityBean.buyPrice * Value.LEVEL_CITY_MONEY_0).toInt(),
                (cityBean.buyPrice * Value.LEVEL_CITY_ARMY_0).toInt()
            )
        mTvCityLevel_1.text =
            String.format(
                resources.getString(R.string.city_level_1),
                cityBean.buyPrice * Value.LEVEL_CITY_MONEY_1,
                cityBean.buyPrice * Value.LEVEL_CITY_ARMY_1
            )
        mTvCityLevel_2.text =
            String.format(
                resources.getString(R.string.city_level_2) , cityBean.buyPrice * Value.LEVEL_CITY_MONEY_2,
                cityBean.buyPrice * Value.LEVEL_CITY_ARMY_2
            )
        mTvCityLevel_3.text =
            String.format(
                resources.getString(R.string.city_level_3) , cityBean.buyPrice * Value.LEVEL_CITY_MONEY_3,
                cityBean.buyPrice * Value.LEVEL_CITY_ARMY_3
            )
        mTvCitySale.text =
            String.format("抵押价 %d", (cityBean.buyPrice * Value.SALE_X).toInt())

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