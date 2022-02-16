package com.hzw.android.richman.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.BuyEquipmentsAdapter
import com.hzw.android.richman.base.BaseMapBean
import com.hzw.android.richman.bean.SpecialBean
import com.hzw.android.richman.config.Value
import com.hzw.android.richman.dialog.TipsDialog
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameOption
import com.hzw.android.richman.listener.OnClickTipsListener
import com.hzw.android.richman.listener.OnCountListener
import com.hzw.android.richman.listener.OnSpecialOptionListener
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.ScreenUtil
import com.hzw.android.richman.utils.ToastUtil
import kotlinx.android.synthetic.main.view_special_info.view.*

/**
 * class SpecialInfoView
 * @author CrazyDragon
 * description 特殊场景面板
 * note
 * create date 2022/2/12
 */
class SpecialInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private lateinit var specialBean: SpecialBean
    lateinit var onSpecialOptionListener: OnSpecialOptionListener
    var army = 0
    var conut = 0
    private var oldPosition = -1

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        inflate(context, R.layout.view_special_info, this)
        radius = ScreenUtil.dp2px(context, 10).toFloat()
        mLlInfo.setOnClickListener(null)

        GameData.INSTANCE.mapData.size

        mSbArmy.max = Value.MAX_BUY_ARMY / Value.X_BUY_ARMY
        mTvMaxArmy.text = Value.MAX_BUY_ARMY.toString()
        mSbArmy.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                army = Value.X_BUY_ARMY * progress
                mTvArmy.text = army.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        mRvEquipments.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
        val buyEquipmentsAdapter = BuyEquipmentsAdapter()
        buyEquipmentsAdapter.setOnItemClickListener { adapter, _, position ->

            if (oldPosition == position) {
                return@setOnItemClickListener
            } else {
                if (oldPosition != -1) {
                    adapter.getViewByPosition(oldPosition, R.id.mLlEquipment)
                        ?.setBackgroundResource(R.drawable.shape_rectangle_solid_yellow_r5)
                }
                adapter.getViewByPosition(position, R.id.mLlEquipment)
                    ?.setBackgroundResource(R.drawable.shape_rectangle_solid_green_r5)
            }
            oldPosition = position
        }
        mRvEquipments.adapter = buyEquipmentsAdapter


        mTvSure.setOnClickListener {
            when (specialBean.type) {
                BaseMapBean.MapType.ARMY -> {
                    if (GameData.INSTANCE.currentPlayer().money < army * 2) {
                        ToastUtil.show("穷逼，钱不够")
                        return@setOnClickListener
                    }
                    GameOption.buyArmy(army)
                }

                BaseMapBean.MapType.GENERALS -> {
                    if (GameData.INSTANCE.currentPlayer().money < Value.X_BUY_GENERALS) {
                        ToastUtil.show("穷逼，钱不够")
                        return@setOnClickListener
                    }
                    GameOption.buyGenerals()
                }

                BaseMapBean.MapType.BIG_MONEY -> {
                    MapUtil.count(mTvCount, object : OnCountListener {
                        override fun onCount(finalCount: Int) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                TipsDialog(
                                    context, "获得" + finalCount * 1000,
                                    object : OnClickTipsListener {
                                        override fun onClickYes() {
                                            GameOption.bigMoney(finalCount)

                                        }
                                    }).show()
                            }, 500)

                        }
                    })
                }

                BaseMapBean.MapType.FREE_GENERALS -> {
                    MapUtil.count(mTvCount, object : OnCountListener {
                        override fun onCount(finalCount: Int) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                TipsDialog(
                                    context,
                                    "获得" + GameOption.judgeFreeGenerals(finalCount) + "名武将",
                                    object : OnClickTipsListener {
                                        override fun onClickYes() {
                                            GameOption.freeGenerals(
                                                GameOption.judgeFreeGenerals(
                                                    finalCount
                                                )
                                            )
                                        }
                                    }).show()
                            }, 500)

                        }
                    })
                }

                BaseMapBean.MapType.CHANCE -> {
                    MapUtil.count(mTvCount, object : OnCountListener {
                        override fun onCount(finalCount: Int) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                TipsDialog(
                                    context,
                                    "机会描述",
                                    object : OnClickTipsListener {
                                        override fun onClickYes() {
                                            GameOption.chance(0)
                                        }
                                    }).show()
                            }, 500)

                        }
                    })
                }

                BaseMapBean.MapType.BANK -> {
                    MapUtil.count(mTvCount, object : OnCountListener {
                        override fun onCount(finalCount: Int) {
                            val msg = MapUtil.bankMsg(finalCount)
                            Handler(Looper.getMainLooper()).postDelayed({
                                TipsDialog(context, msg, object : OnClickTipsListener{
                                    override fun onClickYes() {
                                        GameOption.bank(finalCount)
                                    }

                                }).show()
                            }, 500)
                        }
                    })
                }

                BaseMapBean.MapType.SHOP -> {
                    if (oldPosition == -1) {
                        ToastUtil.show("未选择道具")
                        return@setOnClickListener
                    }
                    GameOption.shop(buyEquipmentsAdapter.data[oldPosition])
                }

                BaseMapBean.MapType.PRISON -> {
                    MapUtil.count(mTvCount, object : OnCountListener {
                        override fun onCount(finalCount: Int) {
                            val msg = MapUtil.prisonMsg(finalCount)
                            Handler(Looper.getMainLooper()).postDelayed({
                                TipsDialog(context, msg, object : OnClickTipsListener{
                                    override fun onClickYes() {
                                        GameOption.prison(finalCount)
                                    }

                                }).show()
                            }, 500)
                        }
                    })
                }
            }
        }
    }

    fun setData(specialBean: SpecialBean) {
        this.specialBean = specialBean
        mLlInfo.setBackgroundResource(specialBean.background)
        mTvTitle.text = specialBean.name
        mTvDesc.text = specialBean.desc
        when (specialBean.type) {
            BaseMapBean.MapType.START -> mTvSure.visibility = GONE
            BaseMapBean.MapType.ARMY -> mLlArmy.visibility = VISIBLE
            BaseMapBean.MapType.BIG_MONEY -> mTvCount.visibility = VISIBLE
            BaseMapBean.MapType.FREE_GENERALS -> mTvCount.visibility = VISIBLE
            BaseMapBean.MapType.BANK -> mTvCount.visibility = VISIBLE
            BaseMapBean.MapType.PRISON -> mTvCount.visibility = VISIBLE
            BaseMapBean.MapType.CHANCE -> mTvCount.visibility = VISIBLE
            BaseMapBean.MapType.SHOP -> {
                mTvDesc.visibility = GONE
                mRvEquipments.visibility = VISIBLE
            }
        }
    }
}