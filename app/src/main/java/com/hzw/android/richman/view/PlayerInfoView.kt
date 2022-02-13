package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.CityAdapter
import com.hzw.android.richman.adapter.EquipmentsAdapter
import com.hzw.android.richman.adapter.GeneralsAdapter
import com.hzw.android.richman.bean.PlayerBean
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
) : FrameLayout(context, attrs, defStyleAttr) {


    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_player_info, this)
        mRvCity.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvGenerals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvEquiments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    fun setData(playerBean: PlayerBean) {
        mTvName.text = playerBean.name
        mTvGDP.text = playerBean.GDP()
        mTvMoney.text = playerBean.money.toString()
        mTvArmy.text = playerBean.army.toString()
        mTvCity.text = playerBean.city.size.toString()
        mTvGeneral.text = playerBean.generals.size.toString()
        mTvEquipments.text = playerBean.equipments.size.toString()

        val cityAdapter = CityAdapter()
        cityAdapter.setNewInstance(playerBean.city)
        mRvCity.adapter = cityAdapter

        val generalsAdapter = GeneralsAdapter()
        generalsAdapter.setNewInstance(playerBean.generals)
        mRvGenerals.adapter = generalsAdapter

        val equipmentsAdapter = EquipmentsAdapter()
        equipmentsAdapter.setNewInstance(playerBean.equipments)
        mRvEquiments.adapter = equipmentsAdapter

    }

}