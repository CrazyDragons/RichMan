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
import com.hzw.android.richman.config.Value.X_COUNT
import com.hzw.android.richman.config.Value.Y_COUNT
import com.hzw.android.richman.game.GameInit
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

        val sumCount = GameInit.INSTANCE.mapList.size

        val itemWidth = ScreenUtil.screenWidth / Y_COUNT / 2
        val itemHeight = ScreenUtil.screenHeight / Y_COUNT

        val constraintSet = ConstraintSet()

        for (i in 0 until sumCount) {

            var mapItem: View
            when (GameInit.INSTANCE.mapList[i].type) {
                BaseMapBean.MapType.CITY -> {
                    mapItem = CityView(context)
                    mapItem.setData(GameInit.INSTANCE.mapList[i] as CityBean)
                }
                BaseMapBean.MapType.AREA -> {
                    mapItem = AreaView(context)
                    mapItem.setData(GameInit.INSTANCE.mapList[i] as AreaBean)
                }
                else -> {
                    mapItem = SpecialView(context)
                    mapItem.setData(GameInit.INSTANCE.mapList[i] as SpecialBean)
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

            if (i in 1 until X_COUNT) {
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

            if (i in X_COUNT until X_COUNT + Y_COUNT - 1) {
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

            if (i in X_COUNT + Y_COUNT - 1 until X_COUNT + Y_COUNT + X_COUNT - 1) {
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

            if (i in X_COUNT + Y_COUNT + X_COUNT - 2 until X_COUNT + Y_COUNT + X_COUNT + Y_COUNT - 4) {
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


}