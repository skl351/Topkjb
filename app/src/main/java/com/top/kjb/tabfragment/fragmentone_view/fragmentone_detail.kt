package com.top.kjb.tabfragment.fragmentone_view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.top.kjb.R
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.adapter.adapter_user_comment
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_main_detail_top
import com.top.kjb.bean.bean_main_item_about_xuanliangdiann
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.customview.pay_bottom
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragemntone_detail.*
import retrofit2.Call
import retrofit2.Response

class fragmentone_detail : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragemntone_detail)
        init_intent()
        init_refre()
        init_click()
        init_top()
    }

    lateinit var list: ArrayList<bean_main_item_about_xuanliangdiann.bean_main_item_about_xuanliangdiann_highlightsGymVOList>
    var adapter: adapter_user_comment? = null
    val mainModel: MainModel by lazy { MainModel() }
    private fun init_top() {
        mainModel.gymnasiumgymnasiumDetails(functionClass.getToken(),id)
            .enqueue(object : retrofit2.Callback<Result<bean_main_item_about_xuanliangdiann>> {
                override fun onFailure(
                    call: Call<Result<bean_main_item_about_xuanliangdiann>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_main_item_about_xuanliangdiann>>,
                    response: Response<Result<bean_main_item_about_xuanliangdiann>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var gymnasium = response?.body()?.result?.gymnasiumPageVO
                        if (gymnasium?.gymPhotoList?.size!=0){
                            id_banner.setViewUrls(this@fragmentone_detail, gymnasium?.gymPhotoList, null)
                            id_banner.visibility=View.VISIBLE
                        }else{
                            id_banner.visibility=View.GONE
                        }
                        if (gymnasium?.gymExtension?.size!=0){
                            id_banner2.setViewUrls(this@fragmentone_detail, gymnasium?.gymExtension, null)
                            id_banner2.visibility=View.VISIBLE
                        }else{
                            id_banner2.visibility=View.GONE
                        }
                        top_text.text = gymnasium?.gymName
                        id_place_name.text = gymnasium?.gymName
                        id_place_address.text = gymnasium?.address
                        id_place_address2.text = gymnasium?.specificAddress
                        id_time_long.text = gymnasium?.businessHours
                        id_years.text = gymnasium?.openYear+"年"
                        id_all_area.text = "总面积"+gymnasium?.area+"m²"
                        id_all_peoples.text = "容纳"+gymnasium?.area+"人"
                        id_star_view.rating = gymnasium?.userEvaluate!!
                        id_show_score.setText(gymnasium.userEvaluate.toString())
                        id_all_num_comment.setText(response?.body()?.result?.gymUserEvaluateNum.toString())
                        list = response?.body()?.result?.highlightsGymVOList!!
                        adapter = adapter_user_comment(this@fragmentone_detail, list)
                        id_RecyclerView.adapter = adapter


                    } else {
                        Show_toast.showText(this@fragmentone_detail, "")
                    }
                }

            })
    }

    var id = 0
    override fun init_intent() {
        super.init_intent()
        id = intent.getIntExtra("id", 0)
    }


    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_click() {
        super.init_click()
        id_click_commtent_list.setOnClickListener(this)
        id_click_place_info.setOnClickListener(this)
        id_back.setOnClickListener(this)
        id_click_pay.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_place_info -> {
                var intent = Intent(this, fragmentone_place_info::class.java)
                startActivity(intent)
            }
            R.id.id_click_pay -> {
                var bottom = pay_bottom(this)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .atView(id_click_pay)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_back -> {
                onBackPressed()
            }
            R.id.id_click_commtent_list -> {
                var intent = Intent(this, user_comment_list::class.java)
                startActivity(intent)
            }
        }
    }
}