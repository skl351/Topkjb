package com.top.kjb.tabfragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.top.kjb.R
import com.top.kjb.Userabout.LoginActivity
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.adapter.adapter_twopage_zixun
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_twopage_item2
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.fragmenttwo_view.publish_item
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentone.id_RecyclerView
import kotlinx.android.synthetic.main.layout_fragmenttwo.*
import retrofit2.Call
import retrofit2.Response


class fragemnt_two : BaseFragment(), View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmenttwo, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_top_bar_2.layoutParams.height=functionClass.getbarHight(activity!!)
        registerBoradcastReceiver()
        init_click()
        init_refre()
        init_view()
        init_guanzhu()

    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.publishsuccess)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.publishsuccess -> {
                    when (current_item) {
                        0 -> {
                            init_guanzhu()
                        }
                        1 -> {
                            init_xuanliangdian()
                        }
                        2 -> {//关注

                        }
                        3 -> {
                            init_zixun()
                        }
                    }
                }

            }
        }
    }

    private fun init_guanzhu() {
        refreshLayout.setNoMoreData(false)
        currentpage = 1
        twoModel.userFollowselectUserFollow(functionClass.getToken(), currentpage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
                    try {
                        list_3he1.clear()
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    try {
                        list_zixun.clear()
                        adapter2.notifyDataSetChanged()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    refreshLayout.finishRefresh()
                    id_attion_view_back.visibility=View.VISIBLE
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    println("关注成功" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        id_attion_view_back.visibility=View.GONE
                        list_3he1 = bean?.result?.list!!
                        adapter = adapter_twopage(activity!!, list_3he1)
                        id_RecyclerView.adapter = adapter
                    } else {
                        Show_toast.showText(activity, "关注list失败")

                    }
                    refreshLayout.finishRefresh()

                }

            })
    }

    private fun init_guanzhu2() {
        currentpage++
        twoModel.userFollowselectUserFollow(functionClass.getToken(), currentpage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
                    try {
                        list_3he1.clear()
                        adapter.notifyDataSetChanged()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    try {
                        list_zixun.clear()
                        adapter2.notifyDataSetChanged()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    refreshLayout.finishLoadMore()
                    id_attion_view_back.visibility=View.VISIBLE
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    println("关注成功" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        id_attion_view_back.visibility=View.GONE
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

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    lateinit var list_3he1: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    lateinit var adapter: adapter_twopage
    lateinit var list_zixun: ArrayList<bean_twopage_item2.bean_twopage_item2_item>
    lateinit var adapter2: adapter_twopage_zixun


    val twoModel: TwoModel by lazy { TwoModel() }
    var currentpage = 1
    var pagesize = 10
    fun init_circle() {
        refreshLayout.setNoMoreData(false)
        currentpage = 1
        twoModel.twoselectAllCircle(functionClass.getToken(), currentpage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
                    refreshLayout.finishRefresh()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    println("圈子成功" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list_3he1 = bean?.result?.list!!
                        adapter = adapter_twopage(activity!!, list_3he1)
                        id_RecyclerView.adapter = adapter
                    } else {
                        Show_toast.showText(activity, "圈子list失败")
                    }
                    refreshLayout.finishRefresh()

                }

            })


    }

    fun init_circle2() {
        currentpage++
        twoModel.twoselectAllCircle(functionClass.getToken(), currentpage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
                    refreshLayout.finishLoadMore()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    println("圈子成功" + response?.body()?.result)
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
                        Show_toast.showText(activity, "圈子list失败")
                    }
                    refreshLayout.finishLoadMore()

                }

            })


    }

    fun init_xuanliangdian() {
        id_attion_view_back.visibility=View.GONE
        refreshLayout.setNoMoreData(false)
        currentpage = 1
        twoModel.twoselectAllHighlights(functionClass.getToken(), currentpage, pagesize)
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
        id_attion_view_back.visibility=View.GONE
        currentpage++
        twoModel.twoselectAllHighlights(functionClass.getToken(), currentpage, pagesize)
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
        id_attion_view_back.visibility=View.GONE
        refreshLayout.setNoMoreData(false)
        currentpage = 1
        twoModel.twoselectselectAllInformation(functionClass.getToken(), currentpage, pagesize)
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
        id_attion_view_back.visibility=View.GONE
        currentpage++
        twoModel.twoselectselectAllInformation(functionClass.getToken(), currentpage, pagesize)
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

    override fun init_view() {
        super.init_view()
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
    }

    override fun init_click() {
        super.init_click()
        id_click_one.setOnClickListener(this)
        id_click_two.setOnClickListener(this)
        id_click_three.setOnClickListener(this)
        id_click_four.setOnClickListener(this)
        id_click_publish.setOnClickListener(this)
    }

    var current_item = 0
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_publish -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, publish_item::class.java)
                intent.putExtra("current_item", current_item)
                startActivity(intent)

            }
            R.id.id_click_one -> {
                id_click_publish.visibility = View.VISIBLE
                current_item = 0
                id_click_one_text.setTextColor(resources.getColor(R.color.color_333333))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_id_click_one_img.visibility = View.VISIBLE
                id_click_two_img.visibility = View.INVISIBLE
                id_click_three_img.visibility = View.INVISIBLE
                id_click_four_img.visibility = View.INVISIBLE
                init_guanzhu()

            }
            R.id.id_click_two -> {
                id_click_publish.visibility = View.VISIBLE
                current_item = 1
                id_click_one_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_333333))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_id_click_one_img.visibility = View.INVISIBLE
                id_click_two_img.visibility = View.VISIBLE
                id_click_three_img.visibility = View.INVISIBLE
                id_click_four_img.visibility = View.INVISIBLE
                init_xuanliangdian()
            }
            R.id.id_click_three -> {
                id_click_publish.visibility = View.GONE
                current_item = 2
                id_click_one_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_333333))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_id_click_one_img.visibility = View.INVISIBLE
                id_click_two_img.visibility = View.INVISIBLE
                id_click_three_img.visibility = View.VISIBLE
                id_click_four_img.visibility = View.INVISIBLE
                //yun dong
            }
            R.id.id_click_four -> {
                id_click_publish.visibility = View.GONE
                current_item = 3
                id_click_one_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_333333))
                id_id_click_one_img.visibility = View.INVISIBLE
                id_click_two_img.visibility = View.INVISIBLE
                id_click_three_img.visibility = View.INVISIBLE
                id_click_four_img.visibility = View.VISIBLE
                init_zixun()
            }

        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        when (current_item) {
            0 -> {
                init_guanzhu()
            }
            1 -> {
                init_xuanliangdian()
            }
            2 -> {
//                init_guanzhu()
            }
            3 -> {
                init_zixun()
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        when (current_item) {
            0 -> {
                init_guanzhu2()
            }
            1 -> {
                init_xuanliangdian2()
            }
            2 -> {
//                init_guanzhu2()
            }
            3 -> {
                init_zixun2()
            }
        }
    }
}