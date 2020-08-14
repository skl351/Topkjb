package com.top.kjb.tabfragment.fragmenttwo_view

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.lcw.library.imagepicker.ImagePicker
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnItemClickListener
import com.lxj.xpopup.XPopup
import com.top.kjb.R
import com.top.kjb.customview.AllPlace_select_bottom
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.GlideEngine
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.nineimageutil.FullyGridLayoutManager
import com.top.kjb.utils.nineimageutil.GridImageAdapter
import kotlinx.android.synthetic.main.layout_publish_item_view.*

class publish_item:BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_publish_item_view)
        init_click()
        init_refre()
    }

    var maxSelectNum = 9
    lateinit var adapter_nine: GridImageAdapter
    private fun init_refre() {
        val manager = FullyGridLayoutManager(this,
            3, GridLayoutManager.VERTICAL, false)
        snpl_moment_add_photos.layoutManager = manager
        adapter_nine = GridImageAdapter(this, object : GridImageAdapter.onAddPicClickListener {
            override fun onAddPicClick() {
                chooseImage()
            }

        })
        adapter_nine.setSelectMax(maxSelectNum)
        snpl_moment_add_photos.adapter = adapter_nine
        adapter_nine.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                val selectList: List<LocalMedia> = adapter_nine.getData() as List<LocalMedia>
                if (selectList.size > 0) {
                    // 预览图片 可自定长按保存路径
//                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
//                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
//                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                    PictureSelector.create(this@publish_item)
                        .themeStyle(R.style.picture_default_style) // xml设置主题
//                            .setPictureStyle(mPictureParameterStyle) // 动态自定义相册主题
                        //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // 设置相册Activity方向，不设置默认使用系统
                        .isNotPreviewDownload(false) // 预览图片长按是否可以下载
                        //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                        .imageEngine(GlideEngine.createGlideEngine()) // 外部传入图片加载引擎，必传项
                        .openExternalPreview(position, selectList)
                }
            }

        })
    }
    // 去选择图片
    private fun chooseImage() {


        functionClass.selectMatisse(this, maxSelectNum, adapter_nine.data as ArrayList<LocalMedia>)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = data?.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES)
                    var selectList_new = functionClass.str2localmedia(selectList!!)

                    adapter_nine.setList(selectList_new)
                    adapter_nine.notifyDataSetChanged()
                }
            }
        }
    }

    override fun init_click() {
        super.init_click()
        id_back.setOnClickListener(this)
        id_click_select_place.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.id_click_select_place->{
                var bottom=AllPlace_select_bottom(this)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .atView(id_click_publish)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_back->{
                onBackPressed()
            }
        }
    }
}