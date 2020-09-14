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
import com.top.kjb.tabfragment.fragmenttwo_view.fragment_two_newlist
import com.top.kjb.tabfragment.fragmenttwo_view.publish_item
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_fragmentone.id_RecyclerView
import kotlinx.android.synthetic.main.layout_fragmenttwo.*


class fragemnt_two : BaseFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmenttwo, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_top_bar_2.layoutParams.height = functionClass.getbarHight(activity!!)

        init_click()
        init_refre()
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
    var framgent3: Fragment? = null
    var framgent4: Fragment? = null
    private fun getfragment() {
        framgent1 = fragment_two_newlist()
        var Bundle = Bundle()
        Bundle.putInt("current", 0)
        framgent1?.arguments = Bundle
        framgent2 = fragment_two_newlist()
        var Bundle2 = Bundle()
        Bundle2.putInt("current", 1)
        framgent2?.arguments = Bundle2
        framgent3 = fragment_two_newlist()
        var Bundle3 = Bundle()
        Bundle3.putInt("current", 2)
        framgent3?.arguments = Bundle3
        framgent4 = fragment_two_newlist()
        var Bundle4 = Bundle()
        Bundle4.putInt("current", 3)
        framgent4?.arguments = Bundle4
        mFragments.add(framgent1 as Fragment)
        mFragments.add(framgent2 as Fragment)
        mFragments.add(framgent3 as Fragment)
        mFragments.add(framgent4 as Fragment)
    }

    var mAdapter: FragmentPagerAdapter? = null
    var currentIndex: Int = 0
    var num_item_page = 4
    private fun init_adapter() {
        view_pager_myself.offscreenPageLimit = 4
        mAdapter = object : FragmentPagerAdapter(activity?.supportFragmentManager!!) {
            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return mFragments.get(position)
            }

            override fun getCount(): Int {
                return mFragments.size
            }


        }
        view_pager_myself.adapter = mAdapter

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
                } else if (currentIndex == 1 && position == 1) {//0->1
                    chagetext(1)
                    val lp = myImgtag.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.leftMargin =
                        (positionOffset * (can_move_view.width.div(num_item_page)) + currentIndex * can_move_view.width.div(
                            num_item_page
                        )).toInt() + id_line1.width / 2 - id_line1.width / 6
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 2 && position == 1) {//1->0
                    chagetext(2)
                    val lp = myImgtag.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.leftMargin =
                        (-(1 - positionOffset) * (can_move_view.width.div(num_item_page)) + currentIndex * can_move_view.width.div(
                            num_item_page
                        )).toInt() + id_line1.width / 2 - id_line1.width / 6
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 2 && position == 2) {//0->1
                    chagetext(2)
                    val lp = myImgtag.getLayoutParams() as RelativeLayout.LayoutParams
                    lp.leftMargin =
                        (positionOffset * (can_move_view.width.div(num_item_page)) + currentIndex * can_move_view.width.div(
                            num_item_page
                        )).toInt() + id_line1.width / 2 - id_line1.width / 6
                    myImgtag.setLayoutParams(lp)
                } else if (currentIndex == 3 && position == 2) {//1->0
                    chagetext(3)
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


    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }


    val twoModel: TwoModel by lazy { TwoModel() }
    var currentpage = 1
    var pagesize = 10


    override fun init_view() {
        super.init_view()
    }

    override fun init_click() {
        super.init_click()
        id_line1.setOnClickListener(this)
        id_line2.setOnClickListener(this)
        id_line3.setOnClickListener(this)
        id_line4.setOnClickListener(this)
        id_click_publish.setOnClickListener(this)
    }

    var current_item = 0
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_publish -> {

                if (!functionClass.islogin()) {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    return
                }
                var intent = Intent(activity, publish_item::class.java)
                intent.putExtra("current_item", current_item)
                startActivity(intent)

            }
            R.id.id_line1 -> {
                view_pager_myself.setCurrentItem(0)
                id_click_publish.visibility = View.VISIBLE
                current_item = 0
                chagetext(current_item)

            }
            R.id.id_line2 -> {
                view_pager_myself.setCurrentItem(1)
                id_click_publish.visibility = View.VISIBLE
                current_item = 1
                chagetext(current_item)
            }
            R.id.id_line3 -> {
                view_pager_myself.setCurrentItem(2)
                id_click_publish.visibility = View.GONE
                current_item = 2
                chagetext(current_item)
                //yun dong
            }
            R.id.id_line4 -> {
                view_pager_myself.setCurrentItem(3)
                id_click_publish.visibility = View.GONE
                current_item = 3
                chagetext(current_item)
            }

        }
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
}