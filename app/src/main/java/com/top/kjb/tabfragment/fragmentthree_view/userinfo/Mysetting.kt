package com.top.kjb.tabfragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_mysetting.*

class Mysetting : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mysetting)
        init_click()
    }

    override fun init_click() {
        super.init_click()
        id_click_myselfinfoseting.setOnClickListener(this)
        id_click_safe.setOnClickListener(this)
        id_click_safe.setOnClickListener(this)
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_back->{
                onBackPressed()
            }
            R.id.id_click_myselfinfoseting -> {
                var intent=Intent(this,Myselfinfo::class.java)
                startActivity(intent)
            }
            R.id.id_click_safe->{
                var intent=Intent(this,Mysafety::class.java)
                startActivity(intent)
            }
        }
    }
}