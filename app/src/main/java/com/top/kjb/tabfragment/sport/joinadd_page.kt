package com.top.kjb.tabfragment.sport

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.top.kjb.R
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_sports_list_item
import com.top.kjb.model.SportsModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util
import kotlinx.android.synthetic.main.layout_joinadd_page_view.*
import retrofit2.Call
import retrofit2.Response

class joinadd_page : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_joinadd_page_view)
        init_intent()
        init_click()
    }

    var id = 0
    override fun init_intent() {
        super.init_intent()
        id = intent.getIntExtra("id", 0)
    }


    override fun init_click() {
        super.init_click()
        id_topbar.findViewById<View>(R.id.id_back).setOnClickListener(this)
        id_click_know.setOnClickListener(this)
    }

    val sportsModel: SportsModel by lazy { SportsModel() }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_know -> {
                sportsModel.togetherLogupdateMyTogetherLog(
                    functionClass.getToken(),
                    functionClass.getUserId(),
                    id,
                    2
                )
                    .enqueue(object : retrofit2.Callback<Result<String>> {
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
                                Show_toast.showText(this@joinadd_page,"加入成功")
                                var intent=Intent(Sp.sport_send)
                                sendBroadcast(intent)
                                finish()

                            } else {

                            }
                        }

                    })
            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }

}