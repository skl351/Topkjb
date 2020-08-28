package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import com.google.gson.JsonObject
import com.top.kjb.bean.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class ThreeModel {

    fun usergetUserCenter(token: String,userId:Int): Call<Result<user_info>> {
        return RetrofitManager.service.usergetUserCenter(token,userId)

    }

    fun userupdateUserDataUserVO(token: String, userDataVO: JsonObject): Call<Result<String>> {
        return RetrofitManager.service.userupdateUserDataUserVO(token, userDataVO)

    }

    fun usergetUserData(token: String): Call<Result<bean_gerenziliao>> {
        return RetrofitManager.service.usergetUserData(token)
    }

    fun userselectAllFansByUserId(token: String,starId:Int): Call<Result<ArrayList<bean_attention_fans>>> {
        return RetrofitManager.service.userselectAllFansByUserId(token,starId)
    }

    fun userselectAllFollowByUserId(token: String,usersId:Int): Call<Result<ArrayList<bean_attention_fans>>> {
        return RetrofitManager.service.userselectAllFollowByUserId(token,usersId)
    }

    fun fansbecome_cancel_fans(token: String, fansId: Int, starId: Int): Call<Result<String>> {
        return RetrofitManager.service.fansbecome_cancel_fans(token, fansId, starId)
    }

    fun userDynamicselectUserDynamic(
        token: String,
        userId: Int,
        currentPage: Int,
        pagesize: Int
    ): Call<Result<bean_total_list>> {
        return RetrofitManager.service.userDynamicselectUserDynamic(
            token,
            userId,
            currentPage,
            pagesize
        )
    }

    fun userFavoritesselectUserFavorites(
        token: String,
        userId: Int,
        currentPage: Int,
        pagesize: Int
    ): Call<Result<bean_total_list>> {
        return RetrofitManager.service.userFavoritesselectUserFavorites(
            token,
            userId,
            currentPage,
            pagesize
        )
    }


}