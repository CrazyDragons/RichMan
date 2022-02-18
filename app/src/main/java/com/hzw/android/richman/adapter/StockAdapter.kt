package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.StockBean
import com.hzw.android.richman.game.GameData

/**
 * class StockAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/18
 */
class StockAdapter : BaseQuickAdapter<StockBean, BaseViewHolder>(R.layout.item_stock) {

    init {
        setNewInstance(GameData.INSTANCE.stocksData)
    }

    override fun convert(holder: BaseViewHolder, item: StockBean) {
        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvPrice, item.newPrice.toString())
            .setTextColorRes(R.id.mTvPrice, if (item.newPrice - item.oldPrice > 0) R.color.colorRed else R.color.colorGreen)
    }
}