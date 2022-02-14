package com.hzw.android.richman.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.GeneralBean

/**
 * class GeneralsAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class GeneralsAdapter : BaseQuickAdapter<GeneralBean, BaseViewHolder>(R.layout.item_generals) {

    override fun convert(holder: BaseViewHolder, item: GeneralBean) {
        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvLife, item.action.toString()+"("+item.life+")")
            .setText(R.id.mTvAttack, item.attack.toString())
            .setText(R.id.mTvDefense, item.defense.toString()).setVisible(R.id.mIvDefense, item.city != null)

        holder.getView<ImageView>(R.id.mIvCover).setImageResource(item.cover)
    }
}