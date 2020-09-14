package com.top.kjb.tabfragment.fragmentthree_view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.adapter.adapter_user_attention_fans
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_attention_fans
import com.top.kjb.bean.user_info
import com.top.kjb.model.ThreeModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentthree_attention.*
import retrofit2.Call
import retrofit2.Response

class fragmentthree_attention : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmentthree_attention)
        registerBoradcastReceiver()
        init_intent()
        init_refre()
        init_view()
        init_click()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.attent_goto)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.attent_goto -> {
                    var userid = intent.getIntExtra("userid", 0)
                    guanzhuto(userid)
                }

            }
        }
    }


    override fun init_view() {
        super.init_view()
        id_top.findViewById<TextView>(R.id.top_text).setText(title)
        if (title.equals("关注")) {
            init_attion()
        } else {
            init_fan()
        }
    }

    var title = ""
    override fun init_intent() {
        super.init_intent()
        title = intent.getStringExtra("title")
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    val threeModel: ThreeModel by lazy { ThreeModel() }
    lateinit var list: ArrayList<bean_attention_fans>
    lateinit var adapter: adapter_user_attention_fans
    private fun init_fan() {
        id_attion_view_back.visibility = View.GONE
        threeModel.userselectAllFansByUserId(functionClass.getToken(), functionClass.getUserId())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_attention_fans>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_attention_fans>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                    try {
                        id_fans_view_back.visibility = View.VISIBLE
                        list.clear()
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_attention_fans>>>,
                    response: Response<Result<ArrayList<bean_attention_fans>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list = response?.body()?.result!!
                        if (list.size == 0) {
                            id_fans_view_back.visibility = View.VISIBLE
                        } else {
                            id_fans_view_back.visibility = View.GONE
                            adapter =
                                adapter_user_attention_fans(this@fragmentthree_attention, list)
                            id_RecyclerView.adapter = adapter
                        }

                    }
                }

            })
    }

    private fun init_attion() {
        id_fans_view_back.visibility = View.GONE
        threeModel.userselectAllFollowByUserId(functionClass.getToken(), functionClass.getUserId())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_attention_fans>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_attention_fans>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                    try {
                        id_attion_view_back.visibility = View.VISIBLE
                        list.clear()
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()

                    }
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_attention_fans>>>,
                    response: Response<Result<ArrayList<bean_attention_fans>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list = response?.body()?.result!!
                        if (list.size == 0) {
                            id_attion_view_back.visibility = View.VISIBLE
                        } else {
                            id_attion_view_back.visibility = View.GONE
                            adapter =
                                adapter_user_attention_fans(this@fragmentthree_attention, list)
                            id_RecyclerView.adapter = adapter
                        }

                    }
                }

            })
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

    private fun guanzhuto(userId: Int) {
        threeModel.fansbecome_cancel_fans(
            functionClass.getToken(),
            functionClass.getUserId(),
            userId
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var intent = Intent(Sp.attent_gotoed)
                    sendBroadcast(intent)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (title.equals("关注")) {
                            init_attion()
                        } else {
                            init_fan()
                        }
                    } else {
                        Show_toast.showText(this@fragmentthree_attention, "关注失败")
                    }
                }

            })
    }
}