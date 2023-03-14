package com.hzw.android.richman.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.hzw.android.richman.MyApplication

/**
 * class ToastUtil
 *
 * @author CrazyDragon
 * description 吐司工具
 * note
 * create date 2022/2/12
 */
object ToastUtil {
    /**
     * 显示吐司
     * @param msg 字符串资源
     */
    fun show(@StringRes msg: Int) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示吐司
     * @param msg 字符串
     */
    fun show(msg: String) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show()
    }
}