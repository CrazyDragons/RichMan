package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.config.Value.COUNT_MAP_X
import com.hzw.android.richman.config.Value.COUNT_MAP_Y
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.listener.OnMapClickListener
import com.hzw.android.richman.utils.ScreenUtil

/**
 * class MapView
 * @author CrazyDragon
 * description 地图视图
 * note
 * create date 2022/2/9
 */
class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var mapViewList = mutableListOf<View>()
    var onMapClickListener: OnMapClickListener? = null

    init {

        val sumCount = GameData.INSTANCE.mapData.size

        val itemWidth = ScreenUtil.screenWidth / COUNT_MAP_Y / 2
        val itemHeight = ScreenUtil.screenHeight / COUNT_MAP_Y

        val constraintSet = ConstraintSet()

        for (i in 0 until sumCount) {

            var mapItem: View
            when (GameData.INSTANCE.mapData[i].type) {
                BaseMapBean.MapType.CITY -> {
                    mapItem = CityView(context)
                    mapItem.setData(GameData.INSTANCE.mapData[i] as CityBean)
                }
                BaseMapBean.MapType.AREA -> {
                    mapItem = AreaView(context)
                    mapItem.setData(GameData.INSTANCE.mapData[i] as AreaBean)
                }
                else -> {
                    mapItem = SpecialView(context)
                    mapItem.setData(GameData.INSTANCE.mapData[i] as SpecialBean)
                }
            }
            mapItem.setOnClickListener {
                onMapClickListener?.onMapClick(i)
            }

            mapItem.id = View.generateViewId()

            constraintSet.constrainWidth(mapItem.id, itemWidth)
            constraintSet.constrainHeight(mapItem.id, itemHeight)

            if (i == 0) {
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM
                )
            }

            if (i in 1 until COUNT_MAP_X) {
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.END,
                    mapViewList[i - 1].id,
                    ConstraintSet.START
                )
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.BOTTOM,
                    mapViewList[i - 1].id,
                    ConstraintSet.BOTTOM
                )
            }

            if (i in COUNT_MAP_X until COUNT_MAP_X + COUNT_MAP_Y - 1) {
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.BOTTOM,
                    mapViewList[i - 1].id,
                    ConstraintSet.TOP
                )
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.START,
                    mapViewList[i - 1].id,
                    ConstraintSet.START
                )
            }

            if (i in COUNT_MAP_X + COUNT_MAP_Y - 1 until COUNT_MAP_X + COUNT_MAP_Y + COUNT_MAP_X - 1) {
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.START,
                    mapViewList[i - 1].id,
                    ConstraintSet.END
                )
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.TOP,
                    mapViewList[i - 1].id,
                    ConstraintSet.TOP
                )
            }

            if (i in COUNT_MAP_X + COUNT_MAP_Y + COUNT_MAP_X - 2 until COUNT_MAP_X + COUNT_MAP_Y + COUNT_MAP_X + COUNT_MAP_Y - 4) {
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.TOP,
                    mapViewList[i - 1].id,
                    ConstraintSet.BOTTOM
                )
                constraintSet.connect(
                    mapItem.id,
                    ConstraintSet.START,
                    mapViewList[i - 1].id,
                    ConstraintSet.START
                )
            }

            addView(mapItem)
            mapViewList.add(mapItem)
        }

        constraintSet.applyTo(this)

    }

    fun update() {
        for (view in mapViewList) {
            if (view is CityView) {
                view.update()
            }
            if (view is AreaView) {
                view.update()
            }
        }
    }

}