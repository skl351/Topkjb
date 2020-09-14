package com.top.kjb.bean

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_twopage_item_3he1 :baseBean(){
    lateinit var list:ArrayList<bean_twopage_item_3he1_item>
    class bean_twopage_item_3he1_item{
        var text=""
        var username=""
        var headImg=""
        var motto=""
        var gymName=""
        var gymDescribe=""
        var startTime=""
        var readTimes=""
        var title=""
        var gymnasiumId=0
        var userScore=0f
        var comprehensiveScore=0f
        var lat=""
        var lng=""
        var id=0
        var userId=0
        var textType=0
        var type=3
        var likeStatus=false
        var favoriteStatus=false
        var followStatus=false
        var pic=""
        var gymPic=""
        var gymHeadImg=""
        var distance=""
        var likeCounts=""
         var picList=ArrayList<String>()
         var likeHeadImg=ArrayList<String>()
    }
}