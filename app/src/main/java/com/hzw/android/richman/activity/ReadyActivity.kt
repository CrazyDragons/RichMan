package com.hzw.android.richman.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.AddPlayerAdapter
import com.hzw.android.richman.base.BaseActivity
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.config.Constants
import com.hzw.android.richman.dialog.ProgressDialog
import com.hzw.android.richman.game.GameSave
import com.hzw.android.richman.listener.OnInputListener
import com.hzw.android.richman.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_ready.*

/**
 * class GameActivity
 *
 * @author CrazyDragon
 * description 游戏准备界面
 * note
 * create date 2022/2/9
 */
class ReadyActivity : BaseActivity(), OnInputListener {

    private var adapter = AddPlayerAdapter()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        progressDialog = ProgressDialog(this)

        mRvPlayer.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRvPlayer.adapter = adapter
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.mTvDelete) {
                adapter.removeAt(position)
            }
        }


        mTvAddPlayer.setOnClickListener {
            adapter.addData(PlayerBean(resources.getString(R.string.player), true))
//            InputDialog(this, this).show()
        }

        mTvAddComputer.setOnClickListener {
            adapter.addData(PlayerBean(resources.getString(R.string.computer), false))
        }

        mTvStart.setOnClickListener {

            if (adapter.data.isEmpty()) {
                ToastUtil.show(R.string.no_player)
                return@setOnClickListener
            }
            if (adapter.data.size > 6) {
                ToastUtil.show("最多只能6人参加！")
                return@setOnClickListener
            }

            progressDialog.show()


            for (i in 0 until adapter.data.size) {
                adapter.data[i].id = i + 1
                adapter.data[i].name = (i + 1).toString() + adapter.data[i].name
//                if (!adapter.data[i].isPlayer) {
//                    adapter.data[i].name = adapter.data[i].name + (i + 1).toString()
//                }
            }
            GameSave.savePlayer(JSON.toJSONString(adapter.data))
            startActivity(
                Intent(this, GameActivity::class.java).putExtra(
                    (Constants.NEW_GAME),
                    true
                )
            )
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog.dismiss()
    }

    override fun onInput(msg: String) {
        adapter.addData(PlayerBean(msg, true))
        val im: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(
            this.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}