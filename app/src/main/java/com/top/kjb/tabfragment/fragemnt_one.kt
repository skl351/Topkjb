package com.top.kjb.tabfragment

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.location.LocationClientOption.LocationMode
import com.top.kjb.MainActivity
import com.top.kjb.MyApplicatipn
import com.top.kjb.R
import com.top.kjb.Userabout.LoginActivity
import com.top.kjb.adapter.adapter_mainpage
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_main_item
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.utils.MyLocationListener
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentone.*
import retrofit2.Call
import retrofit2.Response


class fragemnt_one : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmentone, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBoradcastReceiver()
        init_click()
        init_refre()
        init_view()
        init_data()
    }




    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.location_send)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var new_lat = ""
    var new_lng = ""
    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.location_send -> {
                    new_lat = intent.getStringExtra("lat").toString()
                    new_lng = intent.getStringExtra("lng").toString()
                    var locationplace = intent.getStringExtra("locationplace")
                    id_locationplace.setText(locationplace)
                }

            }
        }
    }


    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }


    private fun init_data() {
        init_banner()
        init_listone()
    }


    val mainModel: MainModel by lazy { MainModel() }
    private fun init_banner() {
        mainModel.mainbannergetad().enqueue(object : retrofit2.Callback<Result<ArrayList<String>>> {
            override fun onFailure(call: Call<Result<ArrayList<String>>>, t: Throwable) {
                println("失败" + t.toString())
                functionClass.error_open(t.toString())
            }

            override fun onResponse(
                call: Call<Result<ArrayList<String>>>,
                response: Response<Result<ArrayList<String>>>
            ) {
                println("首页banner" + response?.body()?.result)
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    var list = bean?.result
                    id_banner.setViewUrls(activity, list, null)
                } else {
                    Toast.makeText(activity, "bannner flag false", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    lateinit var list_main_data: ArrayList<bean_main_item>
    lateinit var adapter_main_data: adapter_mainpage
    fun init_listone() {
        mainModel.main_zonghe()
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    response: Response<Result<ArrayList<bean_main_item>>>
                ) {
                    println("首页场地综合" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list_main_data = bean?.result!!
                        adapter_main_data = adapter_mainpage(activity!!, list_main_data)
                        id_RecyclerView.adapter = adapter_main_data
                    } else {
                        Show_toast.showText(activity, "综合排序失败")
                    }

                }

            })


    }

    fun init_listtwo() {
        mainModel.main_zonghe()
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    response: Response<Result<ArrayList<bean_main_item>>>
                ) {
                    println("首页评分排序" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list_main_data = bean?.result!!
                        adapter_main_data = adapter_mainpage(activity!!, list_main_data)
                        id_RecyclerView.adapter = adapter_main_data
                    } else {
                        Show_toast.showText(activity, "评分最高排序失败")
                    }

                }

            })


    }

    fun init_listthree(lat: String, lng: String) {
        mainModel.main_zuijing(lat, lng)
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    response: Response<Result<ArrayList<bean_main_item>>>
                ) {
                    println("首页场地" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list_main_data = bean?.result!!
                        adapter_main_data = adapter_mainpage(activity!!, list_main_data)
                        id_RecyclerView.adapter = adapter_main_data
                    } else {
                        Toast.makeText(activity, "离我最近排序失败", Toast.LENGTH_SHORT).show()
                    }

                }

            })


    }

    override fun init_view() {
        super.init_view()

    }

    override fun init_click() {
        super.init_click()
        id_zonghe_click_big.setOnClickListener(this)
        id_pingfen_click_big.setOnClickListener(this)
        id_nearest_click_big.setOnClickListener(this)
        id_click_location.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_location->{
                (activity as MainActivity).init_questlocation()
            }
            R.id.id_nearest_click_big -> {
                id_zonghe_click.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_zonghe_line.visibility = View.INVISIBLE

                id_pingfen_click.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_pingfen_line.visibility = View.INVISIBLE

                id_nearest_click.setTextColor(resources.getColor(R.color.black))
                id_nearest_line.visibility = View.VISIBLE
                init_listthree(new_lat, new_lng)
            }
            R.id.id_pingfen_click_big -> {
                id_zonghe_click.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_zonghe_line.visibility = View.INVISIBLE

                id_pingfen_click.setTextColor(resources.getColor(R.color.black))
                id_pingfen_line.visibility = View.VISIBLE

                id_nearest_click.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_nearest_line.visibility = View.INVISIBLE
                init_listtwo()

            }
            R.id.id_zonghe_click_big -> {
                id_zonghe_click.setTextColor(resources.getColor(R.color.black))
                id_zonghe_line.visibility = View.VISIBLE

                id_pingfen_click.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_pingfen_line.visibility = View.INVISIBLE

                id_nearest_click.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_nearest_line.visibility = View.INVISIBLE
                init_listone()
            }
        }
    }





}