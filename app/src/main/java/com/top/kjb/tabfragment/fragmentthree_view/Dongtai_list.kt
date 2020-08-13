package com.top.kjb.tabfragment.fragmentthree_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.originpack.BaseFragment
import kotlinx.android.synthetic.main.layout_list_view.*

class Dongtai_list : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list_view, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        registerBoradcastReceiver()
        init_view()
        init_refre()
        init_click()
        init_data()
    }

    lateinit var list:ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    lateinit var adapter:adapter_twopage
    private fun init_data() {
        list= ArrayList()
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        adapter= adapter_twopage(activity!!,list)
        id_RecyclerView.adapter=adapter
    }


    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_view() {
        super.init_view()

    }
}