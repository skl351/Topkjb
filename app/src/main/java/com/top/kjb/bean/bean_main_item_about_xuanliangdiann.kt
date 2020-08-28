package com.top.kjb.bean

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_main_item_about_xuanliangdiann : baseBean() {

    lateinit var gymnasiumPageVO: bean_main_item_about_xuanliangdiann_class

    class bean_main_item_about_xuanliangdiann_class {
        var gymID = 0
        var gymName = ""
        var userEvaluate = 0f
        var address = ""
        var specificAddress = ""
        var tel = ""
        var businessHours = ""
        var openYear = ""
        var area = ""
        var seatingCapacity = ""
        lateinit var gymPhotoList: ArrayList<String>
        lateinit var gymExtension: ArrayList<String>
    }

    var gymUserEvaluateNum = ""
    lateinit var highlightsGymVOList: ArrayList<bean_main_item_about_xuanliangdiann_highlightsGymVOList>

    class bean_main_item_about_xuanliangdiann_highlightsGymVOList {

        lateinit var highlightsUser: bean_main_item_about_xuanliangdiann_highlightsGymVOList_highlightsUser

        class bean_main_item_about_xuanliangdiann_highlightsGymVOList_highlightsUser {
            var id = 0
            var username = ""
            var headImg = ""
            var motto = ""
            var startTime = ""
        }

        lateinit var highlightsComments: bean_main_item_about_xuanliangdiann_highlightsGymVOList_highlightsComments


        class bean_main_item_about_xuanliangdiann_highlightsGymVOList_highlightsComments {
            var id = 0
            var startTime = ""
            var commentsText = ""
        }

        lateinit var highlightsReply: bean_main_item_about_xuanliangdiann_highlightsGymVOList_highlightsReply

        class bean_main_item_about_xuanliangdiann_highlightsGymVOList_highlightsReply {
            var id = 0
        }
    }
}