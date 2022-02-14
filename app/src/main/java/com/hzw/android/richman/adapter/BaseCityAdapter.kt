package com.hzw.android.richman.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
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

        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvCost,
                if (item is CityBean) item.needCostMoney().toString() else item.owner?.allAreaCostMoney()
                    .toString()
            )
            .setText(
                R.id.mTvDefense,
                if (item.generals == null) "0" else item.generals?.defense.toString()
            )
        holder.getView<LevelView>(R.id.mTvCityLevel)
            .setLevel(if (item is CityBean) item.level else item.owner!!.allArea(), false)


        holder.getView<ImageView>(R.id.mIvCover).setImageResource(if (item is CityBean) item.cover else R.drawable.bg_area)
    }
}