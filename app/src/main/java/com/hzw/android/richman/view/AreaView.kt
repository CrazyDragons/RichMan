package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.AreaBean
import kotlinx.android.synthetic.main.view_area.view.*

/**
 * class CityView
 * @author CrazyDragon
 * description 城池视图
 * note
 * create date 2022/2/9
 */
class AreaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_area, this)
    }

    fun setData(areaBean: AreaBean) {
        mTvName.text = areaBean.name
        mTvBuyArmy.text = areaBean.army.toString()
        mIvDefense.visibility = if (areaBean.general == null) GONE else VISIBLE
        mTvOwner.visibility = if (areaBean.owner == null) GONE else VISIBLE
        mTvOwner.text = areaBean.owner?.name
    }


}