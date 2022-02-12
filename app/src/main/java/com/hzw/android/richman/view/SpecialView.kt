package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.SpecialBean
import kotlinx.android.synthetic.main.view_special.view.*

/**
 * class CityView
 * @author CrazyDragon
 * description 特殊场景视图
 * note
 * create date 2022/2/9
 */
class SpecialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_special, this)
    }

    fun setData(specialBean: SpecialBean) {
        mIvSpecialBg.setImageResource(specialBean.background)
    }


}