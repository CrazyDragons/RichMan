package com.hzw.android.richman.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.game.GameData.Companion.INSTANCE
import java.math.BigDecimal
import kotlin.math.pow

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
            .setText(R.id.mTvGDP, getGDP(item).toString() + "%")
            .setText(R.id.mTvMoney, item.money.toString())
            .setText(R.id.mTvArmy, item.army.toString())
            .setText(R.id.mTvCity, item.city.size.toString())
            .setText(R.id.mTvGeneral, item.generals.size.toString())
            .setText(R.id.mTvEquipments, item.equipments.size.toString())
    }

    private fun getGDP(playerBean: PlayerBean): Int {
        val x: Double
        var sum = 0.0
        for (i in INSTANCE.playerData.indices) {
            sum += getSingleGDP(INSTANCE.playerData[i])
        }
        x = getSingleGDP(playerBean) / sum
        return (getDouble2(x) * 100).toInt()
    }

    private fun getSingleGDP(playerBean: PlayerBean): Double {
        return playerBean.money + playerBean.army * 2 +
                getCityMoney(playerBean.city) + playerBean.generals.size * 2000 + playerBean.equipments.size * 2000
    }

    private fun getCityMoney(arrayList: MutableList<CityBean>): Double {
        var money = 0.0
        for (i in arrayList.indices) {
            money += arrayList[i].buyPrice * 2.0.pow(arrayList[i].level.toDouble())
        }
        return money
    }

    private fun getDouble2(value: Double): Double {
        return BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
    }
}