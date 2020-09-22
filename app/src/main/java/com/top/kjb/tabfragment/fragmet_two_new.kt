package com.top.kjb.tabfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.top.kjb.R
import com.top.kjb.Userabout.LoginActivity
import com.top.kjb.adapter.wuxianhuadongtab.GoodsFragment2
import com.top.kjb.adapter.wuxianhuadongtab.TabAdapter
import com.top.kjb.adapter.wuxianhuadongtab.TabAdapter2
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_type_item
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.fragmenttwo_view.publish_item
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util
import kotlinx.android.synthetic.main.layout_fragment_two_new_view.*
import retrofit2.Call
import retrofit2.Response

class fragmet_two_new : BaseFragment(), View.OnClickListener {
    val mainModel: MainModel by lazy { MainModel() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_two_new_view, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_top_bar_2.layoutParams.height = functionClass.getbarHight(activity!!)
        init_click()
        init_horline()

    }

    override fun init_click() {
        super.init_click()
        id_click_publish.setOnClickListener(this)
    }

    private var mAdapter: TabAdapter2? = null
    private fun init_horline() {

        mainModel.selectAllGroundingSports()
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_type_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_type_item>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_type_item>>>,
                    response: Response<Result<ArrayList<bean_type_item>>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        println("bean.toString()" + bean?.result)
                        var list_big = ArrayList<bean_type_item>()
                        var item1 = bean_type_item()
                        item1.sportsId = -2//关注
                        item1.sportsName = "关注"
                        list_big.add(item1)
                        var item2 = bean_type_item()
                        item2.sportsId = -1//慈溪
                        item2.sportsName = Sp.address_small.split("市")[0]
                        list_big.add(item2)
                        var list = bean?.result
                        list_big.addAll(list!!)
                        java_util().addliandongline2(horizontalScrollview2, list_big, activity)
                        horizontalScrollview2.setOnItemClickListerter { adapter, view, position ->
                            viewpager2.setCurrentItem(position, false)
                        }

                        val list_fragment: MutableList<com.top.kjb.adapter.wuxianhuadongtab.BaseFragment2> =
                            java.util.ArrayList()
                        for (i in 0..list_big?.size!! - 1) {
                            list_fragment.add(GoodsFragment2.getInstance(list_big.get(i).sportsId))
                        }

                        mAdapter = TabAdapter2(activity?.getSupportFragmentManager(), list_fragment)
                        viewpager2.adapter = mAdapter
                        viewpager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {

                            }

                            override fun onPageSelected(position: Int) {
                                horizontalScrollview2.setScrollPosition(position)
                            }

                            override fun onPageScrollStateChanged(state: Int) {}
                        })

                    } else {
                        Show_toast.showText(activity, "获取运动分类失败")
                    }
                }

            })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.id_click_publish -> {
                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, publish_item::class.java)
                startActivity(intent)
            }
        }
    }

}