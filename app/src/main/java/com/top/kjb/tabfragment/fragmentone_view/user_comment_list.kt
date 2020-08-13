package com.top.kjb.tabfragment.fragmentone_view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_user_comment
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_fragemntone_detail.*
import kotlinx.android.synthetic.main.layout_user_comment_list.*
import kotlinx.android.synthetic.main.layout_user_comment_list.id_RecyclerView

class user_comment_list : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_user_comment_list)
        init_click()
        init_refre()
        init_data()
    }

    lateinit var list: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    var adapter: adapter_user_comment? = null
    private fun init_data() {
        list = ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>()
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        adapter = adapter_user_comment(this, list)
        id_RecyclerView.adapter = adapter
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_back->{
                onBackPressed()

            }
        }
    }
}