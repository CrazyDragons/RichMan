package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.utils.ScreenUtil

/**
 * class LevelView
 * @author CrazyDragon
 * description 级别视图
 * note
 * create date 2022/2/12
 */
class LevelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_level, this)
    }

    fun setLevel(level: Int, isBig: Boolean) {
        removeAllViews()
        if (level >= 0) {
            for (i in 1..level) {
                val star = ImageView(context)
                val params = LayoutParams(
                    ScreenUtil.dp2px(context, if (isBig) 20 else 10),
                    ScreenUtil.dp2px(context, if (isBig) 20 else 10)
                )
                params.marginEnd = ScreenUtil.dp2px(context, if (isBig) 4 else 2)
                star.layoutParams = params
                star.scaleType = ImageView.ScaleType.CENTER_CROP
                star.setBackgroundResource(if (isBig) R.drawable.icon_star_white else R.drawable.icon_star)
                addView(star)
            }
        } else {
            val star = ImageView(context)
            val params = LayoutParams(
                ScreenUtil.dp2px(context, 10),
                ScreenUtil.dp2px(context, 10)
            )
            star.layoutParams = params
            star.scaleType = ImageView.ScaleType.CENTER_CROP
            star.setBackgroundResource(R.drawable.icon_area_star)
            addView(star)
        }

    }


}