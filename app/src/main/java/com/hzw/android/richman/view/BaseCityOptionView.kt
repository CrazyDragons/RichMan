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
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.dialog.OptionGeneralsDialog
import com.hzw.android.richman.dialog.TipsDialog
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameLog
import com.hzw.android.richman.game.GameOption
import com.hzw.android.richman.listener.OnClickTipsListener
import kotlinx.android.synthetic.main.view_base_city_option.view.*

/**
 * class OptionView
 * @author CrazyDragon
 * description 操作视图
 * note
 * create date 2022/2/13
 */
class BaseCityOptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_base_city_option, this)

        mBtnBuy.setOnClickListener(this)
        mBtnLevel.setOnClickListener(this)
        mBtnDefense.setOnClickListener(this)
        mBtnCost.setOnClickListener(this)
        mBtnPk.setOnClickListener(this)
        mBtnAttack.setOnClickListener(this)

        when (GameData.INSTANCE.currentMap()) {
            is BaseCityBean -> {

                val playerBean = GameData.INSTANCE.currentPlayer()
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                if (baseCityBean.owner == null) {
                    var canBuy = true
                    if (baseCityBean is CityBean && playerBean.money < baseCityBean.buyPrice) {
                        canBuy = false
                    }
                    if (baseCityBean is AreaBean && playerBean.army < Value.AREA_ARMY) {
                        canBuy = false
                    }
                    buttonStatus(
                        canBuy,
                        level = false,
                        defense = false,
                        cost = false,
                        pk = false,
                        attack = false
                    )
                } else {
                    if (baseCityBean.owner?.id == GameData.INSTANCE.currentPlayer().id) {
                        val canLevel =
                            (GameData.INSTANCE.currentMap() is CityBean)
                                    && (GameData.INSTANCE.currentMap() as CityBean).level < 3
                                    && playerBean.status == PlayerBean.STATUS.OPTION_FALSE && playerBean.money >= (GameData.INSTANCE.currentMap() as CityBean).buyPrice * Value.X_LEVEL_CITY_COST
                        buttonStatus(
                            false,
                            canLevel,
                            defense = true,
                            cost = false,
                            pk = false,
                            attack = false
                        )
                    } else {
                        if (GameData.INSTANCE.currentPlayer().status == PlayerBean.STATUS.OPTION_TRUE) {
                            buttonStatus(
                                buy = false,
                                level = false,
                                defense = false,
                                cost = false,
                                pk = false,
                                attack = false
                            )
                        } else {
                            GameData.INSTANCE.currentPlayer().status = PlayerBean.STATUS.ATTACK

                            var canCost = true
                            var canAttack = true

                            if (baseCityBean is CityBean) {
                                canCost = playerBean.money >= baseCityBean.needCostMoney()
                                canAttack = playerBean.army >= baseCityBean.needCostArmy() && playerBean.allAttackGenerals().size > 0
                            }

                            if (baseCityBean is AreaBean) {
                                canCost =
                                    playerBean.money >= baseCityBean.owner!!.allAreaCostMoney()
                                canAttack =
                                    playerBean.army >= baseCityBean.owner!!.allAreaCostArmy() && playerBean.allAttackGenerals().size > 0
                            }

                            val canPK: Boolean = playerBean.generals.size > 0

                            buttonStatus(
                                buy = false,
                                level = false,
                                defense = false,
                                cost = true,
                                pk = canPK,
                                attack = canAttack
                            )

                            if (!canCost && !canPK && !canAttack) {
                                TipsDialog(context, "注意，你已经不能支付过路费，现在银行先垫付，如有股票请抛出，否则下次轮到你时需要进行强制拍卖").show()
                                if (!GameData.INSTANCE.currentPlayer().isPlayer) {
                                    GameData.INSTANCE.currentPlayer().money += GameData.INSTANCE.currentPlayer().stockMoney()
                                    GameData.INSTANCE.currentPlayer().stocks.clear()
                                    GameLog.INSTANCE.addSystemLog("抛售了股票")
                                }
                            }
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

    private fun showNormalDialog(msg: String, onClickTipsListener: OnClickTipsListener) {

        TipsDialog(context, msg, "是", "算了", onClickTipsListener).show()

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mBtnBuy -> {
                when (GameData.INSTANCE.currentMap()) {
                    is CityBean -> {
                        val cityBean = GameData.INSTANCE.currentMap() as CityBean
                        showNormalDialog(
                            "是否用 " + cityBean.buyPrice + " 购买 " + cityBean.name + " ?",
                            object : OnClickTipsListener {
                                override fun onClickYes() {
                                    GameOption.buyBaseCity(cityBean)
                                }

                            })
                    }

                    is AreaBean -> {
                        val areaBean = GameData.INSTANCE.currentMap() as AreaBean
                        showNormalDialog(
                            "是否用 " + areaBean.army + " 兵力攻占 " + areaBean.name + " ?",
                            object : OnClickTipsListener {
                                override fun onClickYes() {
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
                        "是否用 " + (cityBean.buyPrice * Value.X_LEVEL_CITY_COST).toInt() + " 升级 " + cityBean.name + " ?",
                        object : OnClickTipsListener {
                            override fun onClickYes() {
                                GameOption.levelCity(cityBean, false)
                            }

                        })
                }
            }

            R.id.mBtnDefense -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                OptionGeneralsDialog(
                    context,
                    OptionGeneralsDialog.TYPE.DEFENSE,
                    baseCityBean
                ).show()
            }

            R.id.mBtnPk -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                showNormalDialog(
                    "是否在 "+baseCityBean.name+" 派武将单挑?",
                    object : OnClickTipsListener {
                        override fun onClickYes() {
                            OptionGeneralsDialog(
                                context,
                                OptionGeneralsDialog.TYPE.PK,
                                baseCityBean
                            ).show()
                        }

                    })
            }

            R.id.mBtnAttack -> {
                val baseCityBean = GameData.INSTANCE.currentMap() as BaseCityBean
                var msg = ""
                if (baseCityBean is CityBean) {
                    msg =
                        "是否用 " + (baseCityBean.needCostArmy()) + " 兵力并派武将攻打 " + baseCityBean.name + " ?"
                }
                if (baseCityBean is AreaBean) {
                    msg =
                        "是否用 " + (baseCityBean.owner!!.allAreaCostArmy()) + " 兵力并派武将攻打 " + baseCityBean.name + " ?"
                }
                showNormalDialog(
                    msg,
                    object : OnClickTipsListener {
                        override fun onClickYes() {
                            OptionGeneralsDialog(
                                context,
                                OptionGeneralsDialog.TYPE.ATTACK,
                                baseCityBean
                            ).show()
                        }

                    })
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
                        msg = "是否交费 " + areaBean.owner!!.allAreaCostMoney() + " ?"
                    }
                }

                showNormalDialog(
                    msg,
                    object : OnClickTipsListener {
                        override fun onClickYes() {
                            GameOption.costBaseCity(GameData.INSTANCE.currentMap() as BaseCityBean, false)
                        }

                    })
            }
        }
    }

}