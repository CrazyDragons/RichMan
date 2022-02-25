package com.hzw.android.richman.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.BaseCityAdapter
import com.hzw.android.richman.adapter.EquipmentsAdapter
import com.hzw.android.richman.adapter.GeneralsAdapter
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.bean.EquipmentBean
import com.hzw.android.richman.bean.GeneralsBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.listener.OnRefreshListener
import com.hzw.android.richman.utils.ToastUtil
import kotlinx.android.synthetic.main.dialog_sale.*


/**
 * class SaleView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/19
 */
@SuppressLint("NotifyDataSetChanged")
class SaleDialog(context: Context, onRefreshListener: OnRefreshListener) : BaseDialog(context) {

    private val saleAdapter =
        object : BaseQuickAdapter<SaleBean, BaseViewHolder>(R.layout.item_sale) {
            override fun convert(holder: BaseViewHolder, item: SaleBean) {
                holder.setText(R.id.mTvName, item.name)
                    .setText(R.id.mTvPrice, item.price.toString())
            }
        }

    var army = 0

    init {
        setContentView(R.layout.dialog_sale)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)

        val playerBean = GameData.INSTANCE.currentPlayer()

        mSbArmy.max = playerBean.army
        mTvMaxArmy.text = army.toString() + "(" + playerBean.army + ")"

        mRvCity.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvGenerals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvEquipments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvSale.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        saleAdapter.setNewInstance(mutableListOf())
        mRvSale.adapter = saleAdapter

        val salePlayers = mutableListOf<SalePlayerBean>()
        for (item in GameData.INSTANCE.playerData) {
            if (item.id != GameData.INSTANCE.currentPlayer().id) {
                salePlayers.add(SalePlayerBean(item, 0))
            }
        }
        salePlayers.add(SalePlayerBean(null, 0))
        mLlPlayer.setPlayers(salePlayers)


        mSbArmy.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                army = progress
                mTvMaxArmy.text = army.toString() + "(" + playerBean.army + ")"
                update()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        val baseCityAdapter = BaseCityAdapter()
        baseCityAdapter.setNewInstance(playerBean.city)
        mRvCity.adapter = baseCityAdapter
        baseCityAdapter.setOnItemClickListener { _, _, position ->
            if (judge(baseCityAdapter.data[position].name!!)) {
                saleAdapter.addData(
                    SaleBean(
                        baseCityAdapter.data[position].name!!,
                        baseCityAdapter.data[position].buyPrice / 2
                    )
                )
            }
            update()
        }

        val generalsAdapter = GeneralsAdapter()
        generalsAdapter.setNewInstance(playerBean.allGenerals())
        mRvGenerals.adapter = generalsAdapter
        generalsAdapter.setOnItemClickListener { _, _, position ->
            if (judge(generalsAdapter.data[position].name)) {
                saleAdapter.addData(
                    SaleBean(
                        generalsAdapter.data[position].name,
                        Value.X_BUY_GENERALS / 2
                    )
                )
            }
            update()
        }

        val equipmentsAdapter = EquipmentsAdapter()
        equipmentsAdapter.setNewInstance(playerBean.equipments)
        mRvEquipments.adapter = equipmentsAdapter
        equipmentsAdapter.setOnItemClickListener { _, _, position ->
            if (judge(equipmentsAdapter.data[position].name)) {
                saleAdapter.addData(
                    SaleBean(
                        equipmentsAdapter.data[position].name,
                        equipmentsAdapter.data[position].price / 2
                    )
                )
            }
            update()
        }

        mTvSureSale.setOnClickListener {
            var player = mLlPlayer.playersData[0]
            var max = 0
            for (item in mLlPlayer.playersData) {
                if (item.price >= max) {
                    max = item.price
                    player = item
                }
            }
            ToastUtil.show("最终以" + player.price + "拍卖给" + if (player.playerBean != null) player.playerBean!!.name else "银行")

            for (item in saleAdapter.data) {
                sale(player.playerBean, item.name)
            }
            if(player.playerBean != null) {
                player.playerBean!!.army += army
                player.playerBean!!.money -= player.price
            }
            GameData.INSTANCE.currentPlayer().army -= army
            GameData.INSTANCE.currentPlayer().money += player.price

            dismiss()

            onRefreshListener.onRefreshData(!GameData.INSTANCE.currentPlayer().isPlayer)
        }

