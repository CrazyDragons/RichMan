package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.PlayerBean
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.view_player.view.*

/**
 * class CityView
 * @author CrazyDragon
 * description 角色视图
 * note
 * create date 2022/2/9
 */
class PlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var mDisposable: Disposable? = null

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_player, this)
    }

    fun setData(playerBean: PlayerBean) {
        mTvName.text = playerBean.name
    }

}