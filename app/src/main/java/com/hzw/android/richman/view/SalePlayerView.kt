package com.hzw.android.richman.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.hzw.android.richman.R
import com.hzw.android.richman.dialog.SaleDialog

/**
 * class SalePlayerView
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/19
 */
class SalePlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var playersData: MutableList<SaleDialog.SalePlayerBean>

    init {
        orientation = VERTICAL
    }

    fun setPlayers(players:MutableList<SaleDialog.SalePlayerBean>) {
        this.playersData = players
        removeAllViews()
        for (i in 0 until players.size) {
            val view = inflate(context, R.layout.view_sale_player, null)
            view.findViewById<TextView>(R.id.mTvName).text = if (playersData[i].playerBean != null) playersData[i].playerBean!!.name else "银行"
            val textView = view.findViewById<TextView>(R.id.mTvComputerPrice)
            val editText = view.findViewById<EditText>(R.id.mEtPrice)
            if (playersData[i].playerBean != null) {
                if (playersData[i].playerBean!!.isPlayer) {
                    textView.visibility = GONE
                }else {
                    editText.visibility = GONE
                    textView.text = playersData[i].price.toString()
                }
            }else {
                editText.visibility = GONE
                textView.text = playersData[i].price.toString()

            }

            editText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    playersData[i].price = s.toString().toInt()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            addView(view)
        }
    }

    fun update(sum:Int) {
        for (i in 0 until playersData.size) {
            if (playersData[i].playerBean == null || !playersData[i].playerBean!!.isPlayer) {
                val price =  if (sum < playersData[i].playerBean!!.money) sum else playersData[i].playerBean!!.money
                getChildAt(i).findViewById<TextView>(R.id.mTvComputerPrice).text = price.toString()
                playersData[i].price = sum
            }
        }
    }
}