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
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmetthree.*
import retrofit2.Call
import retrofit2.Response

class fragment_three : BaseFragment(), View.OnClickListener {
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
        init_click()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.loginsuccess)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.loginsuccess -> {
                    init_data()
                }

            }
        }
    }

    private fun init_data() {
        if (!functionClass.islogin()) {
            return
        }
        threeModel.usergetUserCenter(functionClass.getToken())
            .enqueue(object : retrofit2.Callback<Result<user_info>> {
                override fun onFailure(call: Call<Result<user_info>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<user_info>>,
                    response: Response<Result<user_info>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        ImageLoader.getInstance()
                            .displayImage(bean?.result?.headImg, id_user_headimg)
                        id_user_name.setText(bean?.result?.username)
                        id_dynamicNum.setText(bean?.result?.dynamicNum)
                        id_collectionNum.setText(bean?.result?.collectionNum)
                        id_fansNum.setText(bean?.result?.fansNum)
                        id_followNum.setText(bean?.result?.followNum)
                    }
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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
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
                startActivity(intent)
            }
            R.id.id_click_fans -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, fragmentthree_attention::class.java)
                startActivity(intent)
            }
            R.id.id_click_collection -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }

            }
            R.id.id_click_dynamic -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }
}