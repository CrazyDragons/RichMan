package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R

/**
 * class LogAdapter
 *
 * @author CrazyDragon
 * description 日志适配器
 * note
 * create date 2022/2/12
 */
class LogAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_log) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.mTvLog, item)
    }
}