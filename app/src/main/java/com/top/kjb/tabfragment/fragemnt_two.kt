package com.top.kjb.tabfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentone.*
import kotlinx.android.synthetic.main.layout_fragmentone.id_RecyclerView
import kotlinx.android.synthetic.main.layout_fragmenttwo.*
import retrofit2.Call
import retrofit2.Response

class fragemnt_two : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmenttwo, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init_click()
        init_refre()
        init_view()
        init_list()

    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    lateinit var list_3he1: ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>
    lateinit var adapter: adapter_twopage


    val twoModel: TwoModel by lazy { TwoModel() }
    var currentpage = 1
    var pagesize = 10
    fun init_list() {
        list_3he1 = ArrayList<bean_twopage_item_3he1.bean_twopage_item_3he1_item>()
        list_3he1.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list_3he1.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list_3he1.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list_3he1.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        list_3he1.add(bean_twopage_item_3he1.bean_twopage_item_3he1_item())
        adapter = adapter_twopage(activity!!, list_3he1)
        id_RecyclerView.adapter = adapter
        return
        twoModel.twoselectAllCircle(functionClass.getToken(), currentpage, pagesize)
            .enqueue(object : retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(call: Call<Result<bean_twopage_item_3he1>>, t: Throwable) {
                    println("失败" + t.toString())
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


                }

            })


    }

    override fun init_view() {
        super.init_view()

    }

    override fun init_click() {
        super.init_click()
        id_click_one.setOnClickListener(this)
        id_click_two.setOnClickListener(this)
        id_click_three.setOnClickListener(this)
        id_click_four.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_one -> {
                id_click_one_text.setTextColor(resources.getColor(R.color.color_333333))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_id_click_one_img.visibility=View.VISIBLE
                id_click_two_img.visibility=View.INVISIBLE
                id_click_three_img.visibility=View.INVISIBLE
                id_click_four_img.visibility=View.INVISIBLE

                init_list()
            }
            R.id.id_click_two -> {
                id_click_one_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_333333))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_id_click_one_img.visibility=View.INVISIBLE
                id_click_two_img.visibility=View.VISIBLE
                id_click_three_img.visibility=View.INVISIBLE
                id_click_four_img.visibility=View.INVISIBLE
                init_list()
            }
            R.id.id_click_three -> {
                id_click_one_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_333333))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_id_click_one_img.visibility=View.INVISIBLE
                id_click_two_img.visibility=View.INVISIBLE
                id_click_three_img.visibility=View.VISIBLE
                id_click_four_img.visibility=View.INVISIBLE
                init_list()
            }
            R.id.id_click_four -> {
                id_click_one_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_two_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_three_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                id_click_four_text.setTextColor(resources.getColor(R.color.color_333333))
                id_id_click_one_img.visibility=View.INVISIBLE
                id_click_two_img.visibility=View.INVISIBLE
                id_click_three_img.visibility=View.INVISIBLE
                id_click_four_img.visibility=View.VISIBLE
                init_list()
            }

        }
    }
}