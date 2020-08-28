package com.top.kjb.tabfragment.fragmentthree_view.userinfo.mysettingactivity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_informationandtixing.*

class informationAndtixing : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_informationandtixing)
        init_click()
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }
}