package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.config.Value
import kotlinx.android.synthetic.main.view_area_info.view.*

/**
 * class AreaInfoView
 * @author CrazyDragon
 * description 战区信息面板
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
        mTvOwner.visibility = if (areaBean.owner == null) GONE else VISIBLE
        mTvOwner.text = String.format(
            resources.getString(R.string.owner),
            areaBean.owner?.name
        )
        mTvAreaArmyCost.text =
            String.format(
                resources.getString(R.string.defense_cost),
                (areaBean.army * Value.DEFENSE_ARMY_COST_X).toInt()
            )
        mTvAreaLevel_1.text =
            String.format(
                resources.getString(R.string.area_level_1),
                (areaBean.army * Value.LEVEL_AREA_MONEY_1).toInt(),
                areaBean.army * Value.LEVEL_AREA_ARMY_1
            )
        mTvAreaLevel_2.text =
            String.format(
                resources.getString(R.string.area_level_2),
                areaBean.army * Value.LEVEL_AREA_MONEY_2,
                areaBean.army * Value.LEVEL_AREA_ARMY_2
            )
        mTvAreaLevel_3.text =
            String.format(
                resources.getString(R.string.area_level_3) , areaBean.army * Value.LEVEL_AREA_MONEY_3,
                areaBean.army * Value.LEVEL_AREA_ARMY_3
            )
        mTvAreaLevel_4.text =
            String.format(
                resources.getString(R.string.area_level_4) , areaBean.army * Value.LEVEL_AREA_MONEY_4,
                areaBean.army * Value.LEVEL_AREA_ARMY_4
            )
        mTvAreaLevel_5.text =
            String.format(
                resources.getString(R.string.area_level_4) , areaBean.army * Value.LEVEL_AREA_MONEY_5,
                areaBean.army * Value.LEVEL_AREA_ARMY_5
            )
        mTvAreaSale.text =
            String.format(resources.getString(R.string.sale_cost), (areaBean.army * Value.SALE_X).toInt())
    }
}