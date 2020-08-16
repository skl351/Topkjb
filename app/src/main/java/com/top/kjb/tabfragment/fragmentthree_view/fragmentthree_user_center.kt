package com.top.kjb.tabfragment.fragmentthree_view

import android.graphics.Typeface
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_fragmentthree_user_center.*

class fragmentthree_user_center : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragmentthree_user_center)
        init_viewpage()
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
        framgent2 = Dongtai_list()
        mFragments.add(framgent1 as Fragment)
        mFragments.add(framgent2 as Fragment)
    }
}