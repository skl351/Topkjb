package com.top.kjb.bean

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_user_comment : baseBean() {
    lateinit var list: ArrayList<bean_user_comment>

    class bean_user_comment {
        var id = 0
        var userId = 0
        var commentsText = ""
        var username = ""
        var parentUserName = ""
        var headImg = ""
        var replyText = ""
        var motto = ""
        var parentId = 0
        var publisherId = 0
        var responderId = 0
        var likesTimes = ""
        var likeStatus = false
        var replyCounts = 0
        var startTime = ""

    }

}