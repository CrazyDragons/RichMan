package com.hzw.android.richman.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.widget.EditText
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
class SaleDialog(context: Context, onRefreshListener: OnRefreshListener): BaseDialog(context) {

    private val saleAdapter = object : BaseQuickAdapter<SaleBean, BaseViewHolder>(R.layout.item_sale) {
        override fun convert(holder: BaseViewHolder, item: SaleBean) {
            holder.setText(R.id.mTvName, item.name).setText(R.id.mTvPrice, item.price.toString())
        }
    }



    init {
        setContentView(R.layout.dialog_sale)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)

        val playerBean = GameData.INSTANCE.currentPlayer()

        mSbArmy.max = playerBean.army
        mTvMaxArmy.text = playerBean.army.toString()

        mRvCity.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvGenerals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvEquipments.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvSale.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRvSalePlayer.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        saleAdapter.setNewInstance(mutableListOf())
        mRvSale.adapter = saleAdapter

        val salePlayerAdapter = object : BaseQuickAdapter<SalePlayerBean, BaseViewHolder>(R.layout.item_sale_player) {
            override fun convert(holder: BaseViewHolder, item: SalePlayerBean) {
                holder.setText(R.id.mTvName, item.playerBean.name).setGone(R.id.mTvComputerPrice, item.playerBean.isPlayer).setGone(R.id.mEtPrice, !item.playerBean.isPlayer)
                if (!item.playerBean.isPlayer) {
                    holder.setText(R.id.mTvComputerPrice, update().toString())
                    item.price = update()
                }else {
                    val price = holder.getView<EditText>(R.id.mEtPrice).text.toString()
                    item.price = if (TextUtils.isEmpty(price)) 0 else price.toInt()
                }
            }
        }

        val salePlayers = mutableListOf<SalePlayerBean>()
        for (item in GameData.INSTANCE.playerData) {
            if (item.id != GameData.INSTANCE.currentPlayer().id) {
                salePlayers.add(SalePlayerBean(item, 0))
            }
        }
        salePlayerAdapter.setNewInstance(salePlayers)
        mRvSalePlayer.adapter = salePlayerAdapter

        val baseCityAdapter = BaseCityAdapter()
        baseCityAdapter.setNewInstance(playerBean.city)
        mRvCity.adapter = baseCityAdapter
        baseCityAdapter.setOnItemClickListener { _, _, position ->
            if (judge(baseCityAdapter.data[position].name!!)) {
                saleAdapter.addData(SaleBean(baseCityAdapter.data[position].name!!, baseCityAdapter.data[position].buyPrice/2))
            }
            update()
        }

        val generalsAdapter = GeneralsAdapter()
        generalsAdapter.setNewInstance(playerBean.allGenerals())
        mRvGenerals.adapter = generalsAdapter
        generalsAdapter.setOnItemClickListener { _, _, position ->
            if (judge(generalsAdapter.data[position].name)) {
                saleAdapter.addData(SaleBean(generalsAdapter.data[position].name, Value.X_BUY_GENERALS/2))
            }
            update()
        }

        val equipmentsAdapter = EquipmentsAdapter()
        equipmentsAdapter.setNewInstance(playerBean.equipments)
        mRvEquipments.adapter = equipmentsAdapter
        equipmentsAdapter.setOnItemClickListener { _, _, position ->
            if (judge(equipmentsAdapter.data[position].name)) {
                saleAdapter.addData(SaleBean(equipmentsAdapter.data[position].name, equipmentsAdapter.data[position].price/2))
            }
            update()
        }

        mTvSureSale.setOnClickListener {
            var player = salePlayerAdapter.data[0]
            var max = 0
            for (item in salePlayerAdapter.data) {
                if (item.price > max) {
                    max = item.price
                    player = item
                }
            }
            ToastUtil.show("最终以"+player.price+"拍卖给"+player.playerBean.name)

            for (item in saleAdapter.data) {
                sale(player.playerBean, item.name)
            }

            player.playerBean.money -= player.price
            GameData.INSTANCE.currentPlayer().money += player.price

            dismiss()

            onRefreshListener.onRefreshData()
        }
    }

    private fun judge(name:String):Boolean {
        for (item in saleAdapter.data) {
            if (item.name == name) {
                saleAdapter.remove(item)
                return false
            }
        }
        return true
    }

    private fun update():Int {
        var sum = 0
        for (item in saleAdapter.data) {
            sum += item.price
        }
        mTvSalePrice.text = sum.toString()
        Handler(Looper.getMainLooper()).postDelayed({
            mRvSalePlayer.adapter?.notifyDataSetChanged()

        }, 200)
        return sum
    }


    class SaleBean(var name: String, var price: Int) {

    }

    class SalePlayerBean(var playerBean: PlayerBean, var price: Int) {

    }

    private fun sale(playerBean: PlayerBean, name:String) {
        for (item in saleAdapter.data) {
            val baseCityBean = searchBaseCity(name)
            val generalsBean = searchGenerals(name)
            val equipmentBean = searchEquipments(name)

            if (baseCityBean != null) {
                GameData.INSTANCE.currentPlayer().city.remove(baseCityBean)
                baseCityBean.owner = playerBean
                playerBean.city.add(baseCityBean)
            }

            if (generalsBean != null) {
                GameData.INSTANCE.currentPlayer().generals.remove(generalsBean)
                generalsBean.owner = playerBean
                playerBean.generals.add(generalsBean)
            }

            if (equipmentBean != null) {
                GameData.INSTANCE.currentPlayer().equipments.remove(equipmentBean)
                equipmentBean.owner = playerBean
                playerBean.equipments.add(equipmentBean)
            }
        }

    }

    private fun searchBaseCity(name: String):BaseCityBean? {
        val baseCityBean = null
        for (item in GameData.INSTANCE.currentPlayer().city) {
            if (item.name == name) {
                return item
            }
        }
        return baseCityBean
    }

    private fun searchGenerals(name: String):GeneralsBean? {
        val generalsBean = null
        for (item in GameData.INSTANCE.currentPlayer().generals) {
            if (item.name == name) {
                return item
            }
        }
        return generalsBean
    }

    private fun searchEquipments(name: String):EquipmentBean? {
        val equipmentBean = null
        for (item in GameData.INSTANCE.currentPlayer().equipments) {
            if (item.name == name) {
                return item
            }
        }
        return equipmentBean
    }
}