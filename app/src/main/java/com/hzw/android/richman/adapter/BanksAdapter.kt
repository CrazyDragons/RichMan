package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.PlayerBean

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
        when(item) {
            PlayerBean.BANK.ABC -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_abc)
                holder.setText(R.id.mTvDesc, "经过起点：得2000\n12点奖励：得1000\n1点惩罚：亏1000")
            }
            PlayerBean.BANK.BOC -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_boc)
                holder.setText(R.id.mTvDesc, "经过起点：得4000（50%）\n12点奖励：得2000（50%）\n1点惩罚：亏2000（50%）")
            }
            PlayerBean.BANK.BOCM -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_bocm)
                holder.setText(R.id.mTvDesc, "经过起点：得本金20%\n12点奖励：得本金5%\n1点惩罚：亏本金15%")
            }
            PlayerBean.BANK.CCB -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_ccb)
                holder.setText(R.id.mTvDesc, "经过起点：得距离起点步数*500\n12点奖励：得500\n1点惩罚：亏1500")
            }
            PlayerBean.BANK.ICBC -> {
                holder.setBackgroundResource(R.id.mIvBank, R.drawable.icon_bank_icbc)
                holder.setText(R.id.mTvDesc, "经过起点：得股票数*100\n12点奖励：得股票数*20\n1点惩罚：亏股票数*20")
            }
        }
    }
}