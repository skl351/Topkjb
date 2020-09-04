package com.top.kjb.tabfragment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.Image
import android.os.Bundle
import android.view.View
import com.google.gson.JsonObject
import com.lcw.library.imagepicker.ImagePicker
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.lxj.xpopup.XPopup
import com.nostra13.universalimageloader.core.ImageLoader
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.top.kjb.R
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_gerenziliao
import com.top.kjb.bean.bean_qiniu_token
import com.top.kjb.customview.dialog.sex_bottom
import com.top.kjb.model.ThreeModel
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_main_item.*
import kotlinx.android.synthetic.main.layout_myselefinfo.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.File

class Myselfinfo : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_myselefinfo)
        registerBoradcastReceiver()
        init_click()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.sexselect)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }


    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.sexselect -> {
                    sex=intent.getStringExtra("sex")
                    id_user_sex.setText(sex)
                }
            }
        }
    }

    val threeModel: ThreeModel by lazy { ThreeModel() }
    private fun init_data() {
        threeModel.usergetUserData(functionClass.getToken())
            .enqueue(object : retrofit2.Callback<Result<bean_gerenziliao>> {
                override fun onFailure(call: Call<Result<bean_gerenziliao>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_gerenziliao>>,
                    response: Response<Result<bean_gerenziliao>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        image_no=bean?.result?.headImg.toString()
                        ImageLoader.getInstance()
                            .displayImage(bean?.result?.headImg, id_head_img)
                        id_user_name.setText(bean?.result?.username)
                        id_user_motto.setText(bean?.result?.motto)
                        id_user_sex.setText(bean?.result?.gender)
                        id_birthday.setText(bean?.result?.birthday)
                        id_city.setText(bean?.result?.city)
                    }
                }

            })
    }

    override fun init_click() {
        super.init_click()
        id_click_updata_image.setOnClickListener(this)
        id_click_save.setOnClickListener(this)
        id_click_sex.setOnClickListener(this)
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_sex -> {
                var bottom = sex_bottom(this)
                XPopup.Builder(this)
                    .hasShadowBg(false)
                    .atView(id_click_sex)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_click_save -> {
                if (list_image_media.size != 0) {
                    init_getqiniutoken()
                } else {
                    init_save()
                }

            }
            R.id.id_click_updata_image -> {
                chooseImage()
            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }

    var sex="男"
    private fun init_save() {
        var jsonObject = JsonObject()


        if (!image_no.equals("")) {
            jsonObject.addProperty("headImg", image_no)
        }
        jsonObject.addProperty("username", id_user_name.text.toString())
        jsonObject.addProperty("motto", id_user_motto.text.toString())
        jsonObject.addProperty("gender", sex)
        println("展示" + jsonObject.toString())

        threeModel.userupdateUserDataUserVO(functionClass.getToken(), jsonObject)
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {

                        var intent = Intent(Sp.loginoutsuccess)
                        sendBroadcast(intent)
                        functionClass.setUsername(id_user_name.text.toString())
                        functionClass.setmotto(id_user_motto.text.toString())
                        functionClass.setHeadImg(image_no)
                        Show_toast.showText(this@Myselfinfo, "")
                        finish()
                    } else {
                        Show_toast.showText(this@Myselfinfo, "保存失败")
                    }
                }

            })
    }

    var image_no = ""
    val twoModel: TwoModel by lazy { TwoModel() }
    var key = ""
    private fun init_getqiniutoken() {

        key = "/highlights/pic_" + functionClass.getUserId() + "_" + System.currentTimeMillis()
        twoModel.QiniugetQiniuToken(functionClass.getToken(), key)
            .enqueue(object : retrofit2.Callback<Result<bean_qiniu_token>> {
                override fun onFailure(call: Call<Result<bean_qiniu_token>>, t: Throwable) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_qiniu_token>>,
                    response: Response<Result<bean_qiniu_token>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {

                        Sp.qiniu_token = bean?.result?.uploadToken.toString()
                        var newFile = File(list_image_media.get(0).realPath)
                        val uploadManager = UploadManager()
                        uploadManager.put(
                            newFile,
                            key,
                            Sp.qiniu_token,
                            object : UpCompletionHandler {
                                override fun complete(
                                    p0: String?,
                                    p1: ResponseInfo?,
                                    p2: JSONObject?
                                ) {
                                    println("=====" + p0 + "," + p1 + "," + p2)
                                    image_no =
                                        Sp.qiiu + p0.toString()
                                    init_save()
                                }

                            },
                            null
                        )
                    } else {
                        println("失败获取七牛")
                    }
                }

            })

    }

    // 去选择图片
    private fun chooseImage() {


        functionClass.selectMatisse(this, 1, ArrayList<LocalMedia>())

    }

    var list_image_media = ArrayList<LocalMedia>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList =
                        data?.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES)

                    if (selectList?.size != 0) {
                        list_image_media =
                            functionClass.str2localmedia(selectList!!) as ArrayList<LocalMedia>
                        ImageLoader.getInstance()
                            .displayImage(
                                "file:/" + list_image_media.get(0).realPath,
                                id_head_img
                            )
                    }

                }
            }
        }
    }
}