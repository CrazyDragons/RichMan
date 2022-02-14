package com.hzw.android.richman.dialog

import android.content.Context
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.GeneralsAdapter
import com.hzw.android.richman.base.BaseCityBean
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.bean.GeneralBean
import com.hzw.android.richman.game.GameData
import com.hzw.android.richman.game.GameOption
import kotlinx.android.synthetic.main.dialog_defense.*

/**
 * class DefenseDialog
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
class DefenseDialog(context: Context, cityBean: BaseCityBean) : BaseDialog(context) {
    init {
        setContentView(R.layout.dialog_defense)
        setWindowAnimations(R.style.scale_alpha_animation)
        setGravity(Gravity.CENTER)

        mRvGenerals.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val generalsAdapter = GeneralsAdapter()
        generalsAdapter.setNewInstance(getDefense())
        mRvGenerals.adapter = generalsAdapter
        generalsAdapter.setOnItemClickListener { _, _, position ->
            GameOption.defense(cityBean, generalsAdapter.data[position])
            dismiss()
        }
        mTvEmptyGeneral.setOnClickListener {
            GameOption.defense(cityBean, null)
            dismiss()
        }
    }

    private fun getDefense():MutableList<GeneralBean> {
        val list = mutableListOf<GeneralBean>()

        for (item in GameData.INSTANCE.currentPlayer().generals) {
            if (item.city == null) {
                list.add(item)
            }
        }

        return list
    }
}