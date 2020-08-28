package com.top.kjb.tabfragment.fragmentthree_view.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_order
import com.top.kjb.bean.bean_order_item
import com.top.kjb.originpack.BaseFragment
import kotlinx.android.synthetic.main.layout_order1_list_view.*

class order1_list:BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.layout_order1_list_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init_refre()
        init_data()
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    lateinit var list:ArrayList<bean_order_item>
    lateinit var adapter:adapter_order
    open fun init_data() {
        list= ArrayList()
        list.add(bean_order_item())
        list.add(bean_order_item())
        list.add(bean_order_item())
        list.add(bean_order_item())
        list.add(bean_order_item())
        list.add(bean_order_item())
        list.add(bean_order_item())
        adapter= adapter_order(activity!!,list)
        id_RecyclerView.adapter=adapter
    }


}