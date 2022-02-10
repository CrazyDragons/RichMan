package com.hzw.android.richman.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.hzw.android.richman.R
import com.hzw.android.richman.adapter.AddPlayerAdapter
import com.hzw.android.richman.bean.PlayerBean
import com.hzw.android.richman.save.Save
import kotlinx.android.synthetic.main.activity_ready.*


class ReadyActivity : BaseActivity() {

    private var adapter = AddPlayerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)


        mRvPlayer.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mRvPlayer.adapter = adapter
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.mTvDelete) {
                adapter.removeAt(position)
            }
        }


        mTvAddPlayer.setOnClickListener {
            showInput()
        }

        mTvAddComputer.setOnClickListener {
            adapter.addData(PlayerBean("电脑", false))
        }

        mTvStart.setOnClickListener {
            for (i in 0 until  adapter.data.size) {
                adapter.data[i].id = i+1
                if (!adapter.data[i].isPlayer) {
                    adapter.data[i].name = adapter.data[i].name + (i+1).toString()
                }
            }
            Save.INSTANCE.savePlayer(JSON.toJSONString(adapter.data))
            startActivity(Intent(this, GameActivity::class.java).putExtra(("newGame"), true))
        }

    }

    private fun showInput() {
        val editText = EditText(this)
        val builder = AlertDialog.Builder(this).setTitle("输入昵称").setView(editText)
            .setPositiveButton("加入") { _, _ ->
                adapter.addData(PlayerBean(editText.text.toString(), true))

            }
        builder.create().show()
    }
}