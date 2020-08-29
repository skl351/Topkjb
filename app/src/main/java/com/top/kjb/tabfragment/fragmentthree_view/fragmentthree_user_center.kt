package com.top.kjb.tabfragment.fragmentthree_view

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.JsonObject
import com.gyf.immersionbar.ImmersionBar
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
import com.top.kjb.bean.bean_qiniu_token
import com.top.kjb.bean.user_info
import com.top.kjb.customview.upcenterimage_bottom
import com.top.kjb.model.ThreeModel
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.tabfragment.Myselfinfo
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentthree_user_center.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.File

class fragmentthree_user_center : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBoradcastReceiver()
        ImmersionBar.with(this).init();
        setContentView(R.layout.layout_fragmentthree_user_center)
        init_intent()
        init_view()
        init_click()
        init_viewpage()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.updatabackimage)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }

    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.updatabackimage -> {
                    chooseImage()
                }

            }
        }
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
                    val selectList = data?.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES)

                    if (selectList?.size != 0) {
                        list_image_media =
                            functionClass.str2localmedia(selectList!!) as ArrayList<LocalMedia>

                        init_getqiniutoken()
                    }

                }
            }
        }
    }

    var userId = 0
    override fun init_intent() {
        super.init_intent()
        userId = intent.getIntExtra("userId", functionClass.getUserId())
    }

    override fun init_click() {
        super.init_click()
        id_back.setOnClickListener(this)
        id_edit_ziliao.setOnClickListener(this)
        id_back_image.setOnClickListener(this)
    }

    override fun init_view() {
        super.init_view()
        id_top_bar.layoutParams.height = functionClass.getbarHight(this)
        if (userId == functionClass.getUserId()) {
            id_edit_ziliao.visibility = View.VISIBLE
            id_click_attion.visibility = View.GONE
        } else {
            id_edit_ziliao.visibility = View.GONE
            id_click_attion.visibility = View.VISIBLE
        }
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {

                val scale = Math.abs(p1).toFloat() / id_linshi_big_view.height
                val alpha = 255 * scale
                println("scale" + scale + "," + alpha)
                if (alpha.toInt() < 255) {

                    toolbar.setBackgroundColor(Color.argb(alpha.toInt(), 255, 255, 255));
                    ImmersionBar.with(this@fragmentthree_user_center).statusBarColor(R.color.white2)
                        .statusBarAlpha(scale)
                        .statusBarColorTransform(R.color.white).init();
                } else {
                    ImmersionBar.with(this@fragmentthree_user_center).statusBarColor(R.color.white)
                        .statusBarDarkFont(true)
                        .init();
                    toolbar.setBackgroundColor(Color.argb(255, 255, 255, 255));
                }

            }

        })
    }

    val threeModel: ThreeModel by lazy { ThreeModel() }
    private fun init_data() {
        threeModel.usergetUserCenter(functionClass.getToken(), userId)
            .enqueue(object : retrofit2.Callback<Result<user_info>> {
                override fun onFailure(call: Call<Result<user_info>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<user_info>>,
                    response: Response<Result<user_info>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        ImageLoader.getInstance()
                            .displayImage(bean?.result?.headImg, id_user_headimg)
                        id_user_name.setText(bean?.result?.username)
                        id_fansNum.setText(bean?.result?.fansNum.toString())
                        id_followNum.setText(bean?.result?.followNum.toString())
                        id_user_summary.setText(bean?.result?.motto)
                        ImageLoader.getInstance()
                            .displayImage(bean?.result?.dynamicBackground, id_backimage)
                    }
                }

            })
    }


    private fun init_viewpage() {
        getfragment()
        init_adapter()
    }

    var mAdapter: FragmentPagerAdapter? = null
    var currentIndex: Int = 0
    var num_item_page = 2
    private fun init_adapter() {

        view_pager_myself.offscreenPageLimit = 2
        mAdapter = object : FragmentPagerAdapter(supportFragmentManager!!) {
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return mFragments.get(position)
            }

            override fun getCount(): Int {
                return mFragments.size
            }


        }
        view_pager_myself.adapter = mAdapter
        id_line1.setOnClickListener({
            view_pager_myself.setCurrentItem(0)

        })
        id_line2.setOnClickListener({
            view_pager_myself.setCurrentItem(1)

        })
        view_pager_myself.addOnPageChangeListener(object :
            androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (currentIndex == 0 && position == 0) {//0->1
                    chagetext(0)
                    val lp = myImgtag.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.leftMargin =
                        (positionOffset * (can_move_view.width.div(num_item_page)) + currentIndex * can_move_view.width.div(
                            num_item_page
                        )).toInt() + id_line1.width / 2 - id_line1.width / 6
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 1 && position == 0) {//1->0
                    chagetext(1)
                    val lp = myImgtag.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.leftMargin =
                        (-(1 - positionOffset) * (can_move_view.width.div(num_item_page)) + currentIndex * can_move_view.width.div(
                            num_item_page
                        )).toInt() + id_line1.width / 2 - id_line1.width / 6
                    myImgtag.setLayoutParams(lp)
                }

            }

            override fun onPageSelected(position: Int) {
                currentIndex = position
            }

        })

    }

    fun chagetext(i: Int) {
        id_line1.setTypeface(Typeface.DEFAULT);
        id_line1.setTextColor(resources.getColor(R.color.color_a4a4a4))
        id_line2.setTypeface(Typeface.DEFAULT);
        id_line2.setTextColor(resources.getColor(R.color.color_a4a4a4))
        when (i) {
            0 -> {
                id_line1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                id_line1.setTextColor(resources.getColor(R.color.color_1cbe6f))
            }
            1 -> {
                id_line2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                id_line2.setTextColor(resources.getColor(R.color.color_1cbe6f))
            }
        }

    }

    var mFragments = ArrayList<Fragment>()
    var framgent1: Fragment? = null
    var framgent2: Fragment? = null
    private fun getfragment() {
        framgent1 = Dongtai_list()
        var Bundle = Bundle()
        Bundle.putInt("userId", userId)
        framgent1?.arguments = Bundle
        framgent2 = soucang_list()
        var Bundle2 = Bundle()
        Bundle2.putInt("userId", userId)
        framgent2?.arguments = Bundle2
        mFragments.add(framgent1 as Fragment)
        mFragments.add(framgent2 as Fragment)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_back_image -> {
                var bottom = upcenterimage_bottom(this)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .atView(id_back_image)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_edit_ziliao -> {
                var intent = Intent(this, Myselfinfo::class.java)
                startActivity(intent)
            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
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
                                    image_no =Sp.qiiu + p0.toString()
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }
    private fun init_save() {



        threeModel.userupdateDynamicBackground(functionClass.getToken(), image_no)
            .enqueue(object : retrofit2.Callback<Result<Int>> {
                override fun onFailure(call: Call<Result<Int>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<Result<Int>>,
                    response: Response<Result<Int>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {

                        init_data()
                    } else {
                        Show_toast.showText(this@fragmentthree_user_center, "更新失败")
                    }
                }

            })
    }
}