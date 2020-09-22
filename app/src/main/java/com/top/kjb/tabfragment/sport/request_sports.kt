package com.top.kjb.tabfragment.sport

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.lxj.xpopup.XPopup
import com.top.kjb.R
import com.top.kjb.adapter.adapter_sendsport_typelist
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_type_item
import com.top.kjb.customview.dialog.sports_address_bottom
import com.top.kjb.model.MainModel
import com.top.kjb.model.SportsModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_request_sports.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class request_sports : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_request_sports)
        registerBoradcastReceiver()
        init_refre()
        init_click()
        initTimePicker()
        initTimePicker2()
        init_data()
        init_changdi()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.select_bottom_address)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.select_bottom_address -> {
                    var id = intent.getIntExtra("id", 0)
                    var name = intent.getStringExtra("name")
                    gymnasiumId = id
                    id_select_gym.setText(name)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }

    var list_address = ArrayList<bean_main_item>()
    private fun init_changdi() {
        sportsModel.bottom_address(functionClass.getToken(), Sp.lat, Sp.lng)
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    response: Response<Result<ArrayList<bean_main_item>>>
                ) {

                    var bean = response.body()
                    if ("success".equals(bean?.flag)) {
                        list_address = bean?.result!!
                    }


                }

            })
    }


    lateinit var list_data: ArrayList<bean_type_item>
    lateinit var adapter: adapter_sendsport_typelist
    val mainModel: MainModel by lazy { MainModel() }
    val sportsModel: SportsModel by lazy { SportsModel() }
    private fun init_data() {
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
                    var bean = response.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result
                        list?.removeAt(0)
                        list_data = list!!
                        adapter = adapter_sendsport_typelist(this@request_sports, list_data)
                        id_RecyclerView.adapter = adapter
                    } else {
                        Show_toast.showText(this@request_sports, "获取顶部分类失败")
                    }
                }

            })
    }

    private fun init_refre() {
        val layoutmanager = GridLayoutManager(this, 4)
        id_RecyclerView.setLayoutManager(layoutmanager)
    }


    var data1 = 0L
    var s_date = ""
    lateinit var now_Calendar: Calendar
    private var pvTime: TimePickerView? = null
    private fun initTimePicker() {//Dialog 模式下，在底部弹出
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(
            startDate.get(Calendar.YEAR) + 1, startDate.get(Calendar.MONTH), startDate.get(
                Calendar.DATE
            )
        )
        pvTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
            data1 = date.time
            s_date = functionClass.getTime(date, "yyyy/MM/dd HH:mm:00")
            id_start_time.text = s_date
            now_Calendar = Calendar.getInstance()
            now_Calendar.time = date
        }).setRangDate(startDate, endDate)
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
            .build()
    }

    var data2 = 0L
    var e_date = ""
    private var pvTime2: TimePickerView? = null
    private fun initTimePicker2() {//Dialog 模式下，在底部弹出
        var startDate: Calendar
        try {
            startDate = now_Calendar
        } catch (e: Exception) {
            e.printStackTrace()
            startDate = Calendar.getInstance()
        }


        val endDate = Calendar.getInstance()
        endDate.set(
            startDate.get(Calendar.YEAR) + 1, startDate.get(Calendar.MONTH), startDate.get(
                Calendar.DATE
            )
        )
        pvTime2 = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
            data2 = date.time
            e_date = functionClass.getTime(date, "yyyy/MM/dd HH:mm:00")
            id_end_time.text = e_date
        }).setRangDate(startDate, endDate)
            .setType(booleanArrayOf(true, true, true, true, true, false))
            .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
            .build()
    }

    override fun init_click() {
        super.init_click()
        id_click_select_time.setOnClickListener(this)
        id_click_select_endtime.setOnClickListener(this)
        id_back.setOnClickListener(this)
        id_click_send.setOnClickListener(this)
        id_click_select_gym.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_select_gym -> {

                var bottom = sports_address_bottom(this)
                bottom.setdatalist(list_address)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_click_send -> {
                send_request()
            }
            R.id.id_back -> {
                onBackPressed()
            }
            R.id.id_click_select_time -> {
                pvTime?.show()
            }
            R.id.id_click_select_endtime -> {
                if (id_start_time.text.toString().equals("")) {
                    Show_toast.showText(this, "请选择开始时间")
                    return
                }
                pvTime2?.show()
            }
        }
    }

    var sportId = 0
    var gymnasiumId = 1//
    var maxPeople = ""
    var nowPeople = "1"
    var time = ""
    var endTime = ""
    var name = ""
    private fun send_request() {
        if (id_edit_name.text.toString().equals("")) {
            Show_toast.showText(this, "请输入此次运动名称")
            return
        } else {
            name = id_edit_name.text.toString()
        }
        sportId = list_data.get(adapter.getSelectPostion()).sportsId

        if (id_input_maxnumber.text.toString().equals("") || id_input_maxnumber.text.toString().equals(
                "0"
            )
        ) {
            Show_toast.showText(this, "请输入人数")
            return
        } else {
            maxPeople = id_input_maxnumber.text.toString()
        }
        if (id_start_time.text.toString().equals("")) {
            Show_toast.showText(this, "请选择开始时间")
            return
        } else {
            time = id_start_time.text.toString()
        }
        if (id_end_time.text.toString().equals("")) {
            Show_toast.showText(this, "请选择结束时间")
            return
        } else {
            endTime = id_end_time.text.toString()
        }
        if (id_select_gym.text.toString().equals("")) {
            Show_toast.showText(this, "请选择场馆")
            return
        } else {
            endTime = id_end_time.text.toString()
        }


        println("send_request" + sportId + "," + maxPeople + "," + time + "," + endTime + "," + name)
//        return
        sportsModel.togetherLoginsertTogetherLog(
            functionClass.getToken(),
            functionClass.getUserId(),
            functionClass.getUserId(),
            sportId,
            time,
            endTime,
            gymnasiumId,
            name,
            maxPeople,
            nowPeople
        ).enqueue(object : retrofit2.Callback<Result<String>> {
            override fun onFailure(call: Call<Result<String>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<Result<String>>,
                response: Response<Result<String>>
            ) {
                Show_toast.showText(this@request_sports, "发起运动成功")
                var intent = Intent(Sp.sport_send)
                sendBroadcast(intent)
                finish()

            }

        })
    }
}