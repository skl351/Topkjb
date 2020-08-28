package com.top.kjb.bean

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_total_list :baseBean(){

    lateinit var list:ArrayList<bean_total_list_class>
    class bean_total_list_class{
        var id=0
        var userId=0
        var text=""
        var headImg=""
        var username=""
        var motto=""
        var readTimes=""
        var tags=""
        var pic=""
        var gymnasiumId=0
        var userScore=0f
        var textType=1//1是圈子 2是炫亮点 3 咨询
    }

}