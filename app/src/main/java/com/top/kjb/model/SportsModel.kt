package com.top.kjb.model

import auntschool.think.com.mynettest.net.RetrofitManager
import auntschool.think.com.mynettest.net.UrlConstant
import com.top.kjb.bean.*
import retrofit2.Call

/**
 * Created by MaiBenBen on 2019/1/8.
 */
class SportsModel {
    fun togetherLogsearchTogetherLog(token:String,type:Int): Call<Result<ArrayList<bean_sports_list_item>>> {
        return RetrofitManager.service.togetherLogsearchTogetherLog(token,type)
    }
    fun togetherLogsearchMyTogetherLog(token:String,userId:Int): Call<Result<ArrayList<bean_sports_list_item>>> {
        return RetrofitManager.service.togetherLogsearchMyTogetherLog(token,userId)
    }
    fun togetherLogupdateMyTogetherLog(token:String,userId:Int,togetherLogId:Int,flag:Int): Call<Result<String>> {
        return RetrofitManager.service.togetherLogupdateMyTogetherLog(token,userId,togetherLogId,flag)
    }
    fun togetherLogsearchTogetherLogById(token:String,userId:Int,id:Int): Call<Result<bean_sports_list_item>> {
        return RetrofitManager.service.togetherLogsearchTogetherLogById(token,userId,id)
    }
    fun togetherLoginsertTogetherLog(token:String,userId:Int,adminId:Int,sportId:Int,time:String,endTime:String,gymnasiumId:Int,name:String,maxPeople:String,nowPeople:String): Call<Result<String>> {
        return RetrofitManager.service.togetherLoginsertTogetherLog(token,userId,adminId,sportId,time,endTime,gymnasiumId,name,maxPeople,nowPeople)
    }
    fun bottom_address(token:String,lat:String,lng:String):Call<Result<ArrayList<bean_main_item>>>{
        return RetrofitManager.service.bottom_address(token,lat,lng)
    }
    fun highlightsselectHighlightsList(token:String,typeId:Int,currentPage:Int,pageSize:Int):Call<Result<bean_twopage_item_3he1>>{
        return RetrofitManager.service.highlightsselectHighlightsList(token,typeId,currentPage,pageSize)
    }



}