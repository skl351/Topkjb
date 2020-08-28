package com.top.kjb.tabfragment.fragmentthree_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_total_list
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.adapter.adapter_user_attention_fans
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_attention_fans
import com.top.kjb.bean.bean_total_list
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.model.ThreeModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_list_view.*
import retrofit2.Call
import retrofit2.Response

class Dongtai_list : BaseFragment() {
    val threeModel: ThreeModel by lazy { ThreeModel() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list_view, null)

    }

    var userId=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getInt("userId")!!
//        registerBoradcastReceiver()
        init_view()
        init_refre()
        init_click()
        init_data()
    }

    lateinit var list: ArrayList<bean_total_list.bean_total_list_class>
    lateinit var adapter: adapter_total_list
    private fun init_data() {

        init_dongtai()
    }

    var currentPage = 1
    var pagesize = 10
    fun init_dongtai() {
        currentPage = 1
        threeModel.userDynamicselectUserDynamic(
            functionClass.getToken(),
            userId,
            currentPage,
            pagesize
        )
            .enqueue(object : retrofit2.Callback<Result<bean_total_list>> {
                override fun onFailure(
                    call: Call<Result<bean_total_list>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_total_list>>,
                    response: Response<Result<bean_total_list>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list = response?.body()?.result?.list!!
                        if (list.size == 0) {
                            id_attion_view_back.visibility = View.VISIBLE
                        } else {
                            id_attion_view_back.visibility = View.GONE
                        }
                        adapter = adapter_total_list(activity!!, list)
                        id_RecyclerView.adapter = adapter

                    }
                }

            })
    }


    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_view() {
        super.init_view()

    }
}