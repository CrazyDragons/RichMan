package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hzw.android.richman.R
import kotlinx.android.synthetic.main.view_root_map.view.*

/**
 * class RooTMapView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/19
 */
class RootMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var playerViewList = mutableListOf<PlayerView>()
    lateinit var mBaseMap: MapView

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_root_map, this)
        this.mBaseMap = baseMap
    }

    fun addPlayerView(playerView: PlayerView) {
        playerViewList.add(playerView)
        addView(playerView)
    }

    fun removePlayerView(playerView: PlayerView) {
        playerViewList.remove(playerView)
        removeView(playerView)
    }
}