package com.top.kjb.bean

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_sports_list_item : baseBean() {

    var sport=""
    var gymnasiumAddress=""
    var gymnasiumName=""
    var gymnasiumPic=""
    var isJoin=0
    lateinit var togetherLogs: bean_sports_list_item_togetherLogs

    class bean_sports_list_item_togetherLogs {
        var id = 0
        var gymnasiumId = 0
        var time = ""
        var endTime = ""
        var nowPeople = ""
        var maxPeople = ""
        var name = ""
    }

    lateinit var userInfo: ArrayList<bean_sports_list_item_userInfo>
    class bean_sports_list_item_userInfo{
        var username=""
        var headImg=""
    }
}