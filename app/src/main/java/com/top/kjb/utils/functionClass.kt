package com.top.kjb.utils

object functionClass {

    fun setToken(token: String) {
        SharedPreferenceUtils.save("token", "token")
    }

    fun getToken(): String {
        return SharedPreferenceUtils.getString("token", "")
    }


    fun islogin():Boolean{
        println("-------------"+getToken())
        if ("".equals(getToken())){
            return false
        }else{
            return true
        }
    }


}