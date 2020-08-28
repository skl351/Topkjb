package com.top.kjb.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.lcw.library.imagepicker.ImagePicker
import com.lcw.library.imagepicker.manager.SelectionManager
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.top.kjb.originpack.BaseActivity
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object functionClass {

    fun setToken(token: String) {
        SharedPreferenceUtils.save("token", token)
    }

    fun getToken(): String {
        return SharedPreferenceUtils.getString("token", "")
    }

    fun setUserId(id: Int) {
        SharedPreferenceUtils.save("userid", id)
    }

    fun getUserId(): Int {
        return SharedPreferenceUtils.getInt("userid", 0)
    }

    fun setUsername(username: String) {
        SharedPreferenceUtils.save("username", username)
    }

    fun getUsername(): String {
        return SharedPreferenceUtils.getString("username", "")
    }

    fun setHeadImg(headImg: String) {
        SharedPreferenceUtils.save("headImg", headImg)
    }

    fun getHeadImg(): String {
        return SharedPreferenceUtils.getString("headImg", "")
    }
    fun setmotto(motto: String) {
        SharedPreferenceUtils.save("motto", motto)
    }

    fun getmotto(): String {
        return SharedPreferenceUtils.getString("motto", "")
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)--好
     */
    fun dip2px(dpValue: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    //像素转换成dp--kotlin----好
    fun pxtodp(pxValue: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将时间戳转为相应格式
     *
     * @param beginDate
     * @return 格式日期
     */
    fun getTime_ms(beginDate: String, type: String): String {
        val sdf = SimpleDateFormat(type)
        return sdf.format(Date(Long.parseLong(beginDate)))
    }

    fun islogin(): Boolean {
        println("-------------" + getToken())
        if ("".equals(getToken())) {
            return false
        } else {
            return true
        }
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        //显示度量
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels //返回屏幕宽度像素
    }

    fun selectMatisse(context: Context, index: Int, selectionData: ArrayList<LocalMedia>) {

//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        var selectList_new = functionClass.media2localstr(selectionData)
        System.out.println("duoshao" + selectList_new.size)
        SelectionManager.getInstance().removeAll() //清空选中记录
        ImagePicker.getInstance()
            .setTitle("选择") //设置标题
            .showCamera(true) //设置是否显示拍照按钮
            .showImage(true) //设置是否展示图片
            .showVideo(false) //设置是否展示视频
            .setSingleType(true) //设置图片视频不能同时选择
            .setMaxCount(index) //设置最大选择图片数目(默认为1，单选)
            .setImagePaths(selectList_new) //保存上一次选择图片的状态，如果不需要可以忽略
            .setImageLoader(GlideLoader()) //设置自定义图片加载器
            .start(context as BaseActivity, PictureConfig.CHOOSE_REQUEST)
    }

    fun str2localmedia(list: ArrayList<String>): List<LocalMedia> {
        var select = ArrayList<LocalMedia>()
        for (i in 0..list.size - 1) {
            var a = LocalMedia()
            a.path = list.get(i)
            a.realPath = list.get(i)
            select.add(a)
        }
        return select
    }

    fun media2localstr(list: List<LocalMedia>): ArrayList<String> {
        var select = ArrayList<String>()
        for (i in 0..list.size - 1) {

            select.add(list.get(i).realPath)
        }
        return select
    }

    fun error_open(errorstring: String) {
        println("数据异常" + errorstring)
    }


}