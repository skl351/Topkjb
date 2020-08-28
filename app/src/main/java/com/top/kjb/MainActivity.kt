package com.top.kjb

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.fragemnt_one
import com.top.kjb.tabfragment.fragemnt_two
import com.top.kjb.tabfragment.fragment_three
import com.top.kjb.utils.MyLocationListener
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.common.Constant
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init_click()
        init_view()
        init_viewpage()
        init_loction()
        init_golocation()
    }
    fun init_questlocation(){
        mLocationClient?.requestLocation()
    }
    open fun init_golocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val checkCallPhonePermission = ContextCompat.checkSelfPermission(this!!, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), Sp.REQUEST_CODE_Location)
                return
            } else {
                mLocationClient?.start()
            }
        } else {
            mLocationClient?.start()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        println(""+requestCode+","+"相关"+grantResults)
        if (requestCode == Sp.REQUEST_CODE_Location) {
            if (grantResults[0]=== PackageManager.PERMISSION_GRANTED){
                mLocationClient?.start()
            }else{
                Show_toast.showText(this,"用户拒绝定位相关权限")
            }


        }
        if (requestCode == Sp.REQUEST_CODE_Location2) {
            if (grantResults[0]=== PackageManager.PERMISSION_GRANTED){
                mLocationClient?.requestLocation()
            }else{
                Show_toast.showText(this,"用户拒绝定位相关权限")
            }


        }
            if (requestCode == Sp.REQUEST_CODE_camera) {
            if (grantResults[0]=== PackageManager.PERMISSION_GRANTED){
                var intent = Intent(this, CaptureActivity::class.java)
                startActivityForResult(intent, Sp.REQUEST_CODE_SCAN);
            }else{
                Show_toast.showText(this,"用户拒绝拍照相关权限")
            }

        }
    }


    var currpage = 0
    private fun init_viewpage() {
        id_icon_1.isSelected = true
        id_text_1.isSelected = true
        myViewpage.offscreenPageLimit = 2
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
                when (position) {
                    0 -> {

                        clickone()
                    }
                    1 -> {

                        clicktwo()
                    }
                    2 -> {

                        clickthree()

                    }
                }
            }
        })
    }

    var bottom_tabist: ArrayList<BaseFragment>? = null
    override fun init_view() {
        super.init_view()
        bottom_tabist = ArrayList()
        val fragment1 = fragemnt_one()
        val fragment2 = fragemnt_two()
        val fragment3 = fragment_three()
        bottom_tabist?.add(fragment1)
        bottom_tabist?.add(fragment2)
        bottom_tabist?.add(fragment3)
    }

    fun clickone() {
        currpage = 0
        id_icon_1.isSelected = true
        id_icon_2.isSelected = false
        id_icon_3.isSelected = false

        id_text_1.isSelected = true
        id_text_2.isSelected = false
        id_text_3.isSelected = false
    }

    fun clicktwo() {
        currpage = 1
        id_icon_1.isSelected = false
        id_icon_2.isSelected = true
        id_icon_3.isSelected = false

        id_text_1.isSelected = false
        id_text_2.isSelected = true
        id_text_3.isSelected = false
    }

    fun clickthree() {
        currpage = 2
        id_icon_1.isSelected = false
        id_icon_2.isSelected = false
        id_icon_3.isSelected = true

        id_text_1.isSelected = false
        id_text_2.isSelected = false
        id_text_3.isSelected = true
    }

    override fun init_click() {
        super.init_click()
        id_icon_1.setOnClickListener(this)
        id_click1.setOnClickListener(this)
        id_icon_2.setOnClickListener(this)
        id_click2.setOnClickListener(this)
        id_icon_3.setOnClickListener(this)
        id_click3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_icon_1, R.id.id_click1 -> {
                myViewpage.currentItem = 0
            }
            R.id.id_icon_2, R.id.id_click2 -> {
                myViewpage.currentItem = 1
            }
            R.id.id_icon_3, R.id.id_click3 -> {
                myViewpage.currentItem = 2
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

        option.setScanSpan(0)

        //可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效
        option.isOpenGps = true
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.isLocationNotify = true
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
                val content= data.getStringExtra(Constant.CODED_CONTENT)
                Show_toast.showText(this,"扫描结果为$content")
            }
        }
    }
}
