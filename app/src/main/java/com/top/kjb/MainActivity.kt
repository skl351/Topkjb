package com.top.kjb

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.gyf.immersionbar.ImmersionBar
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.*
import com.top.kjb.tabfragment.chat.java_chat
import com.top.kjb.utils.MyLocationListener
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.Sp.status_show
import com.top.kjb.utils.Sp.status_show_dialog
import com.top.kjb.utils.functionClass
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.common.Constant
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).statusBarDarkFont(true).init()
        setContentView(R.layout.activity_main)

        init_click()
        init_view()
        init_viewpage()
        init_loction()
        init_golocation()
        java_chat().login()
    }

    var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.getAction() == KeyEvent.ACTION_DOWN) {

            if (!status_show || status_show_dialog) {
                if (!status_show) {
                    (bottom_tabist?.get(0) as fragment_one).showtop()
                    status_show = !status_show
                }
                if (status_show_dialog) {
                    (bottom_tabist?.get(0) as fragment_one).hide_bottom_location()
                    status_show_dialog = !status_show_dialog
                }

                return true
            }

//            var home = Intent(Intent.ACTION_MAIN);
//            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            home.addCategory(Intent.CATEGORY_HOME);
//            startActivity(home);

            if (System.currentTimeMillis() - exitTime > 2000) {
                Show_toast.showText(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis()
            } else {
                onBackPressed()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    fun init_start() {
        if (mLocationClient?.isStarted!!) {
            mLocationClient?.requestLocation()
        } else {
            mLocationClient?.start()
        }
    }


    open fun init_golocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    Sp.REQUEST_CODE_Location
                )
                return
            } else {
                init_start()
            }
        } else {
            init_start()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        println("" + requestCode + "," + "相关" + grantResults)
        if (requestCode == Sp.REQUEST_CODE_Location) {
            if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                init_start()
            } else {
                Show_toast.showText(this, "用户拒绝定位相关权限")
            }


        }
        if (requestCode == Sp.REQUEST_CODE_Location2) {
            if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                init_start()
            } else {
                Show_toast.showText(this, "用户拒绝定位相关权限")
            }


        }
        if (requestCode == Sp.REQUEST_CODE_camera) {
            if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                var intent = Intent(this, CaptureActivity::class.java)
                startActivityForResult(intent, Sp.REQUEST_CODE_SCAN);
            } else {
                Show_toast.showText(this, "用户拒绝拍照相关权限")
            }

        }
    }


    private fun init_viewpage() {
        id_icon_1.isSelected = true
        id_text_1.isSelected = true
        myViewpage.offscreenPageLimit = 4
        myViewpage.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return bottom_tabist?.get(position)!!
            }

            override fun getCount(): Int {
                return bottom_tabist?.size!!
            }

        }
        myViewpage.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                clickitem(position)
            }
        })
    }

    var bottom_tabist: ArrayList<BaseFragment>? = null
    override fun init_view() {
        super.init_view()
        bottom_tabist = ArrayList()
        val fragment1 = fragment_one()
        val fragment1_sport = fragment_one_sprots()
        val fragment2 = fragmet_two_new()
        val fragment2_communication = fragemnt_two_communication()
        val fragment3 = fragment_three()
        bottom_tabist?.add(fragment1)
        bottom_tabist?.add(fragment1_sport)
        bottom_tabist?.add(fragment2)
        bottom_tabist?.add(fragment2_communication)
        bottom_tabist?.add(fragment3)

        icon_list = ArrayList()
        text_list = ArrayList()
        icon_list.add(id_icon_1)
        icon_list.add(id_icon_1_sport)
        icon_list.add(id_icon_2)
        icon_list.add(id_icon_2_2)
        icon_list.add(id_icon_3)
        text_list.add(id_text_1)
        text_list.add(id_text_1_sport)
        text_list.add(id_text_2)
        text_list.add(id_text_2_2)
        text_list.add(id_text_3)
    }

    lateinit var icon_list: ArrayList<Button>
    lateinit var text_list: ArrayList<TextView>

    fun clickitem(a: Int) {
        for (i in 0..text_list.size - 1) {
            icon_list.get(i).isSelected = false
            text_list.get(i).isSelected = false
        }
        text_list.get(a).isSelected = true
        icon_list.get(a).isSelected = true
    }


    override fun init_click() {
        super.init_click()
        id_icon_1.setOnClickListener(this)
        id_click1.setOnClickListener(this)
        id_icon_1_sport.setOnClickListener(this)
        id_click1_sport.setOnClickListener(this)
        id_icon_2.setOnClickListener(this)
        id_click2.setOnClickListener(this)
        id_click2_2.setOnClickListener(this)
        id_icon_2_2.setOnClickListener(this)
        id_icon_3.setOnClickListener(this)
        id_click3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_icon_1, R.id.id_click1 -> {
                myViewpage.currentItem = 0
            }
            R.id.id_icon_1_sport, R.id.id_click1_sport -> {
                myViewpage.currentItem = 1
            }
            R.id.id_icon_2, R.id.id_click2 -> {
                myViewpage.currentItem = 2
            }
            R.id.id_icon_2_2, R.id.id_click2_2 -> {
                myViewpage.currentItem = 3
            }
            R.id.id_icon_3, R.id.id_click3 -> {
                myViewpage.currentItem = 4
            }

        }
    }

    var mLocationClient: LocationClient? = null
    private val myListener: MyLocationListener = MyLocationListener()

    private fun init_loction() {
        mLocationClient = LocationClient(this);
        //声明LocationClient类
        mLocationClient?.registerLocationListener(myListener)

        val option = LocationClientOption()
        option.priority = LocationClientOption.NetWorkFirst
        option.addrType = "all"
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy

        //可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；
        option.setCoorType("BD09ll")

//        option.setScanSpan(0)

        //可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效
        option.isOpenGps = false
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

//        option.isLocationNotify = true
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false)
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，V7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setNeedNewVersionRgc(true);
//可选，设置是否需要最新版本的地址信息。默认需要，即参数为true

        mLocationClient?.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

// 扫描二维码/条码回传
        // 扫描二维码/条码回传
        if (requestCode == Sp.REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val content = data.getStringExtra(Constant.CODED_CONTENT)
                Show_toast.showText(this, "扫描结果为$content")
            }
        }
        println("requestCode" + requestCode)
        if (requestCode == Sp.REQUEST_GPS) {
            init_start()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient?.stop()
    }

    fun showbottom() {
        functionClass.setanimvisible_totop(id_myBottomview)
    }

    fun hidebottom() {
        functionClass.setanimgone_tobottomy(id_myBottomview)
    }

    fun showbottom_location(view: View) {
        functionClass.setanimvisible_totop(id_locatio_view_bottom)
        id_locatio_view_bottom.addView(view)
    }

    fun hidebottom_location() {
        functionClass.setanimgone_tobottomy(id_locatio_view_bottom)
        id_locatio_view_bottom.removeAllViews()
    }


}
