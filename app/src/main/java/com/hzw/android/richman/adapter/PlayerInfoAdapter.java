package com.hzw.android.richman.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hzw.android.richman.R;
import com.hzw.android.richman.bean.CityBean;
import com.hzw.android.richman.bean.PlayerBean;
import com.hzw.android.richman.game.GameData;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * class PlayerInfoAdapter
 *
 * @author CrazyDragon
 * description
 * note
 * create date 2022/2/12
 */
public class PlayerInfoAdapter extends BaseQuickAdapter<PlayerBean, BaseViewHolder> {

    public PlayerInfoAdapter() {
        super(R.layout.item_player_info);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PlayerBean playerBean) {
        baseViewHolder.setText(R.id.mTvName, playerBean.getName())
                .setText(R.id.mTvGDP, getGDP(playerBean) + "%")
                .setText(R.id.mTvMoney, playerBean.getMoney() + "")
                .setText(R.id.mTvArmy, playerBean.getArmy() + "")
                .setText(R.id.mTvCity, playerBean.getCitys().size() + "")
                .setText(R.id.mTvGeneral, playerBean.getGenerals().size() + "")
                .setText(R.id.mTvEquipments, playerBean.getEquipments().size() + "");
    }

    private double getSingleGDP(PlayerBean playerBean) {
        return playerBean.getMoney() +
                playerBean.getArmy() * 2 +
                getCityMoney(playerBean.getCitys()) +
                playerBean.getGenerals().size() * 2000 +
                playerBean.getEquipments().size() * 2000;
    }

    private int getGDP(PlayerBean playerBean) {
        double x;
        double sum = 0;

        for (int i = 0; i < GameData.Companion.getINSTANCE().getPlayerData().size(); i++) {
            sum += getSingleGDP(GameData.Companion.getINSTANCE().getPlayerData().get(i));
        }

        x = getSingleGDP(playerBean) / sum;

        return (int) (getDouble2(x)*100);

    }

    private double getCityMoney(ArrayList<CityBean> arrayList) {
        double money = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            money += arrayList.get(i).getBuyPrice() + (arrayList.get(i).getBuyPrice() / 2d) * arrayList.get(i).getLevel();
        }

        return money;
    }

    private double getDouble2(double value) {
        return new BigDecimal(value).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}