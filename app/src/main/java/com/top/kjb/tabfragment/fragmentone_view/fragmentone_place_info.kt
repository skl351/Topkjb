package com.top.kjb.tabfragment.fragmentone_view

import android.os.Bundle
import android.view.View
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_place_info.*

class fragmentone_place_info : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_place_info)
        init_click()
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }
}