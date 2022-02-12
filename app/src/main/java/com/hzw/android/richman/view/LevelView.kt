package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.SpecialBean
import kotlinx.android.synthetic.main.view_special.view.*

/**
 * class LevelView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/12
 */
class LevelView  @JvmOverloads constructor(
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
        mTvName.text = specialBean.name
        mIvSpecialBg.setBackgroundColor(ContextCompat.getColor(context, specialBean.color))
    }


}