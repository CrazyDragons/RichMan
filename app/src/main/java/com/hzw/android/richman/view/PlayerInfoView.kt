package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.BaseCityAdapter
import com.hzw.android.richman.adapter.EquipmentsAdapter
import com.hzw.android.richman.adapter.GeneralsAdapter
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.utils.ScreenUtil
import kotlinx.android.synthetic.main.view_player_info.view.*

/**
 * class PlayerInfoView
 * @author CrazyDragon
 * description 角色详情
 * note
 * create date 2022/2/13
 */
class PlayerInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {


    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_player_info, this)
        mRvCity.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvGenerals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvEquipments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        radius = ScreenUtil.dp2px(context, 10).toFloat()
    }

    fun setData(playerBean: PlayerBean) {
        mTvName.text = playerBean.name
        mTvGDP.text = playerBean.GDP()
        mTvStock.text = playerBean.stockMoney().toString()
        mTvMoney.text = playerBean.money.toString()
        mTvArmy.text = playerBean.army.toString()
        mTvCity.text = playerBean.city.size.toString()
        mTvGenerals.text = playerBean.allGenerals().size.toString()
        mTvEquipments.text = playerBean.equipments.size.toString()

        val baseCityAdapter = BaseCityAdapter()
        baseCityAdapter.setNewInstance(playerBean.city)
        mRvCity.adapter = baseCityAdapter

        val generalsAdapter = GeneralsAdapter()
        generalsAdapter.setNewInstance(playerBean.allGenerals())
        mRvGenerals.adapter = generalsAdapter

        val equipmentsAdapter = EquipmentsAdapter()
        equipmentsAdapter.setNewInstance(playerBean.equipments)
        mRvEquipments.adapter = equipmentsAdapter

    }

}