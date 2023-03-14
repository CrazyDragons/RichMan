package com.hzw.android.richman.dialog

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.listener.OnInputListener
import com.hzw.android.richman.utils.MapUtil
import com.hzw.android.richman.utils.ToastUtil
import kotlinx.android.synthetic.main.dialog_input.*


/**
 * class InputDialog
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/15
 */
class InputDialog(context: AppCompatActivity, onInputListener: OnInputListener) :
    BaseDialog(context), TextWatcher, TextView.OnEditorActionListener {

    private var msg = ""
    private var onInputListener: OnInputListener
    private var buff: PlayerBean.BUFF? = null

    init {
        setContentView(R.layout.dialog_input)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)
        this.onInputListener = onInputListener

        mEtInput.addTextChangedListener(this)
        mEtInput.setOnEditorActionListener(this)

        val list = mutableListOf<PlayerBean.BUFF>()
        list.add(PlayerBean.BUFF.ADD_COST)
        list.add(PlayerBean.BUFF.REDUCE_COST)
        list.add(PlayerBean.BUFF.ADD_ATTACK_ARMY)
        list.add(PlayerBean.BUFF.REDUCE_ATTACK_ARMY)
        list.add(PlayerBean.BUFF.ADD_ATTACK)
        list.add(PlayerBean.BUFF.ADD_DEFENSE)
        list.add(PlayerBean.BUFF.ADD_MONEY)
        list.add(PlayerBean.BUFF.ADD_ARMY)
        list.add(PlayerBean.BUFF.ADD_STOCK)
        list.add(PlayerBean.BUFF.ADD_CITY)
        list.add(PlayerBean.BUFF.ADD_GENERALS)
        list.add(PlayerBean.BUFF.ADD_EQUIPMENTS)

        mRvPlayer.layoutManager = GridLayoutManager(context, 6)
        val buffAdapter =
            object : BaseQuickAdapter<PlayerBean.BUFF, BaseViewHolder>(R.layout.item_buff) {
                override fun convert(holder: BaseViewHolder, item: PlayerBean.BUFF) {
                    when (item) {
                        PlayerBean.BUFF.ADD_COST -> holder.setText(R.id.mTvDesc, "收租金增加10%")
                        PlayerBean.BUFF.REDUCE_COST -> holder.setText(R.id.mTvDesc, "交租金减少10%")
                        PlayerBean.BUFF.ADD_ATTACK_ARMY -> holder.setText(R.id.mTvDesc, "驻兵增加10%")
                        PlayerBean.BUFF.REDUCE_ATTACK_ARMY -> holder.setText(R.id.mTvDesc, "损兵减加10%")
                        PlayerBean.BUFF.ADD_ATTACK -> holder.setText(R.id.mTvDesc, "攻击增加5")
                        PlayerBean.BUFF.ADD_DEFENSE -> holder.setText(R.id.mTvDesc, "防御增加5")
                        PlayerBean.BUFF.ADD_MONEY -> holder.setText(R.id.mTvDesc, "初始金额+50%")
                        PlayerBean.BUFF.ADD_ARMY -> holder.setText(R.id.mTvDesc, "初始兵力+50%")
                        PlayerBean.BUFF.ADD_GENERALS -> holder.setText(R.id.mTvDesc, "初始武将+2")
                        PlayerBean.BUFF.ADD_EQUIPMENTS -> holder.setText(R.id.mTvDesc, "初始道具+2")
                        PlayerBean.BUFF.ADD_CITY -> holder.setText(R.id.mTvDesc, "初始城池+1")
                        PlayerBean.BUFF.ADD_STOCK -> holder.setText(R.id.mTvDesc, "初始股票+50股")
                    }
                }

            }
        buffAdapter.setNewInstance(list)
        buffAdapter.setOnItemClickListener { _, _, position ->
            mTvBuff.text = MapUtil.buffDesc(buffAdapter.data[position])
            buff = buffAdapter.data[position]
        }
        mRvPlayer.adapter = buffAdapter

        mTvYes.setOnClickListener {
            if (buff == null) {
                ToastUtil.show("请选择角色buff")
                return@setOnClickListener
            }
            onInputListener.onInput(msg, buff!!)
            dismiss()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
        if (actionId == 0) {
            if (buff == null) {
                ToastUtil.show("请选择角色buff")
                return false
            }
            onInputListener.onInput(msg, buff!!)
            dismiss()
        }
        return false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        msg = s.toString()
    }

    override fun afterTextChanged(s: Editable?) {
    }
}