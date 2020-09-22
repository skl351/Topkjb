package com.top.kjb.tabfragment.sport

import android.os.Bundle
import android.view.View
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.tabfragment.fragmenttwo_view.fragment_two_newlist
import kotlinx.android.synthetic.main.layout_sports_zixun.*

class More_zixun : BaseActivity(), View.OnClickListener {


    lateinit var fragment: fragment_two_newlist
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sports_zixun)
        init_click()
        fragment = fragment_two_newlist()
        var Bundle = Bundle()
        Bundle.putInt("current", 3)
        fragment?.arguments = Bundle
        // add and show first fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    override fun init_click() {
        super.init_click()
        id_top_bar.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }
}