package com.hzw.android.richman.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.bean.AreaBean
import com.hzw.android.richman.bean.CityBean
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.dialog.OptionGeneralsDialog
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

            is BaseCityBean -> {

                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                if (baseCityBean.owner == null) {
                    buttonStatus(true, false, false, false, false, false)
                } else {
                    if (baseCityBean.owner?.id == GameData.INSTANCE.currentPlayer().id) {
                        val level =
                            (GameData.INSTANCE.currentMap() is CityBean) && (GameData.INSTANCE.currentMap() as CityBean).level < 3 && GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_FALSE
                        buttonStatus(
                            false,
                            level,
                            true,
                            false,
                            false,
                            false
                        )
                    } else {
                        if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_TRUE) {
                            buttonStatus(false, false, false, false, false, false)
                        } else {
                            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.ATTACK
                            buttonStatus(false, false, false, true, true, true)
                        }

                    }
                }
            }


            is SpecialBean -> {
                val specialBean = GameData.INSTANCE.currentMap() as SpecialBean
            }
        }
    }

    private fun buttonStatus(
        buy: Boolean,
        level: Boolean,
        defense: Boolean,
        cost: Boolean,
        pk: Boolean,
        attack: Boolean
    ) {
        mBtnBuy.visibility = if (buy) VISIBLE else GONE
        mBtnLevel.visibility = if (level) VISIBLE else GONE
        mBtnDefense.visibility = if (defense) VISIBLE else GONE
        mBtnCost.visibility = if (cost) VISIBLE else GONE
        mBtnPk.visibility = if (pk) VISIBLE else GONE
        mBtnAttack.visibility = if (attack) VISIBLE else GONE
    }

    private fun showNormalDialog(msg: String, onAlterDialogListener: OnAlterDialogListener) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            .setMessage(msg)
            .setPositiveButton("是") { _, _ ->
                onAlterDialogListener.onSure()

            }.setNegativeButton("算了", null)
        builder.create().show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mBtnBuy -> {
                when (GameData.INSTANCE.currentMap()) {
                    is CityBean -> {
                        val cityBean = GameData.INSTANCE.currentMap() as CityBean
                        showNormalDialog(
                            "是否用金钱" + cityBean.buyPrice + "购买 " + cityBean.name + " ?",
                            object : OnAlterDialogListener {
                                override fun onSure() {
                                    GameOption.buyBaseCity(cityBean)
                                }

                            })
                    }

                    is AreaBean -> {
                        val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                        showNormalDialog(
                            "是否用" + areaBean.army + "兵力攻占 " + areaBean.name + " ?",
                            object : OnAlterDialogListener {
                                override fun onSure() {
                                    GameOption.buyBaseCity(areaBean)
                                }

                            })
                    }
                }
            }

            R.id.mBtnLevel -> {
                if (GameData.INSTANCE.currentMap() is CityBean) {
                    val cityBean = GameData.INSTANCE.currentMap() as CityBean
                    showNormalDialog(
                        "是否升级 " + cityBean.name + " ?",
                        object : OnAlterDialogListener {
                            override fun onSure() {
                                GameOption.levelCity(cityBean)
                            }

                        })
                }
            }

            R.id.mBtnDefense -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                OptionGeneralsDialog(context, OptionGeneralsDialog.TYPE.DEFENSE, baseCityBean).show()
            }

            R.id.mBtnPk -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                OptionGeneralsDialog(context, OptionGeneralsDialog.TYPE.PK, baseCityBean).show()
            }

            R.id.mBtnAttack -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                OptionGeneralsDialog(context, OptionGeneralsDialog.TYPE.ATTACK, baseCityBean).show()
            }

            R.id.mBtnCost -> {

                var msg = ""
                when (GameData.INSTANCE.currentMap()) {
                    is CityBean -> {
                        val cityBean = GameData.INSTANCE.currentMap() as CityBean
                        msg = "是否交费 " + cityBean.needCostMoney() + " ?"
                    }

                    is AreaBean -> {
                        val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                        msg = "是否交费 " + areaBean.owner?.allAreaCostMoney() + " ?"
                    }
                }

                showNormalDialog(
                    msg,
                    object : OnAlterDialogListener {
                        override fun onSure() {
                            GameOption.costBaseCity(GameData.INSTANCE.currentMap() as BaseCityBean)
                        }

                    })
            }
        }
    }

}