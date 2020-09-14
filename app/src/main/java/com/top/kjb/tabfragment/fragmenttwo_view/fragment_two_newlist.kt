package com.top.kjb.tabfragment.fragmenttwo_view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.top.kjb.R
import com.top.kjb.adapter.adapter_total_list
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.adapter.adapter_twopage_zixun
import com.top.kjb.adapter.adapter_user_attention_fans
import com.top.kjb.bean.*
import com.top.kjb.model.ThreeModel
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_list_view_two.*
import retrofit2.Call
import retrofit2.Response

class fragment_two_newlist : BaseFragment(), OnRefreshListener, OnLoadMoreListener {
    val threeModel: ThreeModel by lazy { ThreeModel() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list_view_two, null)

    }

    var current = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        current = arguments?.getInt("current")!!
        registerBoradcastReceiver()
        init_view()
        init_refre()
        init_click()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.publishsuccess)
        intentFilter.addAction(Sp.attent_gotoed)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.attent_gotoed->{
                    init_data()
                }
                Sp.publishsuccess -> {
                    init_data()
                }

            }
        }
    }
    lateinit var list: ArrayList<bean_total_list.bean_total_list_class>
    private fun init_data() {

        when (current) {
            0 -> {
                init_guanzhu()
            }
            1 -> {
                init_xuanliangdian()
            }
            2 -> {
            }
            3 -> {
                init_zixun()
            }
        }
    }

    private fun init_data2() {

        when (current) {
            0 -> {
                init_guanzhu2()
            }
            1 -> {
                init_xuanliangdian2()
            }
            2 -> {
            }
            3 -> {
                init_zixun2()
            }
        }
    }

    var currentPage = 1
    var pagesize = 10
    val twoModel: TwoModel by lazy { TwoModel() }
    lateinit var list_3he1: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    lateinit var adapter: adapter_twopage
    lateinit var list_zixun: ArrayList<bean_twopage_item2.bean_twopage_item2_item>
    lateinit var adapter2: adapter_twopage_zixun
    private fun init_guanzhu() {
        refreshLayout.setNoMoreData(false)
        currentPage = 1
        twoModel.userFollowselectUserFollow(functionClass.getToken(), currentPage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
                    try {
                        list_3he1.clear()
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    try {
                        list_zixun.clear()
                        adapter2.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    refreshLayout.finishRefresh()
                    id_attion_view_back.visibility = View.VISIBLE
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    println("关注成功" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {

                        list_3he1 = bean?.result?.list!!
                        if (list_3he1.size==0){
                            id_attion_view_back.visibility = View.VISIBLE
                        }else{
                            id_attion_view_back.visibility = View.GONE
                        }
                        adapter = adapter_twopage(activity!!, list_3he1)
                        id_RecyclerView.adapter = adapter
                    } else {
                        id_attion_view_back.visibility = View.VISIBLE
                        Show_toast.showText(activity, "关注list失败")

                    }
                    refreshLayout.finishRefresh()

                }

            })
    }

    private fun init_guanzhu2() {
        currentPage++
        twoModel.userFollowselectUserFollow(functionClass.getToken(), currentPage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
                    try {
                        list_3he1.clear()
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    try {
                        list_zixun.clear()
                        adapter2.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    refreshLayout.finishLoadMore()
                    id_attion_view_back.visibility = View.VISIBLE
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    println("关注成功" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        id_attion_view_back.visibility = View.GONE
                        var list = bean?.result?.list!!
                        if (list.size != 0) {
                            list_3he1.addAll(list)
                            adapter.notifyDataSetChanged()
                        } else {
                            refreshLayout?.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        }
                    } else {
                        Show_toast.showText(activity, "关注list失败")
                    }
                    refreshLayout.finishLoadMore()

                }

            })
    }

    fun init_xuanliangdian() {
        id_attion_view_back.visibility = View.GONE
        refreshLayout.setNoMoreData(false)
        currentPage = 1
        twoModel.twoselectAllHighlights(functionClass.getToken(), currentPage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                    refreshLayout.finishRefresh()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list_3he1 = bean?.result?.list!!
                        adapter = adapter_twopage(activity!!, list_3he1)
                        id_RecyclerView.adapter = adapter
                    } else {
                        Show_toast.showText(activity, "炫亮点list失败")
                    }

                    refreshLayout.finishRefresh()
                }

            })


    }

    fun init_xuanliangdian2() {
        id_attion_view_back.visibility = View.GONE
        currentPage++
        twoModel.twoselectAllHighlights(functionClass.getToken(), currentPage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                    refreshLayout.finishLoadMore()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result?.list!!
                        if (list.size != 0) {
                            list_3he1.addAll(list)
                            adapter.notifyDataSetChanged()
                        } else {
                            refreshLayout?.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        }

                    } else {
                        Show_toast.showText(activity, "炫亮点list失败")
                    }
                    refreshLayout.finishLoadMore()

                }

            })


    }

    fun init_zixun() {
        id_attion_view_back.visibility = View.GONE
        refreshLayout.setNoMoreData(false)
        currentPage = 1
        twoModel.twoselectselectAllInformation(functionClass.getToken(), currentPage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item2>> {
                override fun onFailure(call: Call<Result<bean_twopage_item2>>, t: Throwable) {
                    println("失败" + t.toString())
                    refreshLayout.finishRefresh()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item2>>,
                    response: Response<Result<bean_twopage_item2>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list_zixun = bean?.result?.list!!
                        adapter2 = adapter_twopage_zixun(activity!!, list_zixun)
                        id_RecyclerView.adapter = adapter2
                    } else {
                        Show_toast.showText(activity, "咨询list失败")
                    }
                    refreshLayout.finishRefresh()

                }

            })

    }

    fun init_zixun2() {
        id_attion_view_back.visibility = View.GONE
        currentPage++
        twoModel.twoselectselectAllInformation(functionClass.getToken(), currentPage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item2>> {
                override fun onFailure(call: Call<Result<bean_twopage_item2>>, t: Throwable) {
                    println("失败" + t.toString())
                    refreshLayout.finishLoadMore()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item2>>,
                    response: Response<Result<bean_twopage_item2>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result?.list!!
                        if (list.size != 0) {
                            list_zixun.addAll(list)
                            adapter2.notifyDataSetChanged()
                        } else {
                            refreshLayout?.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        }
                    } else {
                        Show_toast.showText(activity, "咨询list失败")
                    }
                    refreshLayout.finishLoadMore()
                }

            })

    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
    }

    override fun init_view() {
        super.init_view()

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        init_data()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        init_data2()
    }
}