        if (!GameData.INSTANCE.currentPlayer().isPlayer) {

            mSbArmy.isEnabled = false
            baseCityAdapter.setOnItemClickListener(null)
            generalsAdapter.setOnItemClickListener(null)
            equipmentsAdapter.setOnItemClickListener(null)

            mSbArmy.progress = GameData.INSTANCE.currentPlayer().army
            for (item in playerBean.city) {
                saleAdapter.addData(SaleBean(item.name!!, item.buyPrice / 2))
            }
            for (item in playerBean.generals) {
                saleAdapter.addData(SaleBean(item.name, Value.X_BUY_GENERALS / 2))
            }
            for (item in playerBean.equipments) {
                saleAdapter.addData(SaleBean(item.name, item.price / 2))
            }
            update()
        }

    }

    private fun judge(name: String): Boolean {
        for (item in saleAdapter.data) {
            if (item.name == name) {
                saleAdapter.remove(item)
                return false
            }
        }
        return true
    }

    private fun update() {
        var sum = 0
        for (item in saleAdapter.data) {
            sum += item.price
        }
        sum += army
        mTvSalePrice.text = sum.toString()
        mLlPlayer.update(sum)
    }


    class SaleBean(var name: String, var price: Int) {

    }

    class SalePlayerBean(var playerBean: PlayerBean?, var price: Int = 0) {

    }

    private fun sale(playerBean: PlayerBean?, name: String) {
        for (item in saleAdapter.data) {
            val baseCityBean = searchBaseCity(name)
            val generalsBean = searchGenerals(name)
            val equipmentBean = searchEquipments(name)

            if (baseCityBean != null) {
                GameData.INSTANCE.currentPlayer().city.remove(baseCityBean)
                if (baseCityBean.generals != null) {
                    GameData.INSTANCE.currentPlayer().generals.add(baseCityBean.generals!!)
                    baseCityBean.generals!!.cityID = 0
                }
                baseCityBean.generals = null
                if (playerBean != null) {
                    baseCityBean.ownerID = playerBean.id
                    playerBean.city.add(baseCityBean)
                }else {
                    baseCityBean.ownerID = 0
                }
            }

            if (generalsBean != null) {
                if (generalsBean.city() != null) {
                    generalsBean.city()!!.generals = null
                }else {
                    GameData.INSTANCE.currentPlayer().generals.remove(generalsBean)
                }
                generalsBean.cityID = 0
                if (playerBean != null) {
                    generalsBean.ownerID = playerBean.id
                    playerBean.generals.add(generalsBean)
                }else {
                    generalsBean.action = generalsBean.life
                    GameData.INSTANCE.generalsData.add(generalsBean)
                }

            }

            if (equipmentBean != null) {
                GameData.INSTANCE.currentPlayer().equipments.remove(equipmentBean)
                if(playerBean != null) {
                    equipmentBean.ownerID = playerBean.id
                    playerBean.equipments.add(equipmentBean)
                }else {
                    equipmentBean.ownerID = 0
                    GameData.INSTANCE.equipmentData.add(equipmentBean)
                }

            }
        }

    }

    private fun searchBaseCity(name: String): BaseCityBean? {
        val baseCityBean = null
        for (item in GameData.INSTANCE.currentPlayer().city) {
            if (item.name == name) {
                return item
            }
        }
        return baseCityBean
    }

    private fun searchGenerals(name: String): GeneralsBean? {
        val generalsBean = null
        for (item in GameData.INSTANCE.currentPlayer().allGenerals()) {
            if (item.name == name) {
                return item
            }
        }
        return generalsBean
    }

    private fun searchEquipments(name: String): EquipmentBean? {
        val equipmentBean = null
        for (item in GameData.INSTANCE.currentPlayer().equipments) {
            if (item.name == name) {
                return item
            }
        }
        return equipmentBean
    }
}