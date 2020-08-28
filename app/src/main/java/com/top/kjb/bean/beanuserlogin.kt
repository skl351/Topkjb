package com.top.kjb.bean

class beanuserlogin:baseBean() {

    var token=""
    lateinit var user:beanuserlogin_user
    class beanuserlogin_user{
        var username=""
        var motto=""
        var headImg=""
        var password=""
        var tel=""
        var id=0
    }
}