package com.top.kjb.tabfragment.fragmenttwo_view

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.top.kjb.R
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_gym_payed_item
import com.top.kjb.bean.bean_qiniu_token
import com.top.kjb.customview.AllPlace_select_bottom
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.GlideEngine
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.nineimageutil.FullyGridLayoutManager
import com.top.kjb.utils.nineimageutil.GridImageAdapter
import kotlinx.android.synthetic.main.layout_publish_item_view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

class publish_item : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_publish_item_view)
        registerBoradcastReceiver()
        init_intent()
        init_view()
        init_click()
        init_refre()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.selectplacelast)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.selectplacelast -> {
                    nowgymnasiumId = intent.getIntExtra("id", 0)
                    var place = intent.getStringExtra("place")
                    id_text_place_name.setText(place)
                }

            }
        }
    }
    var list_place = ArrayList<bean_gym_payed_item>()
    private fun init_data() {
        twoModel.gymnasiumLogselectGymListByUserId(functionClass.getToken())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_gym_payed_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_gym_payed_item>>>,
                    t: Throwable
                ) {
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_gym_payed_item>>>,
                    response: Response<Result<ArrayList<bean_gym_payed_item>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result

                        list_place = list!!

                    }

                }

            })
    }

    override fun init_view() {
        super.init_view()

    }

    override fun init_intent() {
        super.init_intent()
    }

    var key = ""
    private fun init_getqiniutoken() {

        key = "/highlights/pic_" + functionClass.getUserId() + "_" + System.currentTimeMillis()
        twoModel.QiniugetQiniuToken(functionClass.getToken(), key)
            .enqueue(object : retrofit2.Callback<Result<bean_qiniu_token>> {
                override fun onFailure(call: Call<Result<bean_qiniu_token>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                    id_click_publish.isEnabled = true

                }

                override fun onResponse(
                    call: Call<Result<bean_qiniu_token>>,
                    response: Response<Result<bean_qiniu_token>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {

                        Sp.qiniu_token = bean?.result?.uploadToken.toString()
                        pubilsh_gotoqiniu()
                    } else {
                        println("失败获取七牛")
                    }
                }

            })

    }

    var maxSelectNum = 9
    lateinit var adapter_nine: GridImageAdapter
    private fun init_refre() {
        val manager = FullyGridLayoutManager(
            this,
            3, GridLayoutManager.VERTICAL, false
        )
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
        id_click_publish.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_publish -> {
                id_click_publish.isEnabled = false
                pubilsh_zhiqian()
            }
            R.id.id_click_select_place -> {
                var bottom = AllPlace_select_bottom(this)

                bottom.setplacelist(list_place)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .atView(id_click_publish)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }

    private fun pubilsh_zhiqian() {
        curr_image = 0
        list_image = ArrayList()
        var imagelist = adapter_nine.data
        if (imagelist.size == 0) {
            pubilsh_goto()
        } else {
            init_getqiniutoken()
        }
    }

    var curr_image = 0
    lateinit var list_image: ArrayList<String>
    private fun pubilsh_gotoqiniu() {
        var imagelist = adapter_nine.data
        var imagenow = imagelist.get(curr_image).realPath
        var newFile = File(imagenow)


        Luban.with(this)
            .load(newFile)
            .ignoreBy(100)
            .setCompressListener(object : OnCompressListener {
                override fun onSuccess(file: File?) {
                    val uploadManager = UploadManager()
                    uploadManager.put(file, key, Sp.qiniu_token, object : UpCompletionHandler {
                        override fun complete(p0: String?, p1: ResponseInfo?, p2: JSONObject?) {
                            println("=====" + p0 + "," + p1 + "," + p2)
                            list_image.add(Sp.qiiu + p0.toString())
                            curr_image++
                            if (curr_image >= adapter_nine.data.size) {
                                pubilsh_goto()
                                return
                            }
                            init_getqiniutoken()
                        }

                    }, null)
                }

                override fun onError(e: Throwable?) {
                    val uploadManager = UploadManager()
                    uploadManager.put(newFile, key, Sp.qiniu_token, object : UpCompletionHandler {
                        override fun complete(p0: String?, p1: ResponseInfo?, p2: JSONObject?) {
                            println("=====" + p0 + "," + p1 + "," + p2)
                            list_image.add(Sp.qiiu + p0.toString())
                            curr_image++
                            if (curr_image >= adapter_nine.data.size) {
                                pubilsh_goto()
                                return
                            }
                            init_getqiniutoken()
                        }

                    }, null)
                }

                override fun onStart() {

                }

            })
            .launch()


    }

    val twoModel: TwoModel by lazy { TwoModel() }
    var nowgymnasiumId = 0
    private fun pubilsh_goto() {
        var text = id_edit_content.text.toString()

        var pic = ""
        for (i in 0..list_image.size - 1) {
            pic += list_image.get(i)
            if (list_image.size - 1 == i) {
                break
            } else {
                pic += ","
            }
        }

        if (text.length == 0 && pic.equals("")) {
            Show_toast.showText(this@publish_item, "发表内容不能为空")
            id_click_publish.isEnabled = false
            return
        }
        var userId = functionClass.getUserId()
        var token = functionClass.getToken()
        var gymnasiumId = nowgymnasiumId



        twoModel.highlightsinsertHighlights(
            token, userId, text, pic, gymnasiumId
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                    id_click_publish.isEnabled = false
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        Show_toast.showText(this@publish_item, bean?.result)
                        var intent = Intent(Sp.publishsuccess)
                        sendBroadcast(intent)
                        finish()
                    } else {
                        Show_toast.showText(this@publish_item, "发表失败")
                        finish()
                    }

                }

            })


    }

    private fun pubilsh_goto_quanzi() {
        var text = id_edit_content.text.toString()
        if (text.length == 0) {
            Show_toast.showText(this@publish_item, "发表内容不能为空")
        }
        var pic = ""
        for (i in 0..list_image.size - 1) {
            pic += list_image.get(i)
            if (list_image.size - 1 == i) {
                break
            } else {
                pic += ","
            }
        }
        println("======" + pic)

        var userId = functionClass.getUserId()
        var token = functionClass.getToken()

        twoModel.circleinsertCircle(token, userId, text, pic)
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        Show_toast.showText(this@publish_item, bean?.result)
                        var intent = Intent(Sp.publishsuccess)
                        sendBroadcast(intent)
                        finish()
                    } else {
                        Show_toast.showText(this@publish_item, "发表失败")
                    }
                }

            })


    }

}