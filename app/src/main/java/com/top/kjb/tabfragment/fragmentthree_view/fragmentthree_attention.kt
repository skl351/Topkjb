package com.top.kjb.tabfragment.fragmentthree_view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_user_attention_fans
import com.top.kjb.bean.bean_attention_fans
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_fragmentthree_attention.*

class fragmentthree_attention  :BaseActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmentthree_attention)
        init_view()
        init_click()
        init_refre()
        init_data()
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    lateinit var list:ArrayList<bean_attention_fans>
    lateinit var adapter: adapter_user_attention_fans
    private fun init_data() {
        list= ArrayList()
        list.add(bean_attention_fans())
        list.add(bean_attention_fans())
        list.add(bean_attention_fans())
        adapter= adapter_user_attention_fans(this,list)
        id_RecyclerView.adapter=adapter
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.id_back->{
                onBackPressed()
            }
        }
    }

}