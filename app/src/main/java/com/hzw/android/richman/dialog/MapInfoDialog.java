package com.hzw.android.richman.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.hzw.android.richman.R;
import com.hzw.android.richman.base.BaseDialog;

/**
 * class InfoDialog
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/13
 */
public class MapInfoDialog extends BaseDialog {

    public MapInfoDialog(@NonNull Context context, View view) {
        super(context);
        setContentView(view);
        setWindowAnimations(R.style.scale_alpha_animation);
        setGravity(Gravity.CENTER);
    }
}