package com.top.kjb.utils

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.top.kjb.MyApplicatipn
import com.top.kjb.R


class MyLocationListener : BDAbstractLocationListener() {
    override fun onReceiveLocation(location: BDLocation) {
        val addr = location.addrStr //获取详细地址信息

        val country = location.country //获取国家

        val province = location.province //获取省份

        val city = location.city //获取城市

        val district = location.district //获取区县

        val street = location.street //获取街道信息

        val adcode = location.adCode //获取adcode

        val town = location.town //获取乡镇信息


        val latitude: Double = location.getLatitude() //获取纬度信息

        val longitude: Double = location.getLongitude() //获取经度信息


        val radius: Float = location.getRadius() //获取定位精度，默认值为0.0f


        val coorType: String = location.getCoorType()
        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        val errorCode: Int = location.getLocType()
        println(
            "经纬度" + latitude + "," + longitude + ","
                    + errorCode + location.city
        )
        if (errorCode == 161 || errorCode == 61) {
            Sp.lat=location.latitude.toString()
            Sp.lng=location.longitude.toString()
            Sp.address_big=location.province+location.city+location.district
            Sp.address_small=location.district
            var intent = Intent(Sp.location_send)
            intent.putExtra("locationinfo", location)
            intent.putExtra("flag",true)
            MyApplicatipn.a?.sendBroadcast(intent)


            //得到后
        } else {

            var lo = BDLocation()
            lo.latitude = 30.194645
            lo.longitude = 121.267968
            var intent = Intent(Sp.location_send)
            intent.putExtra("flag",false)
            intent.putExtra("locationinfo", lo)
            MyApplicatipn.a?.sendBroadcast(intent)


        }

    }


}
