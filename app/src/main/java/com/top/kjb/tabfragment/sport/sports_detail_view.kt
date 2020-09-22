package com.top.kjb.tabfragment.sport

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.top.kjb.R
import com.top.kjb.adapter.adapter_head_sports_detail_userhead
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_sports_list_item
import com.top.kjb.model.SportsModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.tabfragment.chat.chatbag.ChatFragment
import com.top.kjb.tabfragment.newfragmentone.fragmentone_newdetail
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.functionClass.getTime_ms
import kotlinx.android.synthetic.main.layout_sports_detail_view.*
import retrofit2.Call
import retrofit2.Response

class sports_detail_view : BaseActivity(), View.OnClickListener {
    var mChatFragment: ChatFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sports_detail_view)
        init_intent()
        init_click()
        init_refre()
        init_chat()

    }

    override fun onResume() {
        super.onResume()
        init_data()
    }

    var id = 0
    override fun init_intent() {
        super.init_intent()
        id = intent.getIntExtra("id", 0)
    }

    var flag_in = 0
    val sportsModel: SportsModel by lazy { SportsModel() }
    private fun init_data() {
        sportsModel.togetherLogsearchTogetherLogById(
            functionClass.getToken(),
            functionClass.getUserId(),
            id
        )
            .enqueue(object : retrofit2.Callback<Result<bean_sports_list_item>> {
                override fun onFailure(
                    call: Call<Result<bean_sports_list_item>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<Result<bean_sports_list_item>>,
                    response: Response<Result<bean_sports_list_item>>
                ) {
                    var bean = response.body()
                    if ("success".equals(bean?.flag)) {
                        id_name.setText(bean?.result?.togetherLogs?.name)
                        id_gym_name.setText(bean?.result?.gymnasiumName)
                        id_show_number.setText("本场参与人数(" + bean?.result?.togetherLogs?.nowPeople + "/" + bean?.result?.togetherLogs?.maxPeople + ")")
                        var userinfo = bean?.result?.userInfo
                        var adapter =
                            adapter_head_sports_detail_userhead(this@sports_detail_view, userinfo!!)
                        id_RecyclerView_user_head.adapter = adapter

                        var str = ""
                        val start_time =
                            getTime_ms(bean?.result?.togetherLogs?.time.toString(), "MM月dd日HH:mm")
                        val end_time =
                            getTime_ms(bean?.result?.togetherLogs?.endTime.toString(), "HH:mm")
                        var chazhi = ""
                        try {
                            val a: Long =
                                (bean?.result?.togetherLogs?.endTime?.toLong()!! - bean?.result?.togetherLogs?.time?.toLong()!!) / 3600000
                            chazhi = a.toString() + ""
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        str = start_time + " - " + end_time + " (" + chazhi + "小时)"
                        id_show_time.setText(str)
                        id_address_text.setText(bean?.result?.gymnasiumAddress)
                        gymID = bean?.result?.togetherLogs?.gymnasiumId!!
                        flag_in = bean?.result?.isJoin!!
                        if (flag_in == 0) {
                            id_click_join_big.visibility = View.VISIBLE
                        } else {
                            id_click_join_big.visibility = View.GONE
                        }
                    } else {

                    }
                }

            })
    }

    private fun init_chat() {

        val chatInfo = ChatInfo()
        chatInfo.type = V2TIMConversation.V2TIM_GROUP
        chatInfo.id = "@TGS#22OUOVWGZ"
        chatInfo.chatName = "聊天室"

        var bundle = Bundle()
        bundle.putSerializable("chatInfo", chatInfo)
        mChatFragment = ChatFragment()
        mChatFragment!!.arguments = bundle
        supportFragmentManager.beginTransaction()
            .add(R.id.empty_view, mChatFragment!!)
            .commit()
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        layoutmanager.orientation = LinearLayoutManager.HORIZONTAL
        id_RecyclerView_user_head.layoutManager = layoutmanager
    }

    override fun init_click() {
        super.init_click()
        topTab.findViewById<View>(R.id.id_back).setOnClickListener(this)
        id_click_join.setOnClickListener(this)
        id_click_look.setOnClickListener(this)
    }

    var gymID = 0
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_look -> {
                val intent = Intent(
                    this,
                    fragmentone_newdetail::class.java
                )
                intent.putExtra("id", gymID)
                startActivity(intent)
            }
            R.id.id_click_join -> {
                var intent = Intent(this, joinadd_page::class.java)
                intent.putExtra("id", id)
                startActivity(intent)

            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }
}