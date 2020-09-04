package com.top.kjb.tabfragment.newfragmentone

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_coach_item
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_coach_detail.*
import retrofit2.Call
import retrofit2.Response

class coach_detail : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_coach_detail)
        ImmersionBar.with(this).init()
        init_intent()
        init_view()
        init_click()

        init_data()
    }

    override fun init_view() {
        super.init_view()
        println(""+functionClass.getlike(uid))
        if (functionClass.getlike(uid)) {
            id_like_love.setImageResource(R.mipmap.icon_love_on)
            id_like_text.setTextColor(Color.RED)
        } else {
            id_like_love.setImageResource(R.mipmap.icon_love_off)
            id_like_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
        }
    }

    var uid = 0
    override fun init_intent() {
        super.init_intent()
        uid = intent.getIntExtra("uid", 0)
    }

    val mainModel: MainModel by lazy { MainModel() }
    private fun init_data() {
        mainModel.coachselectCoach(functionClass.getToken(), uid)
            .enqueue(object : retrofit2.Callback<Result<bean_coach_item>> {
                override fun onFailure(call: Call<Result<bean_coach_item>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<bean_coach_item>>,
                    response: Response<Result<bean_coach_item>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (bean?.result?.homepageBgImg?.size != 0) {
                            id_banner.setViewUrls(
                                this@coach_detail,
                                bean?.result?.homepageBgImg,
                                null
                            )

                        }
                        id_teacher_name.setText(bean?.result?.name)
                        id_congye_year.setText("从业" + bean?.result?.workYear + "年")
                        id_num_kec.setText(bean?.result?.cumTeach + "节")
                        var type_text = ""
                        for (i in 0..bean?.result?.tap?.size!! - 1) {
                            type_text += bean?.result?.tap?.get(i)
                            if (i == bean?.result?.tap?.size!! - 1) {
                                break
                            } else {
                                type_text += "  |  "
                            }
                        }
                        id_text1.setText(type_text)
                        id_coach_text.setText(bean?.result?.synopsis)
                        ImageLoader.getInstance()
                            .displayImage(bean?.result?.certificateImg, id_coach_image)
                    } else {
                        Show_toast.showText(this@coach_detail, "")
                    }
                }

            })

    }

    override fun init_click() {
        super.init_click()
        id_click_back.setOnClickListener(this)
        id_click_like.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_like -> {
                if (functionClass.getlike(uid)) {

                    id_like_love.setImageResource(R.mipmap.icon_love_off)
                    id_like_text.setTextColor(resources.getColor(R.color.color_a4a4a4))
                    functionClass.setlike(uid, false)
                } else {
                    id_like_love.setImageResource(R.mipmap.icon_love_on)
                    id_like_text.setTextColor(Color.RED)
                    functionClass.setlike(uid, true)
                }
            }
            R.id.id_click_back -> {
                onBackPressed()
            }

        }
    }

}