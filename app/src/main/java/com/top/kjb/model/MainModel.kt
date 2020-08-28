package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import auntschool.think.com.mynettest.net.UrlConstant
import com.top.kjb.bean.*
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class MainModel {
    fun mainbannergetad(token:String): Call<Result<ArrayList<String>>> {
        return RetrofitManager.service.mainbannergetad(token)
    }
    fun main_zuijing(lat:String,lng:String,token:String):Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.main_zuijing(lat,lng,token)
    }
    fun main_zonghe(token:String):Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.main_zonghe(token)
    }
    fun main_pingfen(token:String):Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.main_pingfen(token)
    }
    fun getplacedetailByid(token: String,gymnasiumId:Int):Call<Result<bean_main_detail_top>>{
        return RetrofitManager.service.getplacedetailByid(token,gymnasiumId)
    }
    fun gymnasiumgymnasiumDetails(token:String,gymId:Int):Call<Result<bean_main_item_about_xuanliangdiann>>{
        return RetrofitManager.service.gymnasiumgymnasiumDetails(token,gymId)
    }


}