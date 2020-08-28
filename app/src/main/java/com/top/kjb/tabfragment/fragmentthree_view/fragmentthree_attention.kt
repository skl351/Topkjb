package com.top.kjb.tabfragment.fragmentthree_view

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
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentthree_attention.*
import retrofit2.Call
import retrofit2.Response

class fragmentthree_attention : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmentthree_attention)
        init_intent()
        init_refre()
        init_view()
        init_click()

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
        threeModel.userselectAllFansByUserId(functionClass.getToken(), functionClass.getUserId())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_attention_fans>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_attention_fans>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
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
        threeModel.userselectAllFollowByUserId(functionClass.getToken(), functionClass.getUserId())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_attention_fans>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_attention_fans>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
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

}