package com.top.kjb.tabfragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.top.kjb.R
import com.top.kjb.Userabout.LoginActivity
import com.top.kjb.model.TwoModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.fragmenttwo_view.chat.layout_chat_one
import com.top.kjb.tabfragment.fragmenttwo_view.fragment_two_newlist
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmenttwo_new.*


class fragemnt_two_communication : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmenttwo_new, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_top_bar_2.layoutParams.height = functionClass.getbarHight(activity!!)
        init_click()
        init_view()
        init_viewpage()

    }

    private fun init_viewpage() {
        getfragment()
        init_adapter()
    }

    var mFragments = ArrayList<Fragment>()
    var framgent1: Fragment? = null
    var framgent2: Fragment? = null
    private fun getfragment() {
        framgent1 = layout_chat_one()
        framgent2 = layout_chat_one()
        mFragments.add(framgent1 as Fragment)
        mFragments.add(framgent2 as Fragment)
    }

    var mAdapter: FragmentPagerAdapter? = null
    var currentIndex: Int = 0
    private fun init_adapter() {
        view_pager_two2.offscreenPageLimit = 1
        mAdapter = object : FragmentPagerAdapter(activity?.supportFragmentManager!!) {
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return mFragments.get(position)
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        view_pager_two2.adapter = mAdapter
        view_pager_two2.addOnPageChangeListener(object :
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

                } else if (currentIndex == 1 && position == 0) {//1->0
                    chagetext(1)

                }


            }

            override fun onPageSelected(position: Int) {
                currentIndex = position
            }

        })

    }


    val twoModel: TwoModel by lazy { TwoModel() }
    var currentpage = 1
    var pagesize = 10


    override fun init_view() {
        super.init_view()
        id_line1.setTextSize(20f)
        id_line2.setTextSize(15f)
    }

    override fun init_click() {
        super.init_click()
        id_line1.setOnClickListener(this)
        id_line2.setOnClickListener(this)
    }

    var current_item = 0
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.id_line1 -> {
                current_item = 0
                chagetext(current_item)
                view_pager_two2.setCurrentItem(0)

            }
            R.id.id_line2 -> {
                current_item = 1
                chagetext(current_item)
                view_pager_two2.setCurrentItem(1)
            }


        }
    }


    fun chagetext(i: Int) {
        id_line1.setTextSize(15f)
        id_line1.setTextColor(resources.getColor(R.color.color_979797))
        id_line2.setTextSize(15f)
        id_line2.setTextColor(resources.getColor(R.color.color_979797))
        when (i) {
            0 -> {
                id_line1.setTextSize(20f)
                id_line1.setTextColor(resources.getColor(R.color.color_424242))
            }
            1 -> {
                id_line2.setTextSize(20f)
                id_line2.setTextColor(resources.getColor(R.color.color_424242))
            }
        }

    }
}