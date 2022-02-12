package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.AreaBean
import kotlinx.android.synthetic.main.view_area.view.*
import kotlinx.android.synthetic.main.view_area.view.mTvOwner
import kotlinx.android.synthetic.main.view_city.view.*
import kotlinx.android.synthetic.main.view_city.view.mTvName

/**
 * class CityView
 * @author CrazyDragon
 * description
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
        mTvOwner.text = areaBean.name
    }


}