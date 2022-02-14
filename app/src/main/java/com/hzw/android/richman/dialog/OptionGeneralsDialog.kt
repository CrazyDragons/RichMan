package com.hzw.android.richman.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.GeneralsAdapter
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.bean.GeneralsBean
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameOption
import kotlinx.android.synthetic.main.dialog_option_generals.*

/**
 * class DefenseDialog
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class OptionGeneralsDialog(context: Context, type: TYPE, cityBean: BaseCityBean) : BaseDialog(context) {
    init {
        setContentView(R.layout.dialog_option_generals)
        setWindowAnimations(R.style.scale_alpha_animation)
        setGravity(Gravity.CENTER)

        mRvGenerals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val generalsAdapter = GeneralsAdapter()
        generalsAdapter.setNewInstance(getStayGenerals())
        mRvGenerals.adapter = generalsAdapter

        when(type) {
            TYPE.DEFENSE -> {
                mTvTips.visibility = View.VISIBLE
                mTvTips.setOnClickListener {
                    GameOption.defense(cityBean, null)
                    dismiss()
                }
                generalsAdapter.setOnItemClickListener { _, _, position ->
                    GameOption.defense(cityBean, generalsAdapter.data[position])
                    dismiss()
                }
            }

            TYPE.PK -> {
                mTvTips.visibility = View.GONE
                generalsAdapter.setOnItemClickListener { _, _, position ->
                    GameOption.pk(cityBean, generalsAdapter.data[position])
                    dismiss()
                }
            }

            TYPE.ATTACK -> {
                mTvTips.visibility = View.GONE
                generalsAdapter.setOnItemClickListener { _, _, position ->
                    GameOption.attack(cityBean, generalsAdapter.data[position])
                    dismiss()
                }
            }
        }



    }

    private fun getStayGenerals():MutableList<GeneralsBean> {
        val list = mutableListOf<GeneralsBean>()

        for (item in GameData.INSTANCE.currentPlayer().generals) {
            if (item.city == null) {
                list.add(item)
            }
        }

        return list
    }

    enum class TYPE {
        DEFENSE,PK,ATTACK
    }
}