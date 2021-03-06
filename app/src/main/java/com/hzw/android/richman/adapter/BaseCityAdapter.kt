package com.hzw.android.richman.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.view.LevelView

/**
 * class CityAdapter
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class BaseCityAdapter : BaseQuickAdapter<BaseCityBean, BaseViewHolder>(R.layout.item_city) {

    override fun convert(holder: BaseViewHolder, item: BaseCityBean) {

        val addDefense = (if (item is CityBean) item.level * Value.ADD_DEFENSE else item.owner!!.allArea() * Value.ADD_DEFENSE) + if (MapUtil.judgeAllColor(item)) Value.ALL_COLOR_DEFENSE else 0
        val defense = if (item.generals == null) (0 + addDefense ) else (item.generals!!.defense + addDefense )
        holder.setText(R.id.mTvName, item.name)
            .setText(R.id.mTvCost,
                if (item is CityBean) item.needCostMoney().toString()
                else item.owner!!.allAreaCostMoney().toString()
            )
            .setText(
                R.id.mTvDefense, defense.toString() + (if (GameData.INSTANCE.currentPlayer().buff == PlayerBean.BUFF.ADD_DEFENSE) "(+5)" else "" )
            )
        holder.getView<LevelView>(R.id.mTvCityLevel)
            .setLevel(if (item is CityBean) item.level else item.owner!!.allArea(), false)


        holder.getView<ImageView>(R.id.mIvCover).setImageResource(if (item is CityBean) item.cover else R.drawable.bg_area)
    }
}