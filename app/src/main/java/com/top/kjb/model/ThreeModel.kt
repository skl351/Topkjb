package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import auntschool.think.com.mynettest.net.UrlConstant
import com.top.kjb.bean.*
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class ThreeModel {

    fun usergetUserCenter(token:String): Call<Result<user_info>> {
        return RetrofitManager.service.usergetUserCenter(token)

    }


}