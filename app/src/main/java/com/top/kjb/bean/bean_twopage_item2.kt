package com.top.kjb.bean

/**
 * Created by MaiBenBen on 2019/1/11.
 */
class bean_twopage_item2:baseBean(){
    lateinit var list:ArrayList<bean_twopage_item2_item>
    class bean_twopage_item2_item{
        var id=0
        var title=""
        var text=""
        var pic=""
        var readTimes=""
        var tags=""
        var startTime=""
    }
}