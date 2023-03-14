package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.EquipmentBean
import com.hzw.android.richman.dialog.TipsDialog
import com.hzw.android.richman.game.GameData

/**
 * class BuyEquipmentsAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/16
 */
class BuyEquipmentsAdapter : BaseQuickAdapter<EquipmentBean, BaseViewHolder>(R.layout.item_buy_equipments) {


    init {
        setNewInstance(GameData.INSTANCE.equipmentData)
    }

    override fun convert(holder: BaseViewHolder, item: EquipmentBean) {

        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvPrice, item.price.toString())

        holder.itemView.setOnLongClickListener {
            TipsDialog(context, item.desc).show()
            return@setOnLongClickListener true
        }

    }
}