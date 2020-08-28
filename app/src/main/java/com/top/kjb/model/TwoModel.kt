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
    fun userFollowselectUserFollow(token:String,currentPage:Int,pageSize:Int): Call<Result<bean_twopage_item_3he1>> {
        return RetrofitManager.service.userFollowselectUserFollow(token,currentPage,pageSize)
    }
    fun twoselectAllHighlights(token:String,currentPage:Int,pageSize:Int): Call<Result<bean_twopage_item_3he1>> {
        return RetrofitManager.service.twoselectAllHighlights(token,currentPage,pageSize)
    }
    fun highlightsselectHighlightsById(token:String,highlightsId:Int): Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>> {
        return RetrofitManager.service.highlightsselectHighlightsById(token,highlightsId)
    }
    fun circleselectCircleById(token:String,highlightsId:Int): Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>> {
        return RetrofitManager.service.circleselectCircleById(token,highlightsId)
    }
    fun informationselectInformationById(token:String,highlightsId:Int): Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>> {
        return RetrofitManager.service.informationselectInformationById(token,highlightsId)
    }
    fun highlightsLikesselectHighlightsLikesCount(token:String,highlightsId:Int): Call<Result<Int>> {
        return RetrofitManager.service.highlightsLikesselectHighlightsLikesCount(token,highlightsId)
    }
    fun twoselectselectAllInformation(token:String,currentPage:Int,pageSize:Int): Call<Result<bean_twopage_item2>> {
        return RetrofitManager.service.twoselectselectAllInformation(token,currentPage,pageSize)
    }
    fun highlightsCommentsselectComments(token:String,highlightsId:Int,currentPage:Int,pageSize:Int): Call<Result<bean_user_comment>> {
        return RetrofitManager.service.highlightsCommentsselectComments(token,highlightsId,currentPage,pageSize)
    }
    fun circleCommentsselectComments(token:String,highlightsId:Int,currentPage:Int,pageSize:Int): Call<Result<bean_user_comment>> {
        return RetrofitManager.service.circleCommentsselectComments(token,highlightsId,currentPage,pageSize)
    }
    fun informationCommentsselectComments(token:String,highlightsId:Int,currentPage:Int,pageSize:Int): Call<Result<bean_user_comment>> {
        return RetrofitManager.service.informationCommentsselectComments(token,highlightsId,currentPage,pageSize)
    }
    fun highlightsLikesinsertHighlightsLikes(token:String,highlightsId:Int,type:Int,likeStatus:Int,favoriteStatus:Int): Call<Result<Int>> {
        return RetrofitManager.service.highlightsLikesinsertHighlightsLikes(token,highlightsId,type,likeStatus,favoriteStatus)
    }
    fun circleLikesinsertCircleLikes(token:String,highlightsId:Int,type:Int,likeStatus:Int,favoriteStatus:Int): Call<Result<Int>> {
        return RetrofitManager.service.circleLikesinsertCircleLikes(token,highlightsId,type,likeStatus,favoriteStatus)
    }
    fun informationLikesinsertInformationLikes(token:String,highlightsId:Int,type:Int,likeStatus:Int,favoriteStatus:Int): Call<Result<Int>> {
        return RetrofitManager.service.informationLikesinsertInformationLikes(token,highlightsId,type,likeStatus,favoriteStatus)
    }
    fun highlightsinsertHighlights(token:String,userId:Int,text:String,pic:String,gymnasiumId:Int,userScore:String): Call<Result<String>> {
        return RetrofitManager.service.highlightsinsertHighlights(token,userId,text,pic,gymnasiumId,userScore)
    }
    fun circleinsertCircle(token:String,userId:Int,text:String,pic:String): Call<Result<String>> {
        return RetrofitManager.service.circleinsertCircle(token,userId,text,pic)
    }
    fun highlightsCommentsinsertHighlightsComments(token:String,publisherId:Int,highlightsId:Int,commentsText:String): Call<Result<String>> {
        return RetrofitManager.service.highlightsCommentsinsertHighlightsComments(token,publisherId,highlightsId,commentsText)
    }
    fun circleCommentsinsertCircleComments(token:String,publisherId:Int,highlightsId:Int,commentsText:String): Call<Result<String>> {
        return RetrofitManager.service.circleCommentsinsertCircleComments(token,publisherId,highlightsId,commentsText)
    }
    fun informationCommentsinsertInformationComments(token:String,publisherId:Int,highlightsId:Int,commentsText:String): Call<Result<String>> {
        return RetrofitManager.service.informationCommentsinsertInformationComments(token,publisherId,highlightsId,commentsText)
    }
    fun QiniugetQiniuToken(token:String,key:String): Call<Result<bean_qiniu_token>> {
        return RetrofitManager.service.QiniugetQiniuToken(token,key)
    }
    fun highlightsReplyselectReply(token:String,commentId:Int,currentPage:Int,pageSize:Int): Call<Result<bean_user_comment>> {
        return RetrofitManager.service.highlightsReplyselectReply(token,commentId,currentPage,pageSize)
    }
    fun circleReplyselectReply(token:String,commentId:Int,currentPage:Int,pageSize:Int): Call<Result<bean_user_comment>> {
        return RetrofitManager.service.circleReplyselectReply(token,commentId,currentPage,pageSize)
    }
    fun informationReplyselectReply(token:String,commentId:Int,currentPage:Int,pageSize:Int): Call<Result<bean_user_comment>> {
        return RetrofitManager.service.informationReplyselectReply(token,commentId,currentPage,pageSize)
    }
    fun highlightsReplyinsertHighlightsReply(token:String,parentId:Int,replyText:String,replyType:Int,commentsId:Int): Call<Result<String>> {
        return RetrofitManager.service.highlightsReplyinsertHighlightsReply(token,parentId,replyText,replyType,commentsId)
    }
    fun circleReplyinsertCircleReply(token:String,parentId:Int,replyText:String,replyType:Int,commentsId:Int): Call<Result<String>> {
        return RetrofitManager.service.circleReplyinsertCircleReply(token,parentId,replyText,replyType,commentsId)
    }
    fun informationReplyinsertInformationReply(token:String,parentId:Int,replyText:String,replyType:Int,commentsId:Int): Call<Result<String>> {
        return RetrofitManager.service.informationReplyinsertInformationReply(token,parentId,replyText,replyType,commentsId)
    }
    fun gymnasiumLogselectGymListByUserId(token:String): Call<Result<ArrayList<bean_gym_payed_item>>> {
        return RetrofitManager.service.gymnasiumLogselectGymListByUserId(token)
    }


}