package com.hzw.android.richman.dialog

import android.content.Context
import android.view.Gravity
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.bean.StockBean
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.listener.OnRefreshListener
import com.hzw.android.richman.utils.ScreenUtil
import kotlinx.android.synthetic.main.dialog_stock.*

/**
 * class StockDialog
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/18
 */
class StockDialog (context: Context, stockBean: StockBean, onRefreshListener: OnRefreshListener) : BaseDialog(context) {

    var buyNumber = 0
    var saleNumber = 0

    init {
        setContentView(R.layout.dialog_stock)
        setWindowAnimations(R.style.scale_alpha_animation)
        setWidth(ScreenUtil.dp2px(context, 400))
        setHeight(ScreenUtil.dp2px(context, 200))
        setCanceledOnTouchOutside(true)
        setGravity(Gravity.CENTER)

        mTvName.text = stockBean.name
        mTvNewPrice.text = "当前股价："+stockBean.newPrice.toString()
        mTvStockNumber.text = "当前持股："+GameData.INSTANCE.currentPlayer().stockNumber(stockBean.name)
        mTvNewPrice.setTextColor(ContextCompat.getColor(context, if (stockBean.newPrice > stockBean.oldPrice) R.color.colorRed else R.color.colorGreen))

        val buyMax = GameData.INSTANCE.currentPlayer().money / stockBean.newPrice
        val saleMax = GameData.INSTANCE.currentPlayer().stockNumber(stockBean.name)

        mSbBuy.max = buyMax
        mSbSale.max = saleMax

        mTvMaxBuy.text = buyMax.toString()
        mTvMaxSale.text = saleMax.toString()

        mSbBuy.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                buyNumber = progress
                mTvMaxBuy.text = buyNumber.toString()
                mTvBuyMoney.text = "入股花费："+(stockBean.newPrice * progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        mSbSale.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                saleNumber = progress
                mTvMaxSale.text = saleNumber.toString()
                mTvSaleMoney.text = "卖出赚得："+(stockBean.newPrice * progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        mTvBuy.setOnClickListener {
            GameData.INSTANCE.currentPlayer().buyStock(stockBean, buyNumber)
            onRefreshListener.onRefreshData()
            dismiss()
        }

        mTvSale.setOnClickListener {
            GameData.INSTANCE.currentPlayer().saleStock(stockBean, saleNumber)
            onRefreshListener.onRefreshData()
            dismiss()
        }

    }
}