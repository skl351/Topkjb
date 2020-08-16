package com.top.kjb.tabfragment.fragmentone_view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.top.kjb.R
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.adapter.adapter_user_comment
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.customview.pay_bottom
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_fragemntone_detail.*

class fragmentone_detail : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragemntone_detail)
        init_refre()
        init_click()
        init_data()
    }

    lateinit var list: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    var adapter: adapter_user_comment? = null
    private fun init_data() {
        list = ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>()
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        adapter = adapter_user_comment(this!!, list)
        id_RecyclerView.adapter = adapter
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_click() {
        super.init_click()
        id_click_commtent_list.setOnClickListener(this)
        id_click_place_info.setOnClickListener(this)
        id_back.setOnClickListener(this)
        id_click_pay.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_place_info->{
                var intent=Intent(this,fragmentone_place_info::class.java)
                startActivity(intent)
            }
            R.id.id_click_pay->{
                var bottom= pay_bottom(this)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .atView(id_click_pay)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_back -> {
                onBackPressed()
            }
            R.id.id_click_commtent_list -> {
                var intent = Intent(this, user_comment_list::class.java)
                startActivity(intent)
            }
        }
    }
}