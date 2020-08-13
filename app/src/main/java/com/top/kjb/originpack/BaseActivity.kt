package com.top.kjb.originpack

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        init_intent()

    }

    open fun init_click() {
    }

    open fun init_intent() {

    }
    open fun init_view() {

    }
}