package com.hzw.android.richman

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
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

    fun setData(i: Int) {
        mTvName.text = "建筑$i"
    }


}