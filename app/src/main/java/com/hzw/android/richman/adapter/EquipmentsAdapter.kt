package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.EquipmentBean

/**
 * class EquipmentsAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class EquipmentsAdapter : BaseQuickAdapter<EquipmentBean, BaseViewHolder>(R.layout.item_equipments) {

    override fun convert(holder: BaseViewHolder, item: EquipmentBean) {

        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvDesc, item.desc)

    }
}