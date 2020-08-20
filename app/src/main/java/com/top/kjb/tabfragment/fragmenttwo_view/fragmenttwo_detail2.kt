package com.top.kjb.tabfragment.fragmenttwo_view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_user_comment2
import com.top.kjb.bean.bean_user_comment
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_fragmenttwo_detail_view2.*

class fragmenttwo_detail2 : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmenttwo_detail_view2)
        init_refre()
        init_click()
        init_data()
    }

    lateinit var list:ArrayList<bean_user_comment>
    lateinit var adapter_comment: adapter_user_comment2
    private fun init_data() {
        list= ArrayList()
        list.add(bean_user_comment())
        list.add(bean_user_comment())
        list.add(bean_user_comment())
        adapter_comment=adapter_user_comment2(this,list)
        id_RecyclerView.adapter=adapter_comment
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }
}