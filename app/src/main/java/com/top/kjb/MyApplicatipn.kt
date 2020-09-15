package com.top.kjb

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatDelegate
import com.baidu.mapapi.SDKInitializer
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hyphenate.easeui.EaseUI
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.top.kjb.net.HeaderInterceptor
import com.top.kjb.utils.SharedPreferenceUtils
import com.top.kjb.utils.Sp
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class MyApplicatipn : Application() {
    companion object {
        lateinit var a: MyApplicatipn
        var okHttpClient = OkHttpClient.Builder()
    }

    override fun onCreate() {
        super.onCreate()
        a = this
        SharedPreferenceUtils.init(this)
        init_image()
        init_retrofit2()
        init_log()
        init_refresh()//刷新
        init_Umeng()
        SDKInitializer.initialize(this)//百度
        val options = EMOptions()
        EaseUI.getInstance().init(this, options)

    }

    /**
     * 友盟统计和推送
     */
    private fun init_Umeng() {
        UMConfigure.setLogEnabled(true)

        /*
注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
*/
        println("umeng=============" + Sp.UmengAppkey + "," + Sp.UMENG_MESSAGE_SECRET)
        UMConfigure.init(
            this,
            Sp.UmengAppkey,
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            Sp.UMENG_MESSAGE_SECRET
        );
        val mPushAgent = PushAgent.getInstance(this)
//        mPushAgent.setNotificaitonOnForeground(false);//前台不显示推送
////注册推送服务，每次调用register方法都会回调该接口
//
//
        mPushAgent?.register(object : IUmengRegisterCallback {

            override fun onSuccess(deviceToken: String) {
                //注册成功会返回device token
                println("deviceToken" + deviceToken)
                Sp.UmengDevicetokens = deviceToken
            }

            override fun onFailure(s: String, s1: String) {
                println("umengdeviceTokenfail" + "" + s + "," + s1)
            }
        })
//        mPushAgent?.displayNotificationNumber = 10//推送条数

//        MiPushRegistar.register(
//            this,
//            PrivateConstants.XM_PUSH_APPID,
//            PrivateConstants.XM_PUSH_APPKEY
//        );//小米
//        HuaWeiRegister.register(this);//华为
//        VivoRegister.register(this);//vivo
//        OppoRegister.register(
//            this,
//            "a1c1a535e8374971910536f78ca47a7b",
//            "5f033627d2604168b1f5a5c98850abcc"
//        );//oppo
    }

    /**
     * 刷新初始化-格式化数据
     */
    private fun init_refresh() {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(object : DefaultRefreshInitializer {
            override fun initialize(context: Context, layout: RefreshLayout) {
                //全局设置（优先级最低）
                layout.setEnableAutoLoadMore(true)
                layout.setEnableOverScrollDrag(false)
                layout.setEnableOverScrollBounce(true)
                layout.setEnableLoadMoreWhenContentNotFull(true)
                layout.setEnableScrollContentWhenRefreshed(true)
            }


        })
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black)//全局设置主题颜色
                return ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }

        })
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                //指定为经典Footer，默认是 BallPulseFooter
                return ClassicsFooter(context).setDrawableSize(20F)
            }

        })
    }

    private fun init_log() {
        var format = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(3)
            .methodOffset(1)
            .tag("PRETTY_LOGGER")
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(format) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        })
    }

    private fun init_retrofit2() {

        var httplog = HttpLoggingInterceptor()
        httplog.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient
            .addInterceptor(httplog)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(6, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
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