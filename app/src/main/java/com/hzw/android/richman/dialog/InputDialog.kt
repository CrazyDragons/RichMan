package com.hzw.android.richman.dialog

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hzw.android.richman.R
import com.hzw.android.richman.base.BaseDialog
import com.hzw.android.richman.listener.OnInputListener
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
    private var onInputListener:OnInputListener

    init {
        setContentView(R.layout.dialog_input)
        setWindowAnimations(R.style.scale_alpha_animation)
        setFull()
        setGravity(Gravity.CENTER)
        this.onInputListener = onInputListener

        mEtInput.addTextChangedListener(this)
        mEtInput.setOnEditorActionListener(this)

        mTvYes.setOnClickListener {
            onInputListener.onInput(msg)
            dismiss()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
        if (actionId == 0) {
            onInputListener.onInput(msg)
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