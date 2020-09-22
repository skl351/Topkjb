package com.top.kjb.adapter.wuxianhuadongtab

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.top.kjb.adapter.adapter_twopage
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_attention_fans
import com.top.kjb.bean.bean_sports_list_item
import com.top.kjb.bean.bean_twopage_item_3he1
import com.top.kjb.model.SportsModel
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.functionClass
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:
 */
class GoodsFragment2 : BaseFragment(), OnRefreshListener, OnLoadMoreListener {
    private var mAdapter: adapter_twopage? = null
    private var type = 0
    override fun initView() {
        if (arguments != null) {
            type = arguments!!.getInt("type")
        }
        id_SmartRefreshLayout_donggtai.setOnLoadMoreListener(this)
        id_SmartRefreshLayout_donggtai.setOnRefreshListener(this)
        goodsRv.layoutManager = LinearLayoutManager(_mActivity)
        goodsRv.clipToPadding = false
        getListData(type)
    }

    val sportsModel: SportsModel by lazy { SportsModel() }
    var currentpage = 1
    var pagesize = 10
    override fun setData() {}
    private fun getListData(type: Int) {
        currentpage = 1
        sportsModel.highlightsselectHighlightsList(
            functionClass.getToken(),
            type,
            currentpage,
            pagesize
        )
            .enqueue(object :
                retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    t: Throwable
                ) {
                    id_SmartRefreshLayout_donggtai.finishRefresh()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (bean?.result?.list?.size == 0) {
                            id_nodate.visibility = View.VISIBLE
                        } else {
                            id_nodate.visibility = View.GONE
                        }
                        mAdapter = adapter_twopage(_mActivity, bean?.result?.list!!)
                        goodsRv.adapter = mAdapter
                    } else {
                        Show_toast.showText(mContext, "sports false")
                    }
                    id_SmartRefreshLayout_donggtai.finishRefresh()
                }

            })
    }

    private fun getListData2(type: Int) {
        currentpage++
        sportsModel.highlightsselectHighlightsList(
            functionClass.getToken(),
            type,
            currentpage,
            pagesize
        )
            .enqueue(object :
                retrofit2.Callback<Result<bean_twopage_item_3he1>> {
                override fun onFailure(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    t: Throwable
                ) {
                    id_SmartRefreshLayout_donggtai.finishLoadMore()
                }

                override fun onResponse(
                    call: Call<Result<bean_twopage_item_3he1>>,
                    response: Response<Result<bean_twopage_item_3he1>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (bean?.result?.list?.size != 0) {
                            mAdapter?.mData?.addAll(bean?.result?.list!!)
                            mAdapter?.notifyDataSetChanged()
                        }
                    } else {
                        Show_toast.showText(mContext, "sports false")
                    }
                    id_SmartRefreshLayout_donggtai.finishLoadMore()
                }

            })
    }

    companion object {
        fun getInstance(type: Int): GoodsFragment2 {
            val bundl = Bundle()
            bundl.putInt("type", type)
            val goodsFragment = GoodsFragment2()
            goodsFragment.arguments = bundl
            return goodsFragment
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getListData(type)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        getListData2(type)
    }


}