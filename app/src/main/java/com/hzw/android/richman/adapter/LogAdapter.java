package com.hzw.android.richman.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hzw.android.richman.R;

/**
 * class LogAdapter
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/12
 */
public class LogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LogAdapter() {
        super(R.layout.item_log);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.mTvLog, s);
    }
}