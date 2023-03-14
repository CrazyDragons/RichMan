package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.PlayerBean
import java.util.*

/**
 * class AddPlayerAdapter
 *
 * @author CrazyDragon
 * description 添加玩家适配器
 * note
 * create date 2022/2/10
 */
class AddPlayerAdapter : BaseQuickAdapter<PlayerBean, BaseViewHolder>(R.layout.item_add_player) {

    override fun convert(holder: BaseViewHolder, item: PlayerBean) {
        holder.setText(R.id.mTvName, item.name)
    }

    init {
        addChildClickViewIds(R.id.mTvDelete)
        setNewInstance(ArrayList())
    }

}