package com.top.kjb

import android.app.Application
import android.graphics.Bitmap
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.top.kjb.utils.SharedPreferenceUtils

class MyApplicatipn : Application() {
    companion object{
        lateinit var a:MyApplicatipn
    }

    override fun onCreate() {
        super.onCreate()
        a=this
        SharedPreferenceUtils.init(this)
        init_image()
    }


    /**
     * 初始化图片显示工具类
     */
    private fun init_image() {
        val defaultOptions = DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.default_image)
            .showImageOnFail(R.drawable.default_image)
            .showImageOnLoading(R.drawable.default_image)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.NONE)//设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
        val config = ImageLoaderConfiguration.Builder(applicationContext)
            .defaultDisplayImageOptions(defaultOptions)
            .discCacheSize(50 * 1024 * 1024)
            .discCacheFileCount(100)
//                .writeDebugLogs()
            .build()
        ImageLoader.getInstance().init(config)
    }
}