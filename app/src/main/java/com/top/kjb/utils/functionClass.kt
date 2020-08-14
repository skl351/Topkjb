package com.top.kjb.utils

import android.content.Context
import com.lcw.library.imagepicker.ImagePicker
import com.lcw.library.imagepicker.manager.SelectionManager
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.top.kjb.originpack.BaseActivity

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
}