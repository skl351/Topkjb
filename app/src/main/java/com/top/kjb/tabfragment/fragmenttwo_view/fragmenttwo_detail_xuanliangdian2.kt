package com.top.kjb.tabfragment.fragmenttwo_view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.nostra13.universalimageloader.core.ImageLoader
import com.top.kjb.R
import com.top.kjb.adapter.adapter_nine_image
import com.top.kjb.adapter.adapter_user_comment2
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.bean.bean_user_comment
import com.top.kjb.customview.RoundImageView
import com.top.kjb.customview.dialog.jubao_select_bottom
import com.top.kjb.model.ThreeModel
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.tabfragment.newfragmentone.fragmentone_newdetail
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util
import kotlinx.android.synthetic.main.layout_fragmenttwo_detail_view.*
import retrofit2.Call
import retrofit2.Response


class fragmenttwo_detail_xuanliangdian2 : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmenttwo_detail_view)
        registerBoradcastReceiver()
        init_intent()
        init_view()
        init_refre()
        init_click()
        init_data()
    }

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.huifuintent)
        intentFilter.addAction(Sp.jubaoinfo)
        registerReceiver(mBroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }

    var parentId = 0
    var replyType = 0
    var commentsId = 0
    var sendtype = 1//1评论 2回复
    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.jubaoinfo -> {
                    var jubao = intent.getStringExtra("jubao")
                    Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2,"举报成功")

                }
                Sp.huifuintent -> {
                    parentId = intent.getIntExtra("parentId", 0)
                    replyType = intent.getIntExtra("replyType", 0)
                    commentsId = intent.getIntExtra("commentsId", 0)
                    sendtype = 2
                    id_edit.requestFocus()
                    id_edit.setText("")
                    id_edit.setHint("回复")
                    java_util().showInput(id_edit, this@fragmenttwo_detail_xuanliangdian2)
                }

            }
        }
    }
    lateinit var list_head: ArrayList<RoundImageView>
    lateinit var list_head_big: ArrayList<View>
    override fun init_view() {
        super.init_view()


        list_head_big = ArrayList<View>()
        list_head_big.add(id_head_1)
        list_head_big.add(id_head_2)
        list_head_big.add(id_head_3)
        list_head_big.add(id_head_4)
        list_head_big.add(id_head_5)
        list_head_big.add(id_head_6)
        list_head = ArrayList()
        list_head.add(id_image1)
        list_head.add(id_image2)
        list_head.add(id_image3)
        list_head.add(id_image4)
        list_head.add(id_image5)
        list_head.add(id_image6)

        id_edit.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                //获取当前界面可视部分
                window.decorView.getWindowVisibleDisplayFrame(r)
                //获取屏幕的高度
                val screenHeight = window.decorView.rootView.height
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                val heightDifference = screenHeight - r.bottom
                Log.d("Keyboard Size", "Size: " + heightDifference)
                if (heightDifference < 200) {
                    id_bottom_edit_big?.visibility = View.GONE
                    zhezhao?.visibility = View.GONE
                } else {
                    id_bottom_edit_big?.visibility = View.VISIBLE
                    zhezhao?.visibility = View.VISIBLE
                }
            }

        })

        id_edit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (sendtype == 1) {
                        init_send()
                    } else {
                        init_huifu()
                    }

                }
                return false;
            }

        })
    }

    private fun init_huifu() {
        var text = id_edit.text.toString()
        twoModel.highlightsReplyinsertHighlightsReply(
            functionClass.getToken(),
            parentId,
            text,
            replyType,
            commentsId
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, bean?.result)
                        init_list(id)
                        java_util().hideInput(this@fragmenttwo_detail_xuanliangdian2)
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "炫亮点评论list失败")
                    }

                }

            })
    }

    private fun init_send() {
        var text = id_edit.text.toString()
        twoModel.highlightsCommentsinsertHighlightsComments(
            functionClass.getToken(),
            functionClass.getUserId(),
            id,
            text
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, bean?.result)
                        init_list(id)
                        java_util().hideInput(this@fragmenttwo_detail_xuanliangdian2)
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "炫亮点评论list失败")
                    }

                }

            })
    }

    override fun init_intent() {
        super.init_intent()
        id = intent.getIntExtra("id", 0)
    }

    lateinit var adapter_nine: adapter_nine_image
    lateinit var list: ArrayList<bean_user_comment.bean_user_comment>
    lateinit var adapter_comment: adapter_user_comment2
    private fun init_data() {
        init_xuanliangdian()
    }


    var id = 0
    var gymnasiumId = 0
    var collect_check = 0
    var like_check = 0

    var followStatus = false
    private fun init_xuanliangdian() {
        twoModel.highlightsselectHighlightsById(
            functionClass.getToken(), id
        ).enqueue(object :
            retrofit2.Callback<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>> {
            override fun onFailure(
                call: Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>,
                t: Throwable
            ) {
                println("失败" + t.toString())
            }

            override fun onResponse(
                call: Call<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>,
                response: Response<Result<bean_twopage_item_3he1.bean_twopage_item_3he1_item>>
            ) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    println("bean?.result?.username" + bean?.result?.username)
                    ImageLoader.getInstance().displayImage(bean?.result?.headImg, id_head_img)
                    id_author_name.setText(bean?.result?.username)
                    id_author_motto.setText(bean?.result?.motto)
                    if ("".equals(bean?.result?.text)) {
                        id_text.visibility = View.GONE
                    } else {
                        id_text.visibility = View.VISIBLE
                        id_text.setText(bean?.result?.text)
                    }
                    followStatus = bean?.result?.followStatus!!
                    if (followStatus) {
                        id_click_attion.setTextColor(resources.getColor(R.color.color_1cbe6f))
                        id_click_attion.background =
                            resources.getDrawable(R.drawable.check_attention)
                        id_click_attion.isSelected = true


                    } else {
                        id_click_attion.background =
                            resources.getDrawable(R.drawable.check_attention2)
                        id_click_attion.isSelected = false
                        id_click_attion.setTextColor(resources.getColor(R.color.white))
                    }

                    userId_1 = bean?.result?.userId!!
                    if (userId_1 == functionClass.getUserId()) {
                        id_click_attion.visibility = View.GONE
                    } else {
                        id_click_attion.visibility = View.VISIBLE
                    }
                    var pic = bean?.result?.pic
                    if ("".equals(pic)) {
                        id_RecyclerView_image.visibility = View.GONE
                    } else {
                        id_RecyclerView_image.visibility = View.VISIBLE
                        var piclist = pic?.split(",")
                        adapter_nine = adapter_nine_image(
                            this@fragmenttwo_detail_xuanliangdian2,
                            piclist
                        )
                        id_RecyclerView_image.adapter = adapter_nine
                    }
                    try {
                        gymnasiumId = bean?.result?.gymnasiumId!!
                        if (gymnasiumId!=0){
                            id_big_view.visibility=View.VISIBLE
                            id_changdi_name.text = bean?.result?.gymName
                            id_changdi_summary.text = bean?.result?.gymDescribe
                            id_star_view.rating = bean?.result?.comprehensiveScore!!
                            id_show_score.setText(bean?.result?.comprehensiveScore.toString())
                            ImageLoader.getInstance().displayImage(bean?.result?.gymHeadImg, id_head)
                            id_location_long.setText("距您：" + bean?.result?.distance)
                            id_big_view.setOnClickListener(object :View.OnClickListener{
                                override fun onClick(p0: View?) {
                                    val intent = Intent(
                                        this@fragmenttwo_detail_xuanliangdian2,
                                        fragmentone_newdetail::class.java
                                    )
                                    intent.putExtra("lat", bean?.result?.lat)
                                    intent.putExtra("lng", bean?.result?.lng)
                                    intent.putExtra("id", bean?.result?.gymnasiumId!!)
                                    startActivity(intent)
                                }

                            })
                        }else{
                            id_big_view.visibility = View.GONE
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        id_big_view.visibility = View.GONE
                    }

                    id_time.setText(
                        functionClass.getTime_ms(
                            bean?.result?.startTime.toString(),
                            "yyyy-MM-dd HH:mm"
                        )
                    )

                    if (bean?.result?.likeStatus!!) {
                        like_check = 1
                    } else {
                        like_check = 0
                    }
                    if (bean?.result?.favoriteStatus!!) {
                        collect_check = 1
                    } else {
                        collect_check = 0
                    }
                    if (!bean?.result?.favoriteStatus!!) {
                        id_collect_img.setImageResource(R.mipmap.icon_star_off)
                    } else {
                        id_collect_img.setImageResource(R.mipmap.icon_star_on)
                    }
                    if (!bean?.result?.likeStatus!!) {
                        id_like_image.setImageResource(R.mipmap.icon_love_off)
                    } else {
                        id_like_image.setImageResource(R.mipmap.icon_love_on)
                    }

                    id_dianzanshu.text = bean?.result.likeCounts + "人点赞"
                    var listhead = bean?.result?.likeHeadImg
                    for (i in 0..listhead?.size!! - 1) {
                        list_head_big.get(i).visibility = View.VISIBLE
                        ImageLoader.getInstance()
                            .displayImage(listhead.get(i), list_head.get(i))
                    }

                } else {
                    Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "获取炫亮点详情")
                }


            }

        })
        init_list(id)
    }

    var currentpage = 1
    var pagesize = 10
    val twoModel: TwoModel by lazy { TwoModel() }
    private fun init_list(id: Int) {


        twoModel.highlightsCommentsselectComments(
            functionClass.getToken(),
            id,
            currentpage,
            pagesize
        )
            .enqueue(object : retrofit2.Callback<Result<bean_user_comment>> {
                override fun onFailure(call: Call<Result<bean_user_comment>>, t: Throwable) {
                    println("失败" + t.toString())
                }

                override fun onResponse(
                    call: Call<Result<bean_user_comment>>,
                    response: Response<Result<bean_user_comment>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list = bean?.result?.list!!
                        adapter_comment =
                            adapter_user_comment2(this@fragmenttwo_detail_xuanliangdian2, list)
                        adapter_comment.textType = 2
                        id_RecyclerView.adapter = adapter_comment
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "炫亮点评论list失败")
                    }


                }

            })

    }

    private fun init_refre() {

        val nineimage = GridLayoutManager(this, 3)
        id_RecyclerView_image?.layoutManager = nineimage


        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
        id_click_commom.setOnClickListener(this)
        zhezhao.setOnClickListener(this)
        id_like_image_big.setOnClickListener(this)
        id_collect_img_big.setOnClickListener(this)
        id_click_attion.setOnClickListener(this)
        id_click_jubao.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_jubao -> {
                var bottom = jubao_select_bottom(this)
                XPopup.Builder(this)
                    .hasShadowBg(true)
                    .atView(id_click_jubao)
                    .asCustom(bottom)
                    .show()
            }
            R.id.id_click_attion -> {
                id_click_attion.isEnabled = false
                guanzhuto()
            }
            R.id.id_like_image_big -> {
                id_like_image_big.isEnabled = false
                gotozan()
            }
            R.id.id_collect_img_big -> {
                id_collect_img_big.isEnabled = false
                gotocollect()
            }
            R.id.zhezhao -> {
                java_util().hideInput(this)
            }
            R.id.id_click_commom -> {
                sendtype = 1
                id_edit.requestFocus()
                id_edit.setText("")
                id_edit.setHint("")
                java_util().showInput(id_edit, this)
            }
            R.id.id_back -> {
                onBackPressed()
            }
        }
    }

    private fun gotozan() {
        var goint = 0
        if (like_check == 1) {
            goint = 0
        } else {
            goint = 1
        }
        twoModel.highlightsLikesinsertHighlightsLikes(
            functionClass.getToken(),
            id,
            0,
            goint,
            collect_check
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {
                id_like_image_big.isEnabled = true
            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (like_check == 1) {
                        like_check = 0
                        id_like_image.setImageResource(R.mipmap.icon_love_off)
                    } else {
                        like_check = 1
                        id_like_image.setImageResource(R.mipmap.icon_love_on)
                    }

                } else {
                    Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "操作失败")
                }
                id_like_image_big.isEnabled = true
            }

        })
    }

    private fun gotocollect() {
        var goint = 0
        if (collect_check == 1) {
            goint = 0
        } else {
            goint = 1
        }
        twoModel.highlightsLikesinsertHighlightsLikes(
            functionClass.getToken(),
            id,
            0,
            like_check,
            goint
        ).enqueue(object : retrofit2.Callback<Result<Int>> {
            override fun onFailure(call: Call<Result<Int>>, t: Throwable) {
                id_collect_img_big.isEnabled = true
            }

            override fun onResponse(call: Call<Result<Int>>, response: Response<Result<Int>>) {
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    if (collect_check == 1) {
                        collect_check = 0
                        id_collect_img.setImageResource(R.mipmap.icon_star_off)
                    } else {
                        collect_check = 1
                        id_collect_img.setImageResource(R.mipmap.icon_star_on)
                    }

                } else {
                    Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "操作失败")
                }
                id_collect_img_big.isEnabled = true
            }

        })
    }

    val threeModel: ThreeModel by lazy { ThreeModel() }
    var userId_1 = 0
    private fun guanzhuto() {
        threeModel.fansbecome_cancel_fans(
            functionClass.getToken(),
            functionClass.getUserId(),
            userId_1
        )
            .enqueue(object : retrofit2.Callback<Result<String>> {
                override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                    id_click_attion.isEnabled = true
                }

                override fun onResponse(
                    call: Call<Result<String>>,
                    response: Response<Result<String>>
                ) {
                    var intent=Intent(Sp.attent_gotoed)
                    sendBroadcast(intent)
                    id_click_attion.isEnabled = true
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (followStatus) {
                            id_click_attion.background =
                                resources.getDrawable(R.drawable.check_attention2)
                            id_click_attion.isSelected = false
                            id_click_attion.setTextColor(resources.getColor(R.color.white))

                        } else {
                            id_click_attion.setTextColor(resources.getColor(R.color.color_1cbe6f))
                            id_click_attion.background =
                                resources.getDrawable(R.drawable.check_attention)
                            id_click_attion.isSelected = true
                        }
                        followStatus = !followStatus

                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, bean?.result)
                    } else {
                        Show_toast.showText(this@fragmenttwo_detail_xuanliangdian2, "关注失败")
                    }
                }

            })
    }
}