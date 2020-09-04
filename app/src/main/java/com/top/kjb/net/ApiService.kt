package com.top.kjb.net

import com.google.gson.JsonObject
import com.top.kjb.bean.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    /**
     * 用户中心
     */
    @POST("user/getUserCenter")
    fun usergetUserCenter(
        @Header("x-auth-token") token: String,
        @Query("userId") userId: Int
    ): Call<Result<user_info>>

    /**
     * 更新用户信息
     */
    @POST("user/updateUserDataUserVO")
    fun userupdateUserDataUserVO(
        @Header("x-auth-token") token: String,
        @Body userDataVO: JsonObject
    ): Call<Result<String>>

    /**
     * 更新用户信息
     */
    @POST("user/updateDynamicBackground")
    fun userupdateDynamicBackground(
        @Header("x-auth-token") token: String,
        @Query("imgUrl") imgUrl: String
    ): Call<Result<Int>>

    /**
     * 个人资料
     */
    @POST("user/getUserData")
    fun usergetUserData(
        @Header("x-auth-token") token: String
    ): Call<Result<bean_gerenziliao>>

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
     * 更换手机号
     */
    @GET("user/updateUserTel")
    fun userupdateUserTel(
        @Header("x-auth-token") token: String,
        @Query("tel") tel: String
    ): Call<Result<beanuserlogin>>

    /**
     * 首页banner
     */
    @GET("banner/getAd")
    fun mainbannergetad(
        @Header("x-auth-token") token: String
    ): Call<Result<ArrayList<String>>>

    /**
     *获取最近场地list
     */
    @GET("gymnasium/gymnasiumList")
    fun main_zuijing(
        @Header("x-auth-token") token: String,
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     *获取最近场地list
     */
    @GET("gymnasium/gymnasiumList")
    fun gymnasiumgymnasiumList(
        @Header("x-auth-token") token: String,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("gymType") gymType: String
    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     *获取综合list
     */
    @POST("gymnasium/searchGymnasiumByComprehensiveScore")
    fun main_zonghe(
        @Header("x-auth-token") token: String
    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     * 根据id 查看炫亮点详情
     */
    @GET("gymnasium/gymnasiumDetails")
    fun gymnasiumgymnasiumDetails(
        @Header("x-auth-token") token: String,
        @Query("gymId") gymId: Int
    ): Call<Result<bean_main_item_about_xuanliangdiann>>

    /**
     * 根据id 查看详情
     */
    @GET("gymnasium/searchGymnasiumById")
    fun getplacedetailByid(
        @Header("x-auth-token") token: String,
        @Query("gymnasiumId") gymnasiumId: Int
    ): Call<Result<bean_main_detail_top>>

    /**
     *获取评分排序
     */
    @POST("ymnasium/searchGymnasiumByUserRank")
    fun main_pingfen(
        @Header("x-auth-token") token: String
    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     * 圈子
     */
    @POST("circle/selectAllCircleUser")
    fun twoselectAllCircle(
        @Header("x-auth-token") token: String,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_twopage_item_3he1>>

    /**
     * 关注
     */
    @POST("userFollow/selectUserFollow")
    fun userFollowselectUserFollow(
        @Header("x-auth-token") token: String,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_twopage_item_3he1>>

    /**
     * 获取炫亮点
     */
    @POST("highlights/selectAllHighlights")
    fun twoselectAllHighlights(
        @Header("x-auth-token") token: String,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_twopage_item_3he1>>

    /**
     * 根据id查看炫亮点相关详情
     */
    @POST("highlights/selectHighlightsById")
    fun highlightsselectHighlightsById(
        @Header("x-auth-token") token: String,
        @Query("highlightsId") highlightsId: Int
    ): Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>

    /**
     * 根据id查看圈子相关详情
     */
    @POST("circle/selectCircleById")
    fun circleselectCircleById(
        @Header("x-auth-token") token: String,
        @Query("circleId") circleId: Int
    ): Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>

    /**
     * 根据id查看咨询相关详情
     */
    @POST("information/selectInformationById")
    fun informationselectInformationById(
        @Header("x-auth-token") token: String,
        @Query("informationId") informationId: Int
    ): Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>

    /**
     * 根据iD查询点赞数量头像
     */
    @POST("highlightsLikes/selectHighlightsLikesCount")
    fun highlightsLikesselectHighlightsLikesCount(
        @Header("x-auth-token") token: String,
        @Query("highlightsId") highlightsId: Int
    ): Call<Result<Int>>

    /**
     * 获取咨询
     */
    @POST("information/selectAllInformation")
    fun twoselectselectAllInformation(
        @Header("x-auth-token") token: String,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_twopage_item2>>

    /**
     * 炫亮点评论
     */
    @POST("highlightsComments/selectComments")
    fun highlightsCommentsselectComments(
        @Header("x-auth-token") token: String,
        @Query("highlightsId") highlightsId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_user_comment>>

    /**
     * 圈子评论
     */
    @POST("circleComments/selectComments")
    fun circleCommentsselectComments(
        @Header("x-auth-token") token: String,
        @Query("circleId") circleId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_user_comment>>

    /**
     * 咨询评论
     */
    @POST("informationComments/selectComments")
    fun informationCommentsselectComments(
        @Header("x-auth-token") token: String,
        @Query("informationId") informationId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_user_comment>>

    /**
     * 炫亮点点赞收藏
     */
    @POST("highlightsLikes/insertHighlightsLikes")
    fun highlightsLikesinsertHighlightsLikes(
        @Header("x-auth-token") token: String,
        @Query("highlightsId") highlightsId: Int,
        @Query("type") type: Int,
        @Query("likeStatus") likeStatus: Int,
        @Query("favoriteStatus") favoriteStatus: Int
    ): Call<Result<Int>>

    /**
     * 圈子点赞收藏
     */
    @POST("circleLikes/insertCircleLikes")
    fun circleLikesinsertCircleLikes(
        @Header("x-auth-token") token: String,
        @Query("circleId") circleId: Int,
        @Query("type") type: Int,
        @Query("likeStatus") likeStatus: Int,
        @Query("favoriteStatus") favoriteStatus: Int
    ): Call<Result<Int>>

    /**
     * 圈子点赞收藏
     */
    @POST("informationLikes/insertInformationLikes")
    fun informationLikesinsertInformationLikes(
        @Header("x-auth-token") token: String,
        @Query("informationId") informationId: Int,
        @Query("type") type: Int,
        @Query("likeStatus") likeStatus: Int,
        @Query("favoriteStatus") favoriteStatus: Int
    ): Call<Result<Int>>

    /**
     * 炫亮点文章发布
     */
    @FormUrlEncoded
    @POST("highlights/insertHighlights")
    fun highlightsinsertHighlights(
        @Header("x-auth-token") token: String,
        @Query("userId") userId: Int,
        @Field("text") text: String,
        @Query("pic") pic: String,
        @Query("gymnasiumId") gymnasiumId: Int
    ): Call<Result<String>>

    /**
     * 圈子文章发布
     */
    @FormUrlEncoded
    @POST("circle/insertCircle")
    fun circleinsertCircle(
        @Header("x-auth-token") token: String,
        @Query("userId") userId: Int,
        @Field("text") text: String,
        @Query("pic") pic: String
    ): Call<Result<String>>

    /**
     * 炫亮点评论发表
     */
    @FormUrlEncoded
    @POST("highlightsComments/insertHighlightsComments")
    fun highlightsCommentsinsertHighlightsComments(
        @Header("x-auth-token") token: String,
        @Query("publisherId") publisherId: Int,//之后不用
        @Field("highlightsId") highlightsId: Int,
        @Query("commentsText") commentsText: String
    ): Call<Result<String>>

    /**
     * 圈子评论发表
     */
    @FormUrlEncoded
    @POST("circleComments/insertCircleComments")
    fun circleCommentsinsertCircleComments(
        @Header("x-auth-token") token: String,
        @Query("publisherId") publisherId: Int,//之后不用
        @Field("circleId") circleId: Int,
        @Query("commentsText") commentsText: String
    ): Call<Result<String>>

    /**
     * 咨询评论发表
     */
    @FormUrlEncoded
    @POST("informationComments/insertInformationComments")
    fun informationCommentsinsertInformationComments(
        @Header("x-auth-token") token: String,
        @Query("publisherId") publisherId: Int,//之后不用
        @Field("informationId") informationId: Int,
        @Query("commentsText") commentsText: String
    ): Call<Result<String>>


    /**
     * 获取七牛token
     */
    @POST("Qiniu/getQiniuToken")
    fun QiniugetQiniuToken(
        @Header("x-auth-token") token: String,
        @Query("key") key: String
    ): Call<Result<bean_qiniu_token>>

    /**
     * 获取粉丝list
     */
    @POST("fans/fans_list")
    fun userselectAllFansByUserId(
        @Header("x-auth-token") token: String,
        @Query("starId") starId: Int
    ): Call<Result<ArrayList<bean_attention_fans>>>

    /**
     * 获取关注list
     */
    @POST("fans/follow_list")
    fun userselectAllFollowByUserId(
        @Header("x-auth-token") token: String,
        @Query("usersId") usersId: Int
    ): Call<Result<ArrayList<bean_attention_fans>>>

    /**
     * 获取动态
     */
    @POST("userDynamic/selectUserDynamic")
    fun userDynamicselectUserDynamic(
        @Header("x-auth-token") token: String,
        @Query("userId") userId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_total_list>>

    /**
     * 获取zixun
     */
    @POST("userFavorites/selectUserFavorites")
    fun userFavoritesselectUserFavorites(
        @Header("x-auth-token") token: String,
        @Query("userId") userId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_total_list>>

    /**
     * 成为取消粉丝
     */
    @POST("fans/become_cancel_fans")
    fun fansbecome_cancel_fans(
        @Header("x-auth-token") token: String,
        @Query("fansId") fansId: Int,
        @Query("starId") starId: Int
    ): Call<Result<String>>

    /**
     * 炫亮点回复list
     */
    @POST("highlightsReply/selectReply")
    fun highlightsReplyselectReply(
        @Header("x-auth-token") token: String,
        @Query("commentId") commentId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_user_comment>>

    /**
     * 圈子回复list
     */
    @POST("circleReply/selectReply")
    fun circleReplyselectReply(
        @Header("x-auth-token") token: String,
        @Query("commentId") commentId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_user_comment>>

    /**
     * 咨询回复list
     */
    @POST("informationReply/selectReply")
    fun informationReplyselectReply(
        @Header("x-auth-token") token: String,
        @Query("commentId") commentId: Int,
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): Call<Result<bean_user_comment>>

    /**
     * 炫亮点回复操作
     * 炫亮点添加回复reply_type = 0是对评论，reply_type = 1是对回复，
     * parentId为被回复对象的id(评论或回复的id),commentsId表示评论的id(当前回复挂在哪条评论下面)
     */
    @POST("highlightsReply/insertHighlightsReply")
    fun highlightsReplyinsertHighlightsReply(
        @Header("x-auth-token") token: String,
        @Query("parentId") parentId: Int,//那条评论的id
        @Query("replyText") replyText: String,//回复内容
        @Query("replyType") replyType: Int,
        @Query("commentsId") commentsId: Int
    ): Call<Result<String>>

    /**
     * 圈子回复操作
     * 圈子添加回复reply_type = 0是对评论，reply_type = 1是对回复，
     * parentId为被回复对象的id(评论或回复的id),commentsId表示评论的id(当前回复挂在哪条评论下面)
     */
    @POST("circleReply/insertCircleReply")
    fun circleReplyinsertCircleReply(
        @Header("x-auth-token") token: String,
        @Query("parentId") parentId: Int,//那条评论的id
        @Query("replyText") replyText: String,//回复内容
        @Query("replyType") replyType: Int,
        @Query("commentsId") commentsId: Int
    ): Call<Result<String>>

    /**
     * 咨询回复操作
     * 咨询添加回复reply_type = 0是对评论，reply_type = 1是对回复，
     * parentId为被回复对象的id(评论或回复的id),commentsId表示评论的id(当前回复挂在哪条评论下面)
     */
    @POST("informationReply/insertInformationReply")
    fun informationReplyinsertInformationReply(
        @Header("x-auth-token") token: String,
        @Query("parentId") parentId: Int,//那条评论的id
        @Query("replyText") replyText: String,//回复内容
        @Query("replyType") replyType: Int,
        @Query("commentsId") commentsId: Int
    ): Call<Result<String>>

    /**
     * 获取消费场馆
     */
    @POST("gymnasiumLog/selectGymListByUserId")
    fun gymnasiumLogselectGymListByUserId(
        @Header("x-auth-token") token: String
    ): Call<Result<ArrayList<bean_gym_payed_item>>>

    /**
     *sportstype
     */
    @GET("sports/selectAllGroundingSports")
    fun selectAllGroundingSports(
    ): Call<Result<ArrayList<bean_type_item>>>

    /**
     * 根据关键字找场馆
     */
    @GET("gymnasium/gymLikeSearch")
    fun gymnasiumgymLikeSearch(
        @Header("x-auth-token") token: String,
        @Query("keyword") keyword: String
    ): Call<Result<ArrayList<bean_main_item>>>

    /**
     * 根据id找场馆详情
     */
    @POST("gymnasium/selectGymDetailPage")
    fun gymnasiumselectGymDetailPage(
        @Header("x-auth-token") token: String,
        @Query("gymID") gymID: Int
    ): Call<Result<bean_main_item_newdetail>>

    /**
     * 根据id找场馆详情
     */
    @POST("coach/selectCoach")
    fun coachselectCoach(
        @Header("x-auth-token") token: String,
        @Query("coachID") coachID: Int
    ): Call<Result<bean_coach_item>>


}