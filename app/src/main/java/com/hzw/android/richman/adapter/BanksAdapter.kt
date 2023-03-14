package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.utils.MapUtil

/**
 * class BanksAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/17
 */
class BanksAdapter: BaseQuickAdapter<PlayerBean.BANK, BaseViewHolder>(R.layout.item_banks) {

    init {
        val list = mutableListOf<PlayerBean.BANK>()
        list.add(PlayerBean.BANK.ABC)
        list.add(PlayerBean.BANK.BOC)
        list.add(PlayerBean.BANK.BOCM)
        list.add(PlayerBean.BANK.CCB)
        list.add(PlayerBean.BANK.ICBC)
        setNewInstance(list)
    }

    override fun convert(holder: BaseViewHolder, item: PlayerBean.BANK) {
        holder.setText(R.id.mTvDesc, MapUtil.bankDesc(item))
        when(item) {
            PlayerBean.BANK.ABC -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_abc)
            }
            PlayerBean.BANK.BOC -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_boc)
            }
            PlayerBean.BANK.BOCM -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_bocm)
            }
            PlayerBean.BANK.CCB -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_ccb)
            }
            PlayerBean.BANK.ICBC -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_icbc)
            }
        }
    }
}