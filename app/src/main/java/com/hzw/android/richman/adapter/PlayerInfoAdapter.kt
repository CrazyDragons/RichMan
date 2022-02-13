package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.PlayerBean

/**
 * class PlayerInfoAdapter
 *
 * @author CrazyDragon
 * description 玩家信息适配器
 * note
 * create date 2022/2/12
 */
class PlayerInfoAdapter : BaseQuickAdapter<PlayerBean, BaseViewHolder>(R.layout.item_player_info) {

    override fun convert(holder: BaseViewHolder, item: PlayerBean) {
        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvGDP, item.GDP())
            .setText(R.id.mTvMoney, item.money.toString())
            .setText(R.id.mTvArmy, item.army.toString())
            .setText(R.id.mTvCity, item.city.size.toString())
            .setText(R.id.mTvGeneral, item.generals.size.toString())
            .setText(R.id.mTvEquipments, item.equipments.size.toString())
    }
}