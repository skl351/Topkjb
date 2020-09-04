package com.top.kjb.tabfragment

import android.os.Bundle
import android.view.View
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_mysafety.*

class Mysafety: BaseActivity() ,View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mysafety)
        init_click()
    }

    override fun init_click() {
        super.init_click()
        id_click_phone_updata.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.id_click_phone_updata->{

            }
        }
    }
}