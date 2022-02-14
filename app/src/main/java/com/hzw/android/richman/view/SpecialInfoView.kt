package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.utils.ScreenUtil
import kotlinx.android.synthetic.main.view_special_info.view.*

/**
 * class SpecialInfoView
 * @author CrazyDragon
 * description 特殊场景面板
 * note
 * create date 2022/2/12
 */
class SpecialInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_special_info, this)
        radius = ScreenUtil.dp2px(context, 10).toFloat()
    }

    fun setData(specialBean: SpecialBean) {
        mClInfo.setBackgroundResource(specialBean.background)
    }
}