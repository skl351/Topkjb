package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import auntschool.think.com.mynettest.net.UrlConstant
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_UpdataBean
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.beanuserlogin
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class LoginModel {
    fun phonegetSMS(tel:String): Call<Result<String>> {
        return RetrofitManager.service.phonegetSMS(tel)
    }
    fun phonelogin(username:String,captcha:String): Call<Result<beanuserlogin>> {
        return RetrofitManager.service.phonelogin(username,captcha)
    }
    fun userupdateUserTel(token:String,tel:String): Call<Result<beanuserlogin>> {
        return RetrofitManager.service.userupdateUserTel(token,tel)
    }
    fun captchacheckSMS(token:String,tel:String,captcha:String): Call<Result<String>> {
        return RetrofitManager.service.captchacheckSMS(token,tel,captcha)
    }



}