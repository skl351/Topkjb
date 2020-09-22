package com.top.kjb.tabfragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import com.top.kjb.R
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.chat.layout_chat_one
import com.top.kjb.tabfragment.chat.layout_chat_two
import com.top.kjb.tabfragment.chat.util.GenerateTestUserSig
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmenttwo_new.*
import java.util.logging.Logger


class fragemnt_two_communication : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmenttwo_new, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_top_bar_2.layoutParams.height = functionClass.getbarHight(activity!!)
        registerBoradcastReceiver()
        init_click()
        init_view()

        init_chat_login()

    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.loginoutsuccess -> {
                    init_chat_login()
                }

            }
        }
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.loginoutsuccess)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    private fun init_chat_login() {
        if ("".equals(functionClass.gettel())) {
            TUIKit.logout(object :IUIKitCallBack{
                override fun onSuccess(data: Any?) {
                    println("腾讯云退出登录成功")
                }

                override fun onError(module: String?, errCode: Int, errMsg: String?) {
                    println("腾讯云退出登录失败" + errCode + "," + errMsg)
                }

            })
        }else{
            // 获取userSig函数
            println("腾讯云登录" + functionClass.gettel())
            val userSig: String = GenerateTestUserSig.genTestUserSig(functionClass.gettel())
            TUIKit.login(functionClass.gettel(), userSig, object : IUIKitCallBack {
                override fun onSuccess(data: Any?) {
                    println("腾讯云登录成功")
                    init_viewpage()
                }

                override fun onError(module: String?, errCode: Int, errMsg: String?) {
                    println("腾讯云登录失败" + errCode + "," + errMsg)
                }

            })
        }

    }

    private fun init_viewpage() {
        getfragment()
        init_adapter()
    }

    var mFragments = ArrayList<Fragment>()
    var framgent1: Fragment? = null
    var framgent2: Fragment? = null
    private fun getfragment() {
        mFragments.clear()
        framgent1 = layout_chat_one()
        framgent2 = layout_chat_two()
        mFragments.add(framgent1 as Fragment)
        mFragments.add(framgent2 as Fragment)
    }

    var mAdapter: FragmentPagerAdapter? = null
    var currentIndex: Int = 0
    private fun init_adapter() {
        view_pager_two2.offscreenPageLimit = 1
        mAdapter = object : FragmentPagerAdapter(activity?.supportFragmentManager!!) {
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return mFragments.get(position)
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        view_pager_two2.adapter = mAdapter
        view_pager_two2.addOnPageChangeListener(object :
            androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (currentIndex == 0 && position == 0) {//0->1
                    chagetext(0)

                } else if (currentIndex == 1 && position == 0) {//1->0
                    chagetext(1)

                }


            }

            override fun onPageSelected(position: Int) {
                currentIndex = position
            }

        })

    }


    val twoModel: TwoModel by lazy { TwoModel() }
    var currentpage = 1
    var pagesize = 10


    override fun init_view() {
        super.init_view()
        id_line1.setTextSize(20f)
        id_line2.setTextSize(15f)
    }

    override fun init_click() {
        super.init_click()
        id_line1.setOnClickListener(this)
        id_line2.setOnClickListener(this)
        id_click_liner.setOnClickListener(this)
    }

    var current_item = 0
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_liner -> {
//                var intent = Intent(activity, liner_list::class.java)
//                startActivity(intent)
            }

            R.id.id_line1 -> {
                current_item = 0
                chagetext(current_item)
                view_pager_two2.setCurrentItem(0)

            }
            R.id.id_line2 -> {
                current_item = 1
                chagetext(current_item)
                view_pager_two2.setCurrentItem(1)
            }


        }
    }


    fun chagetext(i: Int) {
        id_line1.setTextSize(15f)
        id_line1.setTextColor(resources.getColor(R.color.color_979797))
        id_line2.setTextSize(15f)
        id_line2.setTextColor(resources.getColor(R.color.color_979797))
        when (i) {
            0 -> {
                id_line1.setTextSize(20f)
                id_line1.setTextColor(resources.getColor(R.color.color_424242))
            }
            1 -> {
                id_line2.setTextSize(20f)
                id_line2.setTextColor(resources.getColor(R.color.color_424242))
            }
        }

    }
}