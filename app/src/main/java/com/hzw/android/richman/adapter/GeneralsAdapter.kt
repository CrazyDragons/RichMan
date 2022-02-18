package com.hzw.android.richman.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.GeneralsBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.game.GameData

/**
 * class GeneralsAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class GeneralsAdapter : BaseQuickAdapter<GeneralsBean, BaseViewHolder>(R.layout.item_generals) {

    override fun convert(holder: BaseViewHolder, item: GeneralsBean) {
        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvLife, item.action.toString()+"("+item.life+")")
            .setText(R.id.mTvAttack, item.attack.toString() + if (GameData.INSTANCE.currentPlayer().buff == PlayerBean.BUFF.ADD_ATTACK) "(+5)" else "")
            .setText(R.id.mTvDefense, item.defense.toString()).setVisible(R.id.mIvDefense, item.city != null)

        holder.getView<ImageView>(R.id.mIvCover).setImageResource(item.cover)
    }
}