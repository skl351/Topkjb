package com.top.kjb.tabfragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmetthree.*
import retrofit2.Call
import retrofit2.Response

class fragment_three : BaseFragment(), View.OnClickListener ,OnRefreshListener{
    val threeModel: ThreeModel by lazy { ThreeModel() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmetthree, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBoradcastReceiver()
        init_view()
        init_refre()
        init_click()
        init_data()
    }
    private fun init_refre() {

        refreshLayout.setOnRefreshListener(this)
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.loginoutsuccess)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
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
        threeModel.usergetUserCenter(functionClass.getToken(),functionClass.getUserId())
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
                        if("".equals(bean?.result?.headImg)){
                            id_user_headimg.setImageResource(R.mipmap.icon_defult_head)
                        }else{
                            ImageLoader.getInstance()
                                .displayImage(bean?.result?.headImg, id_user_headimg)
                        }

                        if("".equals(bean?.result?.username)){
                            id_user_name.setText("暂未登录")
                        }else{
                            id_user_name.setText(bean?.result?.username)
                        }
                        if("".equals(bean?.result?.motto)){
                            id_user_summary.setText("快来一起加入我们吧")
                        }else{
                            id_user_summary.setText(bean?.result?.motto)
                        }

                        if (bean?.result?.dynamicNum==0){
                            id_dynamicNum.setText("0")
                        }else{
                            id_dynamicNum.setText(bean?.result?.dynamicNum.toString())
                        }
                        if (bean?.result?.collectionNum==0){
                            id_collectionNum.setText("0")
                        }else{
                            id_collectionNum.setText(bean?.result?.collectionNum.toString())
                        }
                        if (bean?.result?.fansNum==0){
                            id_fansNum.setText("0")
                        }else{
                            id_fansNum.setText(bean?.result?.fansNum.toString())
                        }
                        if (bean?.result?.followNum==0){
                            id_followNum.setText("0")
                        }else{
                            id_followNum.setText(bean?.result?.followNum.toString())
                        }
                    }else{
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
        id_click_cikajilu.setOnClickListener(this)
        id_click_order_jilu.setOnClickListener(this)
        id_click_vip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_vip->{
                var intent=Intent(activity, top_vip::class.java)
                startActivity(intent)
            }
            R.id.id_click_order_jilu->{
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent=Intent(activity,my_order_list::class.java)
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

            R.id.id_click_cikajilu -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity!!, fragmentthree_cikajilu::class.java)
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
                intent.putExtra("title","关注")
                startActivity(intent)
            }
            R.id.id_click_fans -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_attention::class.java)
                intent.putExtra("title","粉丝")
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