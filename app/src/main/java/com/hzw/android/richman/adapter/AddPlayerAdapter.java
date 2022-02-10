package com.hzw.android.richman.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hzw.android.richman.R;
import com.hzw.android.richman.bean.PlayerBean;

import java.util.ArrayList;

/**
 * class AddPlayerAdapter
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/10
 */
public class AddPlayerAdapter extends BaseQuickAdapter<PlayerBean, BaseViewHolder> {

    public AddPlayerAdapter() {
        super(R.layout.item_add_player);
        addChildClickViewIds(R.id.mTvDelete);
        setNewInstance(new ArrayList<PlayerBean>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PlayerBean playerBean) {
        baseViewHolder.setText(R.id.mTvName, playerBean.getName());
    }
}