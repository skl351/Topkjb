package com.top.kjb.tabfragment.newfragmentone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.top.kjb.R
import com.top.kjb.adapter.adapter_head_hor
import com.top.kjb.adapter.adapter_user_comment
import com.top.kjb.adapter.adapter_vip_hor_head
import com.top.kjb.bean.*
import com.top.kjb.customview.dialog.selectlocatonitem_dialog
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.tabfragment.fragmentone_view.fragmentone_place_info
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.SpacesItemDecoration
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentone_newdetail_view.*
import retrofit2.Call
import retrofit2.Response


class fragmentone_newdetail : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmentone_newdetail_view)
        ImmersionBar.with(this).init()
        init_view()
        init_intent()
        init_click()
        init_refre()
        init_data()
    }

    override fun init_view() {
        super.init_view()

    }

    val mainModel: MainModel by lazy { MainModel() }
    var gym_title = ""
    private fun init_data() {
        mainModel.gymnasiumselectGymDetailPage(functionClass.getToken(), id)
            .enqueue(object : retrofit2.Callback<Result<bean_main_item_newdetail>> {
                override fun onFailure(
                    call: Call<Result<bean_main_item_newdetail>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_main_item_newdetail>>,
                    response: Response<Result<bean_main_item_newdetail>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (bean?.result?.gymBgImg?.size != 0) {
                            id_banner.setViewUrls(
                                this@fragmentone_newdetail,
                                bean?.result?.gymBgImg,
                                null
                            )

                        }


                        id_place_name.setText(bean?.result?.gymName)
                        id_star_view.rating = bean?.result?.gymScore!!
                        gym_title = bean?.result?.gymName.toString()
                        id_show_score.setText("" + bean?.result?.gymScore!!);
                        id_years.setText(bean?.result?.openYear + "年");
                        id_all_area.setText("总面积" + bean?.result.area + "m²");
                        id_all_peoples.setText("容纳" + bean?.result.seatingCapacity + "人");
                        id_qixie.setText("器械" + bean?.result.instrumentTap + "种");
                        id_place_address.setText(bean?.result?.address)
                        id_place_address2.setText(bean?.result?.specificAddress)
                        phone = bean?.result?.tel.toString()
                        id_vip_number.setText(bean?.result?.vipNum + "人")
                        try {
                            id_time_long.setText(
                                bean?.result.businessHours.get(0) + "  " + bean?.result.businessHours.get(
                                    1
                                )
                            );
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        var adapter = adapter_vip_hor_head(
                            this@fragmentone_newdetail,
                            bean?.result?.vipHeadImgList!!
                        )
                        id_RecyclerView_vip.adapter = adapter

                        var adapter2 =
                            adapter_head_hor(this@fragmentone_newdetail, bean?.result?.coachList!!)
                        id_RecyclerView.adapter = adapter2

                    } else {
                        Show_toast.showText(this@fragmentone_newdetail, "")
                    }
                }

            })

    }

    var id = 0
    override fun init_intent() {
        super.init_intent()
        id = intent.getIntExtra("id", 0)
        try {
            lat = intent.getStringExtra("lat")
            lng = intent.getStringExtra("lng")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun init_refre() {
        id_RecyclerView_vip.addItemDecoration(SpacesItemDecoration(-10))
        val layoutmanager = LinearLayoutManager(this)
        layoutmanager.orientation = LinearLayoutManager.HORIZONTAL
        id_RecyclerView?.layoutManager = layoutmanager
        val layoutmanager2 = LinearLayoutManager(this)
        layoutmanager2.orientation = LinearLayoutManager.HORIZONTAL
        id_RecyclerView_vip?.layoutManager = layoutmanager2
    }

    override fun init_click() {
        super.init_click()
        id_click_phone.setOnClickListener(this)
        id_location_goto.setOnClickListener(this)
        id_click_back.setOnClickListener(this)
        id_click_place_info.setOnClickListener(this)
    }

    var phone = ""
    var lat = ""
    var lng = ""
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_place_info -> {
                return
                var intent = Intent(this, fragmentone_place_info::class.java)
                startActivity(intent)
            }
            R.id.id_click_back -> {
                onBackPressed()
            }
            R.id.id_location_goto -> {
                val dialog = selectlocatonitem_dialog(this)
                var bean = bean_main_item()
                bean.lat = lat
                bean.lng = lng
                bean.name = gym_title
                bean.gDescribe = ""
                dialog.setbeango(bean)
                XPopup.Builder(this)
                    .hasShadowBg(false)
                    .atView(id_location_goto)
                    .asCustom(dialog)
                    .show()
            }
            R.id.id_click_phone -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }
    }
}