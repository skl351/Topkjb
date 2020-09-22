package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import com.top.kjb.bean.Result
import retrofit2.Call

class ClueModel {
    fun clubselectMemberHeadImgList(token: String): Call<Result<ArrayList<String>>> {
        return RetrofitManager.service.clubselectMemberHeadImgList(token)
    }
}