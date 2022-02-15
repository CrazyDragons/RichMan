package com.hzw.android.richman.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.view.LevelView

/**
 * class CityAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class BaseCityAdapter : BaseQuickAdapter<BaseCityBean, BaseViewHolder>(R.layout.item_city) {

    override fun convert(holder: BaseViewHolder, item: BaseCityBean) {

        val addDefense = if (item is CityBean) item.level * Value.ADD_DEFENSE else item.owner!!.allArea() * Value.ADD_DEFENSE
        val defense = if (item.generals == null) (0 + addDefense + if (MapUtil.judgeAllColor(item)) Value.ALL_COLOR_DEFENSE else 0)
        else (item.generals!!.defense + addDefense + if (MapUtil.judgeAllColor(item)) Value.ALL_COLOR_DEFENSE else 0)
        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvCost,
                if (item is CityBean) (item.needCostMoney() * if (MapUtil.judgeAllColor(item)) Value.X_ALL_COLOR_MONEY else 1).toString()
                else (item.owner!!.allAreaCostMoney() * if (MapUtil.judgeAllColor(item)) Value.X_ALL_COLOR_MONEY else 1).toString()
            )
            .setText(
                R.id.mTvDefense, defense.toString()
            )
        holder.getView<LevelView>(R.id.mTvCityLevel)
            .setLevel(if (item is CityBean) item.level else item.owner!!.allArea(), false)


        holder.getView<ImageView>(R.id.mIvCover).setImageResource(if (item is CityBean) item.cover else R.drawable.bg_area)
    }
}