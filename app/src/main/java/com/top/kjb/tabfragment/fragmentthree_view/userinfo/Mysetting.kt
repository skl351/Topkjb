package com.top.kjb.tabfragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.top.kjb.R
import com.top.kjb.customview.MyclearDialog
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.tabfragment.fragmentthree_view.userinfo.mysettingactivity.informationAndtixing
import com.top.kjb.tabfragment.fragmentthree_view.userinfo.mysettingactivity.xieyiAndtiaokuan
import com.top.kjb.utils.CacheDataManager
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_mysetting.*

class Mysetting : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mysetting)
        init_view()
        init_click()
    }

    override fun init_view() {
        super.init_view()
        id_cache.text = CacheDataManager.getTotalCacheSize(this)
    }

    override fun init_click() {
        super.init_click()
        id_click_myselfinfoseting.setOnClickListener(this)
        id_click_safe.setOnClickListener(this)
        id_click_cleardata.setOnClickListener(this)
        id_click_loginout.setOnClickListener(this)
        id_click_xieyiandtiaokuan.setOnClickListener(this)
        id_click_information.setOnClickListener(this)
        id_click_information.setOnClickListener(this)
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_information -> {
                val intent = Intent(this, informationAndtixing::class.java)
                startActivity(intent)
            }
            R.id.id_click_xieyiandtiaokuan -> {
                val intent = Intent(this, xieyiAndtiaokuan::class.java)
                startActivity(intent)
            }
            R.id.id_click_loginout -> {
                functionClass.setuserinfo(0,"","","","","")
                Show_toast.showText(this, "退出登录成功")
                var intent = Intent(Sp.loginoutsuccess)
                sendBroadcast(intent)
                finish()
            }
            R.id.id_click_cleardata -> {
                var thred = Thread({
                    try {
                        CacheDataManager.clearAllCache(this)
                        if (CacheDataManager.getTotalCacheSize(this).startsWith("0")) {
                            handlerCache.sendEmptyMessage(0)
                        }
                    } catch (e: Exception) {

                    }
                })
                thred.start()

            }
            R.id.id_click_information -> {

            }
            R.id.id_back -> {
                onBackPressed()
            }
            R.id.id_click_myselfinfoseting -> {
                var intent = Intent(this, Myselfinfo::class.java)
                startActivity(intent)
            }
            R.id.id_click_safe -> {
                var intent = Intent(this, Mysafety::class.java)
                startActivity(intent)
            }
        }
    }

    //缓存清理的handler
    val handlerCache = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            when (msg.what) {
                0 -> {
                    var clearDialog = MyclearDialog(this@Mysetting)
                    clearDialog.show()
                    id_cache.text = CacheDataManager.getTotalCacheSize(this@Mysetting)

                }
            }
        }
    }
}