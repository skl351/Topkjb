package com.top.kjb.tabfragment

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_head_yuanquan
import com.top.kjb.adapter.wuxianhuadongtab.GoodsFragment
import com.top.kjb.adapter.wuxianhuadongtab.TabAdapter
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_sports_list_item
import com.top.kjb.bean.bean_type_item
import com.top.kjb.model.ClueModel
import com.top.kjb.model.MainModel
import com.top.kjb.model.SportsModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.club.Club_activity
import com.top.kjb.tabfragment.sport.More_zixun
import com.top.kjb.tabfragment.sport.request_sports
import com.top.kjb.tabfragment.sport.sports_detail_view
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util
import kotlinx.android.synthetic.main.layout_fragment_one_sports.*
import retrofit2.Call
import retrofit2.Response


class fragment_one_sprots : BaseFragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_one_sports, null)

    }

    val sportsModel: SportsModel by lazy { SportsModel() }
    val mainModel: MainModel by lazy { MainModel() }
    val clueModel: ClueModel by lazy { ClueModel() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        id_fragment_one_sports_bar.layoutParams.height = functionClass.getbarHight(activity!!)
        registerBoradcastReceiver()
        init_click()
        init_view()
        init_horline()
        init_banner()
        init_mysports()
        init_myclub()

    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.sport_send)
        intentFilter.addAction(Sp.loginoutsuccess)

        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.loginoutsuccess -> {
                    init_mysports()
                    init_myclub()

                }
                Sp.sport_send -> {
                    init_mysports()
                }

            }
        }
    }

    private fun init_myclub() {
        if (!functionClass.islogin()) {
            id_show_myclub_info.visibility = View.VISIBLE
            id_RecyclerView_myclub_head.visibility = View.GONE
            return
        }
        id_show_myclub_info.visibility = View.GONE
        id_RecyclerView_myclub_head.visibility = View.VISIBLE
        clueModel.clubselectMemberHeadImgList(functionClass.getToken())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<String>>> {
                override fun onFailure(call: Call<Result<ArrayList<String>>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<ArrayList<String>>>,
                    response: Response<Result<ArrayList<String>>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        val adapter = adapter_head_yuanquan(context!!, bean?.result!!)
                        id_RecyclerView_myclub_head.setAdapter(adapter)
                    }

                }

            })


    }

    private fun init_mysports() {
        if (!functionClass.islogin()) {
            id_mysport_big.removeAllViews()
            return
        }
        sportsModel.togetherLogsearchMyTogetherLog(
            functionClass.getToken(),
            functionClass.getUserId()
        )
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_sports_list_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_sports_list_item>>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_sports_list_item>>>,
                    response: Response<Result<ArrayList<bean_sports_list_item>>>
                ) {
                    var bean = response.body()
                    if ("success".equals(bean?.flag)) {
                        id_mysport_big.removeAllViews()
                        for (i in 0..bean?.result?.size!! - 1) {
                            var bean_nei = bean?.result?.get(i)
                            val view = View.inflate(activity, R.layout.layout_my_sports_item, null)
                            var str = ""
                            val start_time =
                                functionClass.getTime_ms(
                                    bean_nei?.togetherLogs?.time.toString(),
                                    "HH:mm"
                                )
                            val end_time =
                                functionClass.getTime_ms(
                                    bean_nei?.togetherLogs?.endTime.toString(),
                                    "HH:mm"
                                )
                            var chazhi = ""
                            try {
                                val a: Long =
                                    (bean_nei?.togetherLogs?.endTime?.toLong()!! - bean_nei?.togetherLogs?.time?.toLong()!!) / 3600000
                                chazhi = a.toString() + ""
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            str = start_time + " - " + end_time + " (" + chazhi + "小时)"

                            var zhouji = java_util.getWeek(bean_nei?.togetherLogs?.time.toString())

                            val right_time1 =
                                functionClass.getTime_ms(
                                    bean_nei?.togetherLogs?.time.toString(),
                                    "MM月dd日"
                                )
                            view.findViewById<TextView>(R.id.id_mysport_right)
                                .setText(right_time1 + " " + zhouji)
                            view.findViewById<TextView>(R.id.id_mysports_time).setText(str)
                            view.findViewById<TextView>(R.id.id_mysports_address)
                                .setText(bean_nei?.gymnasiumName)
                            view.findViewById<TextView>(R.id.id_mysport_number_str)
                                .setText("已参加：" + bean_nei?.togetherLogs?.nowPeople + "/" + bean_nei?.togetherLogs?.maxPeople)
                            view.findViewById<View>(R.id.id_cancel_sport)
                                .setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(p0: View?) {
                                        val builder: AlertDialog.Builder =
                                            AlertDialog.Builder(activity)
                                        builder.setMessage("确定取消运动吗")
                                        builder.setPositiveButton("确定",
                                            object : DialogInterface.OnClickListener {
                                                override fun onClick(
                                                    p0: DialogInterface?,
                                                    p1: Int
                                                ) {
                                                    sportsModel.togetherLogupdateMyTogetherLog(
                                                        functionClass.getToken(),
                                                        functionClass.getUserId(),
                                                        bean_nei?.togetherLogs?.id!!,
                                                        1
                                                    ).enqueue(object :
                                                        retrofit2.Callback<Result<String>> {
                                                        override fun onFailure(
                                                            call: Call<Result<String>>,
                                                            t: Throwable
                                                        ) {

                                                        }

                                                        override fun onResponse(
                                                            call: Call<Result<String>>,
                                                            response: Response<Result<String>>
                                                        ) {
                                                            var bean = response.body()
                                                            if ("success".equals(bean?.flag)) {
                                                                Show_toast.showText(
                                                                    activity,
                                                                    "取消运动成功"
                                                                )

                                                                var intent = Intent(Sp.sport_send)
                                                                activity?.sendBroadcast(intent)

                                                            } else {

                                                            }
                                                        }

                                                    })
                                                }

                                            })
                                        builder.show()

                                    }

                                })
                            view.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(p0: View?) {
                                    val intent = Intent(
                                        context,
                                        sports_detail_view::class.java
                                    )
                                    intent.putExtra("id", bean_nei?.togetherLogs?.id)
                                    context!!.startActivity(intent)
                                }
                            })
                            id_mysport_big.addView(view)
                        }
                    } else {
                        Show_toast.showText(activity, "mysports false")
                    }
                }

            })
    }

    private fun init_banner() {
        mainModel.mainbannergetad(functionClass.getToken())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<String>>> {
                override fun onFailure(call: Call<Result<ArrayList<String>>>, t: Throwable) {
                    println("失败" + t.toString())
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<String>>>,
                    response: Response<Result<ArrayList<String>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result
                        id_banner_sport.setViewUrls(activity, list, null)
                    } else {
                        Toast.makeText(activity, "bannner flag false", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    private var mAdapter: TabAdapter? = null
    private fun init_horline() {

        mainModel.selectAllGroundingSports()
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_type_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_type_item>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_type_item>>>,
                    response: Response<Result<ArrayList<bean_type_item>>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result
                        java_util().addliandongline(horizontalScrollview, list, activity)
                        horizontalScrollview.setOnItemClickListerter { adapter, view, position ->
                            viewpager.setCurrentItem(position, false)
                        }

                        val list_fragment: MutableList<com.top.kjb.adapter.wuxianhuadongtab.BaseFragment> =
                            java.util.ArrayList()
                        for (i in 0..list?.size!! - 1) {
                            list_fragment.add(GoodsFragment.getInstance(list.get(i).sportsId))
                        }

                        mAdapter = TabAdapter(activity?.getSupportFragmentManager(), list_fragment)
                        viewpager.adapter = mAdapter
                        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {

                            }

                            override fun onPageSelected(position: Int) {
                                horizontalScrollview.setScrollPosition(position)
                            }

                            override fun onPageScrollStateChanged(state: Int) {}
                        })

                    } else {
                        Show_toast.showText(activity, "获取运动分类失败")
                    }
                }

            })


    }


    override fun init_view() {
        super.init_view()
//        top_text.setTypeface(Typeface.createFromAsset(activity?.assets, "fonts/use_one.ttf"));
//        id_myclub_text.setTypeface(Typeface.createFromAsset(activity?.assets, "fonts/use_one.ttf"));

        val layoutmanager: LinearLayoutManager = object : LinearLayoutManager(context) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        layoutmanager.orientation = LinearLayoutManager.HORIZONTAL
        layoutmanager.reverseLayout = true
        id_RecyclerView_myclub_head.setLayoutManager(layoutmanager)
    }

    override fun init_click() {
        super.init_click()
        id_click_request.setOnClickListener(this)
        id_click_more_zixun.setOnClickListener(this)
        id_click_club.setOnClickListener(this)
        id_click_bisai.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_club -> {
                var intent = Intent(activity, Club_activity::class.java)
                startActivity(intent)
            }
            R.id.id_click_more_zixun -> {
                var intent = Intent(activity, More_zixun::class.java)
                startActivity(intent)
            }
            R.id.id_click_request -> {
                var intent = Intent(activity, request_sports::class.java)
                startActivity(intent)
            }
        }
    }
}