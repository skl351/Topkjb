package com.top.kjb.bean

class beanuserlogin:baseBean() {

    var taken=""
    lateinit var user:beanuserlogin_user
    class beanuserlogin_user{
        var username=""
        var password=""
        var tel=""
    }
}