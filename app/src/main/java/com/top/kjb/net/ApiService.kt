package com.top.kjb.net

import com.top.kjb.bean.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /**
     * 获取验证码
     */
    @POST("captcha/getSMS")
    fun phonegetSMS(
        @Query("tel") tel: String
    ): Call<Result<String>>

    /**
     * 验证码登录
     */
    @GET("user/login")
    fun phonelogin(
        @Query("username") username: String,
        @Query("captcha") captcha: String
    ): Call<Result<beanuserlogin>>

    /**
     * 首页banner
     */
    @GET("banner/getAd")
    fun mainbannergetad(

    ): Call<Result<ArrayList<String>>>

    /**
     *获取最近场地list
     */
    @GET("gymnasium/gymnasiumList")
    fun main_zuijing(
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     *获取综合list
     */
    @POST("gymnasium/searchGymnasiumByComprehensiveScore")
    fun main_zonghe(

    ): Call<Result<ArrayList<bean_main_item>>>
    /**
     * 根据id 查看详情
     */
    @GET("gymnasium/searchGymnasiumById")
    fun getplacedetailByid(
        @Query("gymnasiumId") gymnasiumId: Int
    ): Call<Result<bean_main_detail_top>>

    /**
     *获取评分排序
     */
    @POST("ymnasium/searchGymnasiumByUserRank")
    fun main_pingfen(

    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     * 圈子
     */
    @POST("circle/selectAllCircle")
    fun twoselectAllCircle(
        @Query("x-auth-token") token: String,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_twopage_item_3he1>>
    /**
     * 圈子
     */
    @POST("user/getUserCenter")
    fun usergetUserCenter(
        @Query("x-auth-token") token: String
    ): Call<Result<user_info>>



//    /**
//     * 获取炫亮点
//     */
//    @GET("highlights/selectAllHighlights")
//    fun twoselectAllHighlights(
//        @Query("x-auth-token") token: String,
//        @Query("currentPage") currentPage: String,
//        @Query("pageSize") pageSize: String
//    ):Call<Result<>>

}