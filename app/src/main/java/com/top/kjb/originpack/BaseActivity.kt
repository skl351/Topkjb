package com.top.kjb.originpack

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.umeng.analytics.MobclickAgent

open class BaseActivity : AppCompatActivity() {
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

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this) // 基础指标统计，不能遗漏

    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this) // 基础指标统计，不能遗漏
    }
}