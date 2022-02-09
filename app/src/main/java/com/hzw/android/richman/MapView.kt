package com.hzw.android.richman

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * class MapView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/9
 */
class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var cityViewList = mutableListOf<CityView>()


    init {
        val xCount = 30
        val yCount = 10
        val sumCount = (xCount+yCount)*2 - 4


        val itemWidth = (ScreenUtils.getScreenWidth(context) - ScreenUtils.dp2px(context, 10))/10
        val itemHeight = (ScreenUtils.getScreenHeight(context) - ScreenUtils.dp2px(context, 10))/10

        val constraintSet = ConstraintSet()

        for (i in 0 until sumCount) {
            val mapItem = CityView(context)
            mapItem.setData(i)
            mapItem.id = View.generateViewId()

            constraintSet.constrainWidth(mapItem.id, itemWidth)
            constraintSet.constrainHeight(mapItem.id, itemHeight)

            if (i == 0) {
                constraintSet.connect(mapItem.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                constraintSet.connect(mapItem.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }

            if (i in 1 until xCount) {
                constraintSet.connect(mapItem.id, ConstraintSet.END, cityViewList[i - 1].id, ConstraintSet.START)
                constraintSet.connect(mapItem.id, ConstraintSet.BOTTOM, cityViewList[i - 1].id, ConstraintSet.BOTTOM)
            }

            if (i in xCount until xCount + yCount - 1) {
                constraintSet.connect(mapItem.id, ConstraintSet.BOTTOM, cityViewList[i - 1].id, ConstraintSet.TOP)
                constraintSet.connect(mapItem.id, ConstraintSet.START, cityViewList[i - 1].id, ConstraintSet.START)
            }

            if (i in xCount + yCount - 1 until xCount + yCount + xCount - 1) {
                constraintSet.connect(mapItem.id, ConstraintSet.START, cityViewList[i - 1].id, ConstraintSet.END)
                constraintSet.connect(mapItem.id, ConstraintSet.TOP, cityViewList[i - 1].id, ConstraintSet.TOP)
            }

            if (i in xCount + yCount + xCount - 2 until xCount + yCount + xCount + yCount - 4) {
                constraintSet.connect(mapItem.id, ConstraintSet.TOP, cityViewList[i - 1].id, ConstraintSet.BOTTOM)
                constraintSet.connect(mapItem.id, ConstraintSet.START, cityViewList[i - 1].id, ConstraintSet.START)
            }

            addView(mapItem)
            cityViewList.add(mapItem)
        }

        constraintSet.applyTo(this)

    }




}