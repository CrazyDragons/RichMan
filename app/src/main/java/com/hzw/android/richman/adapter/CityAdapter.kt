package com.hzw.android.richman.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean

/**
 * class CityAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class CityAdapter : BaseQuickAdapter<BaseCityBean, BaseViewHolder>(R.layout.item_city) {

    override fun convert(holder: BaseViewHolder, item: BaseCityBean) {

        if (item is CityBean) {
            holder.setText(R.id.mTvName, item.name)
                .setText(R.id.mTvLevel, item.level.toString())
                .setText(R.id.mTvCost, item.getCostMoney().toString())
                .setText(
                    R.id.mTvDefense,
                    if (item.general == null) "0" else item.general?.defense.toString()
                )
        }


        holder.getView<ImageView>(R.id.mIvCover).setImageResource(item.cover)
    }
}