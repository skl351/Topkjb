package com.top.kjb.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_main_item_newdetail() : baseBean() {

    var gymName = ""
    var gymScore = 0f
    var businessHours = ArrayList<String>()
    var openYear = ""
    var area = "0"
    var seatingCapacity = "0"
    var instrumentTap = "0"
    var address = ""
    var specificAddress = ""
    var tel = ""
    var vipNum = ""
    lateinit var coachList: ArrayList<bean_main_item_newdetail_coachList>

    class bean_main_item_newdetail_coachList {
        var coachHeadImg = ""
        var coachName = ""
        var coachId = 0
    }

    lateinit var vipHeadImgList: ArrayList<bean_main_item_newdetail_vipHeadImgList>

    class bean_main_item_newdetail_vipHeadImgList {
        var userID = 0
        var gymID = 0
        var headImg = ""
    }

    lateinit var gymBgImg: ArrayList<String>
}