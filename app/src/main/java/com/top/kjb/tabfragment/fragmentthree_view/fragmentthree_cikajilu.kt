package com.top.kjb.tabfragment.fragmentthree_view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_cikausejilu
import com.top.kjb.bean.bean_cikausejilu
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_fragmentthreecikajilu_view.*

class fragmentthree_cikajilu :BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmentthreecikajilu_view)
        init_click()
        init_refre()
        init_data()
    }
    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }
    lateinit var list:ArrayList<bean_cikausejilu>
    lateinit var adapter:adapter_cikausejilu
    private fun init_data() {
        list=ArrayList<bean_cikausejilu>()
        list.add(bean_cikausejilu())
        list.add(bean_cikausejilu())
        list.add(bean_cikausejilu())
        list.add(bean_cikausejilu())
        adapter= adapter_cikausejilu(this,list)
        id_RecyclerView.adapter=adapter
    }

    override fun init_click() {
        super.init_click()
        id_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.id_back->{
                onBackPressed()
            }
        }
    }
}