package com.top.kjb.tabfragment.fragmentthree_view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_information
import com.top.kjb.bean.bean_infomation
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_information_view.*

class Information : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_information_view)
        init_refre()
        init_data()
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    lateinit var list: ArrayList<bean_infomation>
    lateinit var adapter: adapter_information
    private fun init_data() {
        list = ArrayList()
        list.add(bean_infomation())
        list.add(bean_infomation())
        list.add(bean_infomation())
        adapter = adapter_information(this, list)
        id_RecyclerView.adapter = adapter
    }
}