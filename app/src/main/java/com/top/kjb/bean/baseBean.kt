package com.top.kjb.bean

import com.google.gson.Gson


/**
 * Created by MaiBenBen on 2019/1/2.
 */
open class baseBean {
    override fun toString(): String {
        return Gson().toJson(this)//可以直接打印json
    }
}