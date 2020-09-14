package com.top.kjb.tabfragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.top.kjb.R
import com.top.kjb.Userabout.updataphoneActivity
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_mysafety.*

class Mysafety : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mysafety)
        init_click()
        init_view()
    }

    override fun init_view() {
        super.init_view()
        id_user_tel.setText(functionClass.gettel())
    }

    override fun init_click() {
        super.init_click()
        id_click_phone_updata.setOnClickListener(this)
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_back -> {
                onBackPressed()
            }
            R.id.id_click_phone_updata -> {
                var intent = Intent(this, updataphoneActivity::class.java)
                startActivity(intent)

            }
        }
    }
}