package com.top.kjb.tabfragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.top.kjb.R
import com.top.kjb.Userabout.LoginActivity
import com.top.kjb.bean.Result
import com.top.kjb.bean.user_info
import com.top.kjb.model.ThreeModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.fragmentthree_view.Information
import com.top.kjb.tabfragment.fragmentthree_view.fragmentthree_attention
import com.top.kjb.tabfragment.fragmentthree_view.fragmentthree_cikajilu
import com.top.kjb.tabfragment.fragmentthree_view.fragmentthree_user_center
import com.top.kjb.tabfragment.fragmentthree_view.orderlist.my_order_list
import com.top.kjb.tabfragment.fragmentthree_view.vip.top_vip
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmetthree.*
import retrofit2.Call
import retrofit2.Response

class fragment_three : BaseFragment(), View.OnClickListener, OnRefreshListener ,
    SensorEventListener {
    val threeModel: ThreeModel by lazy { ThreeModel() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmetthree, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBoradcastReceiver()
        id_top_bar_3.layoutParams.height = functionClass.getbarHight(activity!!)
        init_step()
        init_view()
        init_refre()
        init_click()
        init_data()
    }
    private fun init_step() {
        if (isSupportStep()) {
            println("支持记录步数")
            val sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        } else {
            println("不支持记录步数")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.getType() == Sensor.TYPE_STEP_COUNTER) {
            println("onSensorChanged: 当前步数：" + event.values[0])

            var str = functionClass.getTime_ms(System.currentTimeMillis().toString(), "yyyy-MM-dd")
//            if (functionClass.getStep() == 0) {
//                //第一次app集成
//                functionClass.setStep(event.values[0].toInt())
//            }
            if (functionClass.getcurStep(str) == 0) {
                //当天第一次
                functionClass.setcurStep(str, event.values[0].toInt())
                id_step.setText("0")
                println("当天1：" + event.values[0])
            } else {
                //当天不是第一次
                var cur_step = event.values[0].toInt() - functionClass.getcurStep(str)
               var baifenbi= (cur_step.toFloat()/4000)*100
                print("=-=-=-"+baifenbi.toInt())
                circle_progress_bar3.value= baifenbi.toFloat()
                id_step.setText(cur_step.toString())

                println("当天2：" +cur_step)
            }

        }
    }

    /**
     * 校验是否支持记步
     *
     * @return
     */
    fun isSupportStep(): Boolean {
        if (activity?.application == null) {
            Show_toast.showText(activity, "StepSensorManager使用前需要提前初始化")
        }
        val packageManager = activity?.application?.packageManager
        return packageManager?.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)!! || packageManager.hasSystemFeature(
            PackageManager.FEATURE_SENSOR_STEP_DETECTOR
        )
    }
    private fun init_refre() {

        refreshLayout.setOnRefreshListener(this)
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.loginoutsuccess)
        intentFilter.addAction(Sp.attent_gotoed)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }


    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {

                Sp.attent_gotoed->{
                    init_data()
                }
                Sp.loginoutsuccess -> {
                    init_data()
                }

            }
        }
    }

    private fun init_data() {
        if (!functionClass.islogin()) {
            id_user_name.setText("暂未登录")
            id_user_summary.setText("快来一起加入我们吧")
            id_dynamicNum.setText("0")
            id_collectionNum.setText("0")
            id_fansNum.setText("0")
            id_followNum.setText("0")
            id_user_headimg.setImageResource(R.mipmap.icon_defult_head)
            refreshLayout.finishRefresh()
            return
        }
        threeModel.usergetUserCenter(functionClass.getToken(), functionClass.getUserId())
            .enqueue(object : retrofit2.Callback<Result<user_info>> {
                override fun onFailure(call: Call<Result<user_info>>, t: Throwable) {
                    id_user_name.setText("暂未登录")
                    id_user_summary.setText("快来一起加入我们吧")
                    id_dynamicNum.setText("0")
                    id_collectionNum.setText("0")
                    id_fansNum.setText("0")
                    id_followNum.setText("0")
                    id_user_headimg.setImageResource(R.mipmap.icon_defult_head)
                    refreshLayout.finishRefresh()
                }

                override fun onResponse(
                    call: Call<Result<user_info>>,
                    response: Response<Result<user_info>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if ("".equals(bean?.result?.headImg)) {
                            id_user_headimg.setImageResource(R.mipmap.icon_defult_head)
                        } else {
                            ImageLoader.getInstance()
                                .displayImage(bean?.result?.headImg, id_user_headimg)
                        }

                        if ("".equals(bean?.result?.username)) {
                            id_user_name.setText("暂未登录")
                        } else {
                            id_user_name.setText(bean?.result?.username)
                        }
                        if ("".equals(bean?.result?.motto)) {
                            id_user_summary.setText("快来一起加入我们吧")
                        } else {
                            id_user_summary.setText(bean?.result?.motto)
                        }

                        if (bean?.result?.dynamicNum == 0) {
                            id_dynamicNum.setText("0")
                        } else {
                            id_dynamicNum.setText(bean?.result?.dynamicNum.toString())
                        }
                        if (bean?.result?.collectionNum == 0) {
                            id_collectionNum.setText("0")
                        } else {
                            id_collectionNum.setText(bean?.result?.collectionNum.toString())
                        }
                        if (bean?.result?.fansNum == 0) {
                            id_fansNum.setText("0")
                        } else {
                            id_fansNum.setText(bean?.result?.fansNum.toString())
                        }
                        if (bean?.result?.followNum == 0) {
                            id_followNum.setText("0")
                        } else {
                            id_followNum.setText(bean?.result?.followNum.toString())
                        }
                    } else {
                        id_user_name.setText("暂未登录")
                        id_user_summary.setText("快来一起加入我们吧")
                        id_dynamicNum.setText("0")
                        id_collectionNum.setText("0")
                        id_fansNum.setText("0")
                        id_followNum.setText("0")
                        id_user_headimg.setImageResource(R.mipmap.icon_defult_head)
                    }
                    refreshLayout.finishRefresh()
                }

            })
    }

    override fun init_view() {
        super.init_view()

    }

    override fun init_click() {
        super.init_click()
        id_click_information.setOnClickListener(this)
        id_click_dynamic.setOnClickListener(this)
        id_click_collection.setOnClickListener(this)
        id_click_fans.setOnClickListener(this)
        id_click_follow.setOnClickListener(this)
        id_click_user_info.setOnClickListener(this)
        id_click_setting.setOnClickListener(this)
        id_click_vip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_vip -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, top_vip::class.java)
                startActivity(intent)
            }

            R.id.id_click_information -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, Information::class.java)
                startActivity(intent)
            }

            R.id.id_click_setting -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, Mysetting::class.java)
                startActivity(intent)
            }
            R.id.id_click_user_info -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_user_center::class.java)
                startActivity(intent)
            }
            R.id.id_click_follow -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_attention::class.java)
                intent.putExtra("title", "关注")
                startActivity(intent)
            }
            R.id.id_click_fans -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_attention::class.java)
                intent.putExtra("title", "粉丝")
                startActivity(intent)
            }
            R.id.id_click_collection -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_user_center::class.java)
                startActivity(intent)

            }
            R.id.id_click_dynamic -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_user_center::class.java)
                startActivity(intent)

            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        init_data()
    }
}