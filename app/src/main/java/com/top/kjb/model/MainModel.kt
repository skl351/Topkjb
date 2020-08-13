package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import auntschool.think.com.mynettest.net.UrlConstant
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_UpdataBean
import com.top.kjb.bean.bean_main_item
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class MainModel {
    fun mainbannergetad(): Call<Result<ArrayList<String>>> {
        return RetrofitManager.service.mainbannergetad()
    }
    fun main_zuijing(lat:String,lng:String):Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.main_zuijing(lat,lng)
    }
    fun main_zonghe():Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.main_zonghe()
    }
    fun main_pingfen():Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.main_pingfen()
    }


}