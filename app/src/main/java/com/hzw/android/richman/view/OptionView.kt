package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameOption
import com.hzw.android.richman.listener.OnAlterDialogListener
import kotlinx.android.synthetic.main.view_option.view.*

/**
 * class OptionView
 * @author CrazyDragon
 * description 操作视图
 * note
 * create date 2022/2/13
 */
class OptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_option, this)

        mBtnBuy.setOnClickListener(this)
        mBtnLevel.setOnClickListener(this)
        mBtnDefense.setOnClickListener(this)
        mBtnCost.setOnClickListener(this)
        mBtnPk.setOnClickListener(this)
        mBtnAttack.setOnClickListener(this)

        when (GameData.INSTANCE.currentMap()) {
            is CityBean -> {
                val cityBean = GameData.INSTANCE.currentMap() as CityBean
                if (cityBean.owner == null) {
                    buttonStatus(true, false, false, false, false, false)
                }else {
                    if (cityBean.owner?.id == GameData.INSTANCE.currentPlayer().id) {
                        buttonStatus(false, cityBean.level < 3 && GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_FALSE, true, false, false, false)
                    }else {
                        if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_TRUE) {
                            buttonStatus(false, false, false, false , false, false)
                        }else{
                            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.ATTACK
                            buttonStatus(false, false, false, true, true, true)
                        }

                    }
                }
            }

            is AreaBean -> {
                val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                mBtnLevel.visibility = GONE
            }

            is SpecialBean -> {
                val specialBean = GameData.INSTANCE.currentMap() as SpecialBean
            }
        }
    }

    private fun buttonStatus(buy: Boolean, level: Boolean, defense: Boolean, cost: Boolean, pk: Boolean, attack: Boolean) {
        mBtnBuy.visibility = if (buy) VISIBLE else GONE
        mBtnLevel.visibility = if (level) VISIBLE else GONE
        mBtnDefense.visibility = if (defense) VISIBLE else GONE
        mBtnCost.visibility = if (cost) VISIBLE else GONE
        mBtnPk.visibility = if (pk) VISIBLE else GONE
        mBtnAttack.visibility = if (attack) VISIBLE else GONE
    }

    private fun showNormalDialog(msg:String, onAlterDialogListener: OnAlterDialogListener) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            .setMessage(msg)
            .setPositiveButton("是") { _, _ ->
                onAlterDialogListener.onSure()

            }.setNegativeButton("算了", null)
        builder.create().show()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.mBtnBuy -> {
                when (GameData.INSTANCE.currentMap()) {
                    is CityBean -> {
                        val cityBean = GameData.INSTANCE.currentMap() as CityBean
                        showNormalDialog("是否购买 "+cityBean.name+" ?", object : OnAlterDialogListener{
                            override fun onSure() {
                                GameOption.buyCity(cityBean)
                            }

                        })
                    }

                    is AreaBean -> {
                        val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                        mBtnLevel.visibility = GONE
                    }
                }
            }
            R.id.mBtnLevel -> {
                if (GameData.INSTANCE.currentMap() is CityBean) {
                    val cityBean = GameData.INSTANCE.currentMap() as CityBean
                    showNormalDialog("是否升级 "+cityBean.name+" ?", object : OnAlterDialogListener{
                        override fun onSure() {
                            GameOption.levelCity(cityBean)
                        }

                    })
                }
            }
            R.id.mBtnCost -> {
                if (GameData.INSTANCE.currentMap() is CityBean) {
                    val cityBean = GameData.INSTANCE.currentMap() as CityBean
                    showNormalDialog("是否交费 "+cityBean.getCostMoney()+" ?", object : OnAlterDialogListener{
                        override fun onSure() {
                            GameOption.costCity(cityBean)
                        }

                    })
                }
            }
        }
    }

}