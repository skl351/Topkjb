package com.top.kjb.tabfragment.fragmentthree_view.orderlist

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentPagerAdapter
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_order_list_view.*

class my_order_list : BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_order_list_view)
        init_click()
        init_viewpage()
    }

    private fun init_viewpage() {
        getfragment()
        init_adapter()
    }

    var mFragments = ArrayList<androidx.fragment.app.Fragment>()
    var framgent1: androidx.fragment.app.Fragment? = null
    var framgent2: androidx.fragment.app.Fragment? = null
    var framgent3: androidx.fragment.app.Fragment? = null
    private fun getfragment() {
        framgent1 = order1_list()
        framgent2 = order1_list()
        framgent3 = order1_list()
        mFragments.add(framgent1 as androidx.fragment.app.Fragment)
        mFragments.add(framgent2 as androidx.fragment.app.Fragment)
        mFragments.add(framgent3 as androidx.fragment.app.Fragment)
    }

    var currentIndex: Int = 0
    var mAdapter: androidx.fragment.app.FragmentPagerAdapter? = null
    private fun init_adapter() {
        var width_use=functionClass.getScreenWidth(this@my_order_list)/3
        var huizhen=20
        id_viewpager.offscreenPageLimit = 2
        mAdapter = object : FragmentPagerAdapter(supportFragmentManager!!) {
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return mFragments.get(position)
            }

            override fun getCount(): Int {
                return mFragments.size
            }


        }
        id_viewpager.adapter = mAdapter

        id_line1.setOnClickListener({
            id_viewpager.setCurrentItem(0)
            (framgent1 as order1_list).init_data()
        })
        id_line2.setOnClickListener({
            id_viewpager.setCurrentItem(1)
            (framgent1 as order1_list).init_data()
        })
        id_line3.setOnClickListener({
            id_viewpager.setCurrentItem(2)
            (framgent1 as order1_list).init_data()
        })
        id_viewpager.addOnPageChangeListener(object :
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
                    val lp = myImgtag.getLayoutParams() as LinearLayout.LayoutParams
                    lp.leftMargin =
                        (positionOffset * (width_use) + currentIndex * width_use).toInt() + id_line1.width / 2 - huizhen
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 1 && position == 0) {//1->0
                    chagetext(1)
                    val lp = myImgtag.getLayoutParams() as LinearLayout.LayoutParams
                    lp.leftMargin =
                        (-(1 - positionOffset) * (width_use) + currentIndex * width_use).toInt() + id_line1.width / 2 - huizhen
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 1 && position == 1) { // 2->1
                    chagetext(1)
                    val lp = myImgtag.getLayoutParams() as LinearLayout.LayoutParams
                    lp.leftMargin =
                        (positionOffset * (width_use) + currentIndex * width_use).toInt() + id_line1.width / 2 -huizhen
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 2 && position == 1) {//1->2
                    chagetext(2)
                    val lp = myImgtag.getLayoutParams() as LinearLayout.LayoutParams
                    lp.leftMargin =
                        (-(1 - positionOffset) * (width_use) + currentIndex * width_use).toInt() + id_line1.width / 2 - huizhen
                    myImgtag.setLayoutParams(lp)
                }

            }

            override fun onPageSelected(position: Int) {
                currentIndex = position
            }

        })

    }

    fun chagetext(i: Int) {
        id_line1_text.setTextColor(resources.getColor(R.color.color_404040))
        id_line2_text.setTextColor(resources.getColor(R.color.color_404040))
        id_line3_text.setTextColor(resources.getColor(R.color.color_404040))
        when (i) {
            0 -> {
                id_line1_text.setTextColor(resources.getColor(R.color.color_1cbe6f))
            }
            1 -> {
                id_line2_text.setTextColor(resources.getColor(R.color.color_1cbe6f))
            }
            2 -> {
                id_line3_text.setTextColor(resources.getColor(R.color.color_1cbe6f))
            }
        }

    }

    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.id_back->{
                onBackPressed()
            }
        }
    }
}