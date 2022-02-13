package com.hzw.android.richman.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.hzw.android.richman.R;
import com.hzw.android.richman.base.BaseDialog;
import com.hzw.android.richman.utils.ScreenUtil;

/**
 * class PlayerInfoDialog
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
public class PlayerInfoDialog extends BaseDialog {

    public PlayerInfoDialog(@NonNull Context context, View view) {
        super(context);
        setContentView(view);
        setWidth(ScreenUtil.INSTANCE.getScreenWidth()/3);
        setFullHeight(1);
        setWindowAnimations(R.style.right_in_right_out_animation);
        setGravity(Gravity.END|Gravity.TOP);
    }
}