package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.utils.ScreenUtil
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
) : CardView(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_area_info, this)
        radius = ScreenUtil.dp2px(context, 10).toFloat()
    }

    fun setData(areaBean: AreaBean) {
        mTvAreaName.text = areaBean.name
        mTvOwner.visibility = if (areaBean.ownerID == 0) GONE else VISIBLE
        mIvAreaGeneral.visibility = if (areaBean.generals == null) GONE else VISIBLE
        mTvOwner.text = areaBean.owner()?.name
        mTvAreaArmyCost.text =
            String.format(
                resources.getString(R.string.defense_cost),
                Value.DEFENSE_ARMY_COST
            )
        mTvAreaLevel_1.text =
            String.format(
                resources.getString(R.string.area_level_1),
                (areaBean.army * Value.X_AREA_MONEY_LEVEL_1),
                Value.DEFENSE_ARMY_COST * Value.X_AREA_ARMY_LEVEL_1
            )
        mTvAreaLevel_2.text =
            String.format(
                resources.getString(R.string.area_level_2),
                areaBean.army * Value.X_AREA_MONEY_LEVEL_2,
                Value.DEFENSE_ARMY_COST * Value.X_AREA_ARMY_LEVEL_2
            )
        mTvAreaLevel_3.text =
            String.format(
                resources.getString(R.string.area_level_3) , areaBean.army * Value.X_AREA_MONEY_LEVEL_3,
                Value.DEFENSE_ARMY_COST * Value.X_AREA_ARMY_LEVEL_3
            )
        mTvAreaLevel_4.text =
            String.format(
                resources.getString(R.string.area_level_4) , areaBean.army * Value.X_AREA_MONEY_LEVEL_4,
                Value.DEFENSE_ARMY_COST * Value.X_AREA_ARMY_LEVEL_4
            )
        mTvAreaLevel_5.text =
            String.format(
                resources.getString(R.string.area_level_5) , areaBean.army * Value.X_AREA_MONEY_LEVEL_5,
                Value.DEFENSE_ARMY_COST * Value.X_AREA_ARMY_LEVEL_5
            )
        mTvAreaSale.text =
            String.format(resources.getString(R.string.sale_cost), (areaBean.army * Value.X_SALE).toInt())
    }
}