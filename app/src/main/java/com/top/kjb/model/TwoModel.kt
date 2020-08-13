package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import auntschool.think.com.mynettest.net.UrlConstant
import com.top.kjb.bean.*
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class TwoModel {

    fun twoselectAllCircle(token:String,currentPage:Int,pageSize:Int): Call<Result<bean_twopage_item_3he1>> {
        return RetrofitManager.service.twoselectAllCircle(token,currentPage,pageSize)
    }




}